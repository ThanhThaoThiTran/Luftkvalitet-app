package com.example.luftkvalitetalfa.service

class Measures {

    companion object {
        val shared = Measures()
    }

    fun calculatePM10(x: Double): Double {
        var aqi = 1.0
        aqi = when {
            x < 0.0 -> 1.0
            x < 60.0 -> (x / 60.0) + 1.0
            x < 120.0 -> ((x - 60.0) / (120.0 - 60.0)) + 2.0
            x < 400.0 -> ((x - 120.0) / (400.0 - 120.0)) + 3.0
            else -> (x / 400.0) + 3.0
        }
        if ( aqi > 4.999 ) {
            aqi = 4.999
        }
        return aqi
    }

    fun calculatePM25(x: Double): Double{
        var aqi = 1.0
        aqi = when {
            x < 0.0 -> 1.0
            x < 30.0 -> (x / 30.0) + 1.0
            x < 50.0 -> ((x - 30.0) / (50.0 - 30.0)) + 2.0
            x < 150.0 -> ((x - 50.0) / (150.0 - 50.0)) + 3.0
            else -> (x / 150.0) + 3.0
        }
        if ( aqi > 4.999 ) {
            aqi = 4.999
        }
        return aqi
    }


    fun calculateO3(x: Double) : Double {
        var aqi = 1.0
        aqi = when {
            x < 0.0 -> 1.0
            x < 100.0 -> (x / 100.0) + 1.0
            x < 180.0 -> ((x - 100.0) / (180.0 - 100.0)) + 2.0
            x < 240.0 -> ((x - 180.0) / (240.0 - 180.0)) + 3.0
            else -> (x / 240.0) + 3.0
        }
        if ( aqi > 4.999 ) {
            aqi = 4.999
        }
        return aqi
    }


    fun calculateNO3(x: Double) : Double {
        var aqi = 1.0
        aqi = when {
            x < 0.0 -> 1.0
            x < 100.0 -> (x / 100.0) + 1.0
            x < 200.0 -> ((x - 100.0) / (200.0 - 100.0)) + 2.0
            x < 400.0 -> ((x - 200.0) / (400.0 - 200.0)) + 3.0
            else -> (x / 400.0) + 3.0
        }
        if ( aqi > 4.999 ) {
            aqi = 4.999
        }
        return aqi
    }

    fun calculateAQI(x: Double) : Double {
        var aqi = 1.0
        aqi = when {
            x < 0.0 -> 1.0
            x < 2.0 -> (x / 2.0)
            x < 3.0 -> ((x - 2.0) / (3.0 - 2.0)) + 2.0
            x < 4.0 -> ((x - 3.0) / (4.0 - 3.0)) + 3.0
            else -> (x / 4.0) + 3.0
        }
        if (aqi > 4.999) {
            aqi = 4.999
        }
        return aqi
    }
}


/*

THE MEASURES USED IN THIS CLASS IS BASED UPON:
Data from The Norwegian Meteorological Institute
Who have decided the levels of the airquality index (AQI) for the Norwegian Climate

About the AirQuality Index classification:
https://luftkvalitet.miljostatus.no/artikkel/613#Forurensningsklasser

The formulas come directly from this API, under "aqi_formula":
https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/aqi_description


Data and products are licensed under Norwegian license for public data (NLOD)
and Creative Commons 4.0 BY Internasjonal.
http://creativecommons.org/licenses/by/4.0/

*/