package com.example.luftkvalitetalfa.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.luftkvalitetalfa.R

class PartiklerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_partikler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("On view created", "Open")

        val partiklSveve = view.findViewById<Button>(R.id.btn_partklSveve)
        val partiklNitro = view.findViewById<Button>(R.id.btn_partklNitro)
        val partiklKarb = view.findViewById<Button>(R.id.btn_partklKarb)
        val partiklSvov = view.findViewById<Button>(R.id.btn_partklSvov)
        val partiklOz = view.findViewById<Button>(R.id.btn_partklOz)
        val partiklBenz = view.findViewById<Button>(R.id.btn_partklBenz)

        partiklSveve.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = SvevestovFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange)
                .addToBackStack(null)
                .commit()
        }

        partiklBenz.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = BenzenFragment()

            fragmenTransaction.replace(R.id.info_fragment, fragChange)
            fragmenTransaction.addToBackStack(null)
            fragmenTransaction.commit()

        }

        partiklKarb.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = KarbFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange)
                .addToBackStack(null)
                .commit()
        }

        partiklOz.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = OzonFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange)
                .addToBackStack(null)
                .commit()
        }

        partiklNitro.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = NitroFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange)
                .addToBackStack(null)
                .commit()
        }

        partiklSvov.setOnClickListener {
            val fragmentManager  = this.fragmentManager
            val fragmenTransaction = fragmentManager!!.beginTransaction()
            val fragChange = SvovelFragment()

            fragmenTransaction
                .replace(R.id.info_fragment, fragChange)
                .addToBackStack(null)
                .commit()
        }
    }
}