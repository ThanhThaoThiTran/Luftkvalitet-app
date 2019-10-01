package com.example.luftkvalitetalfa.favoritt

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.luftkvalitetalfa.R
import com.example.luftkvalitetalfa.model.Station
import com.example.luftkvalitetalfa.service.Favourite


class FavorittAdapter (private val favorittListe : ArrayList<Station>) : RecyclerView.Adapter<FavorittAdapter.ViewHolder>() {
    var vg : ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_element, parent, false)
        vg = parent
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favorittListe.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val s: Station = favorittListe[position]
        val time : Int = Favourite.shared.getTime()


        holder.itemTitle.text = s.meta.location.name
        holder.itemDescription.text = s.getStatus(time)
        holder.itemColor.setBackgroundColor(Color.parseColor(s.getColor(time)))


        holder.itemDelete.setOnClickListener {
            removeItem(position)
            makeToast("Fjerner " + holder.itemTitle.text + " fra favoritter")
        }
    }

    private fun makeToast(text : String){
        Toast.makeText(vg!!.context, text, Toast.LENGTH_LONG).show()
    }

    private fun removeItem(position: Int) {
        favorittListe.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder (itemView : View)  : RecyclerView.ViewHolder(itemView) {
        val itemTitle = itemView.findViewById<TextView>(R.id.text_title_favs)!!
        val itemDescription = itemView.findViewById<TextView>(R.id.text_description_favs)!!
        val itemDelete = itemView.findViewById<ImageButton>(R.id.btn_deleteFavorite)!!
        val itemColor = itemView.findViewById<View>(R.id.color_view_favs)
    }


}
