package com.example.luftkvalitetalfa.maalestasjon

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.luftkvalitetalfa.model.Element
import com.example.luftkvalitetalfa.R
import com.example.luftkvalitetalfa.service.Favourite
import com.example.luftkvalitetalfa.model.Station
import com.example.luftkvalitetalfa.service.Measures
import com.example.luftkvalitetalfa.service.Stasjonsliste


class MaalestasjonFragment : Fragment() {

    var stasjon: Station? = null
    private var EOI = "NO0011A"
    var tidspunkt: Int = Stasjonsliste.shared.getTime()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maalestasjon, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        if(arg != null) EOI  = arg.getString("EOI")

        stasjon = Stasjonsliste.shared.luftkvalitetsliste[EOI]

        // Layout Reference:

        val stasjonNavn: TextView = view.findViewById(R.id.textview_målestasjon)
        val sted : TextView = view.findViewById(R.id.textview_place)

        val postAdr : TextView = view.findViewById(R.id.textview_postCode)
        val fargebar : View = view.findViewById(R.id.imageview_luftstatusFargebar)
        val status: TextView = view.findViewById(R.id.txt_updateStatus)

        val knapp: ImageButton = view.findViewById(R.id.btn_addStation_fromMaalestasjon)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_maalestasjon)

        // Layout Setup:
        stasjonNavn.text = stasjon?.meta?.location?.name
        sted.text = stasjon?.meta?.superlocation?.name
        postAdr.text = stasjon?.meta?.superlocation?.areacode


        val pm10 = stasjon!!.data.time[tidspunkt].variables.pm10_concentration.value.toString()
        val pm25 = stasjon!!.data.time[tidspunkt].variables.pm25_concentration.value.toString()
        val no2 = stasjon!!.data.time[tidspunkt].variables.no2_concentration.value.toString()
        val o3 = stasjon!!.data.time[tidspunkt].variables.o3_concentration.value.toString()
        val aqi = stasjon!!.data.time[tidspunkt].variables.AQI.value.toString()

        val pm10Farge = calculateColor(pm10, "pm10")
        val pm25Farge = calculateColor(pm25, "pm25")
        val no2Farge = calculateColor(no2, "nitrogendioksid")
        val o3Farge = calculateColor(o3, "ozon")

        val barFarge: String = calculateColor(aqi, "bar")
        fargebar.setBackgroundColor(Color.parseColor(barFarge))

        status.text = status(aqi, pm10, pm25, no2, o3)

        val particles = ArrayList<Element>()
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            particles.add(Element("Svevestøv PM10", pm10, pm10Farge))
            particles.add(Element("Svevestøv PM2,5", pm25, pm25Farge))
            particles.add(Element("Nitrogendioksid", no2, no2Farge))
            particles.add(Element("Ozon", o3, o3Farge))

            val adapter = MaalestasjonAdapter(particles)
            recyclerView.adapter = adapter
        }

        // Håndterer knapp interaksjon. Kan denne plasseres i en egen metode?

        knapp.setOnClickListener {
            val alertView = LayoutInflater.from(context).inflate(R.layout.alert_layout, null)

            val build = AlertDialog.Builder(context!!)
                .setView(alertView)
                .setTitle("Legg til favoritter")
                .setPositiveButton("Legg til") { dialog, _ ->
                    dialog.dismiss()

                    if(Favourite.shared.favorittListe.contains(stasjon!!)){
                        Toast.makeText(context, "" + stasjonNavn.text + " finnes allerede i favoritter", Toast.LENGTH_LONG)
                            .show()
                    }else{
                        Favourite.shared.favorittListe.add(stasjon!!)
                        Toast.makeText(context, "Legger til " +stasjonNavn.text + " i favoritter", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Senere") { dialog, _ ->
                    dialog.dismiss()
                }

            build.show()
        }
    }

    fun status(aqi: String, pm10: String, pm25: String, no2: String, o3: String) :String {
        var gronnStatus = "Nå er det lav luftforurensing 💚"
        var status = ""

        val p1 = statusParticle(pm10, "pm10")
        val p2 = statusParticle(pm25, "pm2.5")
        val o = statusParticle(o3, "ozon")
        val n = statusParticle(no2, "nitrogendioksid")
        var lvl = ""

        var niva = Measures.shared.calculateAQI(aqi.toDouble())
        if (niva < 2.0) {
            return gronnStatus
        } else if (niva < 3.0) {
            lvl = "moderat"
            status = "Nå er luftforurensingen $lvl \npå grunn av $p1$p2$o$n."
            return status
        }
        else if (niva < 4.0) {
            lvl = "høy"
            status = "Nå er luftforurensingen $lvl \npå grunn av $p1$p2$o$n."
            return status
        }
        else if (niva < 5.0) {
            lvl = "alvorlig"
            status = "Nå er luftforurensingen $lvl \npå grunn av $p1$p2$o$n."
            return status
        }
        else {
            status = "Luftforurensingen i dag er uviss."
            return status
        }
    }

    fun statusParticle(value: String, particle: String): String {
        val verdi = state(value, particle)
        var nyN = particle
        if (verdi > 2.0) {
            return nyN
        } else {
            nyN = ""
            return nyN
        }
    }

    fun state(value: String, particle: String ) : Double {
        var state = 0.0
        val v = value.toDouble()
        when (particle){
            "pm10" -> {
                state = Measures.shared.calculatePM10(v)
                return state
            }
            "pm25" -> {
                state = Measures.shared.calculatePM25(v)
                return state
            }
            "nitrogendioksid" -> {
                state = Measures.shared.calculateNO3(v)
                return state
            }
            "ozon" -> {
                state = Measures.shared.calculateO3(v)
                return state
            }
            else -> {
                return 0.0
            }
        }
    }


     fun calculateColor(v: String, particle: String): String {
        var state = 0.0
        val value = v.toDouble()

        when (particle) {
            "pm10" -> {
                state = Measures.shared.calculatePM10(value)
                return color(state)
            }
            "pm25" -> {
                state = Measures.shared.calculatePM25(value)
                return color(state)
            }
            "nitrogendioksid" -> {
                state = Measures.shared.calculateNO3(value)
                return color(state)
            }
            "ozon" -> {
                state = Measures.shared.calculateO3(value)
                return color(state)
            }
            "bar" -> {
                state = Measures.shared.calculateAQI(value)
                return color(state)
            }
            "status" -> {
                return "lol"
            }
            // Returnerer Unknown, grå
            else -> return "#8E8B8B"
        }
    }


    fun color(state: Double): String {
        return when {
            state < 2.0 -> // Status Lite: Grønn
                "#9EFDBF"
            state < 3.0 -> // Status Moderat: Gul
                "#FDF29E"
            state < 4.0 -> // Status Høy: Rød
                "#FF6969"
            state < 5.0 -> // Status Alvorlig: Lilla
                "#58004E"
            else -> // Status Unknown: Grå
                "#8E8B8B"
        }
    }
}
