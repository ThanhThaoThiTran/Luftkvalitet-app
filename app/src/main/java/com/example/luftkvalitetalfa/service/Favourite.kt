package com.example.luftkvalitetalfa.service

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.example.luftkvalitetalfa.model.Station

class Favourite {

    val favorittListe = ArrayList<Station>()

    companion object {
        val shared = Favourite()
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