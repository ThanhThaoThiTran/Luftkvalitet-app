package com.example.luftkvalitetalfa.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.luftkvalitetalfa.R

class InfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val partikler = view.findViewById<Button>(R.id.btn_infoPartikler)
        val helse = view.findViewById<Button>(R.id.btn_infoHelse)
        val kilder = view.findViewById<Button>(R.id.btn_infoKilder)
        val tiltak = view.findViewById<Button>(R.id.btn_infoTiltak)

        partikler.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = PartiklerFragment()

            childFragmentManager.beginTransaction().apply {
                replace(R.id.info_fragment, fragChange)
                commit()
            }

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange, "PartiklerFragment")
                .addToBackStack(null)
                .commit()
        }

        helse.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = HelseFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange, "HelseFragment")
                .addToBackStack(null)
                .commit()
        }

        tiltak.setOnClickListener {
            Log.e("Tiltak", " --- > Kjører!!")
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = TiltakFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange, "TiltakFragment")
                .addToBackStack(null)
                .commit()
        }

        kilder.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = KilderFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange, "KildeFragment")
                .addToBackStack(null)
                .commit()
        }
    }
}