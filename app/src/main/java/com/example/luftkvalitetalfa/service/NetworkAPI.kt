package com.example.luftkvalitetalfa.service
import com.example.luftkvalitetalfa.model.Station
import com.example.luftkvalitetalfa.model.Stations
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class NetworkAPI {

    companion object {
       val shared = NetworkAPI()
    }

    fun fetchStation(s: String, completion: (Station) -> Unit) {
        val url = "https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/?station=$s"

        val request = Request.Builder().url(url).header("User-Agent", "Gruppe25").build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val station = gson.fromJson(body, Station::class.java)

                completion(station)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch Station:  $s")
            }
        })
    }


    fun fetchStations(completion: (Array<Stations>) -> Unit) {
        val url = "https://in2000-apiproxy.ifi.uio.no/weatherapi/airqualityforecast/0.1/stations"

        val request = Request.Builder().url(url).header("User-Agent", "Gruppe25").build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val stations = gson.fromJson(body, Array<Stations>::class.java)

                completion(stations)
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch Stations")
            }
        })
    }
}


/*

THIRD PARTY HTTP WEB CLIENT USED IN THIS APP:
"OKHTTP"
https://square.github.io/okhttp/
Released under the Apache 2.0 license

Copyright 2016 Square, Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

/////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////

THIRD PARTY LIBRARY FOR CONVERTING JSON STRING INTO JAVA OBJECTS IN THIS APP:
"GSON"
https://github.com/google/gson
Released under the Apache 2.0 license

Copyright 2008 Google Inc.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


*/

