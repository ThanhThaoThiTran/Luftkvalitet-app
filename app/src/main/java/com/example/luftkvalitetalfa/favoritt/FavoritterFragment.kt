package com.example.luftkvalitetalfa.favoritt

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.luftkvalitetalfa.R
import com.example.luftkvalitetalfa.service.Favourite


class FavoritterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val elements = Favourite.shared.favorittListe

        val view: View = inflater.inflate(R.layout.fragment_favoritter, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            val adapter = FavorittAdapter(elements)
            recyclerView.adapter = adapter
        }
        return view
    }
}
