package com.example.luftkvalitetalfa.model

import com.example.luftkvalitetalfa.service.Measures

class Station(val meta: Meta, val data: Data) {

    fun getStatus(t: Int) : String {
        val aq = this.data.time[t].variables.AQI.value
        var state = Measures.shared.calculateAQI(aq.toDouble())
        return when {
            state < 2.0 -> // Status Lite: Grønn
                "Her er det lav luftforurensing"
            state < 3.0 -> // Status Moderat: Gul
                "Her er det moderat luftforurensing"
            state < 4.0 -> // Status Høy: Rød
                "Her er det høy luftforurensing"
            state < 5.0 -> // Status Alvorlig: Lilla
                "Her er det alvorlig luftforurensing"
            else -> // Status Unknown: Grå
                "Ingen Data"
        }
    }

    fun getColor(t: Int) : String{
        val aq = this.data.time[t].variables.AQI.value
        var state = Measures.shared.calculateAQI(aq.toDouble())
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

class Meta(val superlocation: Superlocation, val location: Location, val reftime: String)

class Superlocation(val name: String, val areacode: String, val latitude: String, val areaclass: String,
                    val longitude: String, val superareacode:String)

class Location(val latitude: String, var name: String, val areacode: String, val longitude: String)


class Data(val time: Array<Time>)

class Time(val from: String, val to: String, val variables: Variables)

class Variables(val pm25_local_fraction_heating: pm25_local_fraction_heating,
                val AQI_pm10: AQI_pm10,
                val pm25_local_fraction_industry: pm25_local_fraction_industry,
                val pm10_local_fraction_industry: pm10_local_fraction_industry,
                val pm10_local_fraction_heating: pm10_local_fraction_heating,
                val no2_concentration: no2_concentration,
                val pm25_local_fraction_shipping: pm25_local_fraction_shipping,
                val AQI_no2: AQI_no2,
                val o3_concentration: o3_concentration,
                val pm25_concentration: pm25_concentration,
                val no2_local_fraction_traffic_exhaust: no2_local_fraction_traffic_exhaust,
                val pm10_local_fraction_traffic_exhaust: pm10_local_fraction_traffic_exhaust,
                val pm10_concentration: pm10_concentration,
                val pm25_local_fraction_traffic_exhaust: pm25_local_fraction_traffic_exhaust,
                val pm25_local_fraction_traffic_nonexhaust:pm25_local_fraction_traffic_nonexhaust,
                val pm10_nonlocal_fraction: pm10_nonlocal_fraction,
                val o3_nonlocal_fraction: o3_nonlocal_fraction,
                val pm10_local_fraction_shipping: pm10_local_fraction_shipping,
                val AQI_o3: AQI_o3,
                val AQI_pm25: AQI_pm25,
                val no2_local_fraction_shipping: no2_local_fraction_shipping,
                val no2_local_fraction_heating: no2_local_fraction_heating,
                val pm25_nonlocal_fraction: pm25_nonlocal_fraction,
                val no2_local_fraction_industry: no2_local_fraction_industry,
                val AQI: AQI,
                val pm10_local_fraction_traffic_nonexhaust: pm10_local_fraction_traffic_nonexhaust,
                val no2_nonlocal_fraction: no2_nonlocal_fraction)

class pm25_local_fraction_heating(val units: String, val value: Float)

class AQI_pm10(val units: String, val value: Float)

class pm25_local_fraction_industry(val units: String, val value: Float)

class pm10_local_fraction_industry(val units: String, val value: Float)

class pm10_local_fraction_heating(val units: String, val value: Float)

class no2_concentration(val units: String, var value: Float)

class pm25_local_fraction_shipping(val units: String, val value: Float)

class AQI_no2(val units: String, val value: Float)

class o3_concentration(val units: String, var value: Float)

class pm25_concentration(val units: String, var value: Float)

class no2_local_fraction_traffic_exhaust(val units: String, val value: Float)

class pm10_local_fraction_traffic_exhaust(val units: String, val value: Float)

class pm10_concentration(val units: String, var value: Float)

class pm25_local_fraction_traffic_exhaust(val units: String, val value: Float)

class pm25_local_fraction_traffic_nonexhaust(val units: String, val value: Float)

class pm10_nonlocal_fraction(val units: String, val value: Float)

class o3_nonlocal_fraction(val units: String, val value: Float)

class pm10_local_fraction_shipping(val units: String, val value: Float)

class AQI_o3(val units: String, val value: Float)

class AQI_pm25(val units: String, val value: Float)

class no2_local_fraction_shipping(val units: String, val value: Float)

class no2_local_fraction_heating(val units: String, val value: Float)

class pm25_nonlocal_fraction(val units: String, val value: Float)

class no2_local_fraction_industry(val units: String, val value: Float)

class AQI(val units: String, var value: Float)

class pm10_local_fraction_traffic_nonexhaust(val units: String, val value: Float)

class no2_nonlocal_fraction(val units: String, val value: Float)
