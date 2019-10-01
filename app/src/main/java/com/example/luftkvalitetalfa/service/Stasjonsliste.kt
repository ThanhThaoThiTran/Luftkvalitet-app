package com.example.luftkvalitetalfa.service

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.example.luftkvalitetalfa.model.Stations
import com.example.luftkvalitetalfa.model.Station

class Stasjonsliste {

    val stasjonsliste = ArrayList<Stations>()
    val luftkvalitetsliste = HashMap<String, Station>()

    companion object {
        val shared = Stasjonsliste()
    }

    fun loadStations() {

        NetworkAPI.shared.fetchStations { stations ->

            for (it in stations) {
                stasjonsliste.add(it)

            }
            this.loadAirquality()
        }

    }
    // Henter verdier og oppretter luftkvalitet-elementer av verdier til fragments

    private fun loadAirquality() {
        for (e in stasjonsliste) {
            NetworkAPI.shared.fetchStation(e.eoi) { station ->
                luftkvalitetsliste[e.eoi] = station
            }
        }
    }

    fun getTime():Int{
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("HH")
        val currentTime = sdf.format(calendar.time)
        var index = currentTime.toInt()-1
        if(index<0){
            //når klokka blir 00
            index = 23
        }
        return index
    }

}