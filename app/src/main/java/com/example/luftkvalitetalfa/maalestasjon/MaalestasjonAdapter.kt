package com.example.luftkvalitetalfa.maalestasjon

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.luftkvalitetalfa.model.Element
import com.example.luftkvalitetalfa.R

class MaalestasjonAdapter (private val positionList : ArrayList<Element>) : RecyclerView.Adapter<MaalestasjonAdapter.ViewHolderPosition>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPosition {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_elementposition, parent, false)
        return ViewHolderPosition(view)
    }

    override fun getItemCount(): Int {
        return positionList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolderPosition, position: Int) {
        val element: Element = positionList[position]

        holder.itemTitle.text = element.title
        holder.itemDescription.text = element.description
        holder.itemColorValue.setBackgroundColor(Color.parseColor(element.areacode))
    }

    class ViewHolderPosition (itemView : View)  : RecyclerView.ViewHolder(itemView) {
        val itemTitle = itemView.findViewById<TextView>(R.id.text_title_position)!!
        val itemDescription = itemView.findViewById<TextView>(R.id.text_description_position)!!
        val itemColorValue = itemView.findViewById<View>(R.id.color_view)!!

    }


}
