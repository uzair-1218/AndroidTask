package com.example.androidtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.room.Entity
import de.hdodenhof.circleimageview.CircleImageView

class FavouriteAdapter() : RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private var drinks_list = emptyList<Entity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_signle_item, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val entity: Entity = drinks_list.get(position)

        holder.fav_image.setImageResource(R.drawable.ic_star_fill)
        holder.drink_name.text = entity.drink_name
        holder.drink_des.text = entity.drink_description
        if (entity.drink_alcoholic.contains("Non alcoholic")) {
            holder.checkBox.isChecked = false
        } else {
            holder.checkBox.isChecked = true
        }

        holder.image.setImageBitmap(entity.drink_image)


    }

    override fun getItemCount(): Int {
        return drinks_list.size
    }

    class FavouriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkBox: CheckBox = itemView.findViewById(R.id.alcoholic_check)
        val image: CircleImageView = itemView.findViewById(R.id.drink_image)
        val drink_name: TextView = itemView.findViewById(R.id.drink_name_txt)
        val drink_des: TextView = itemView.findViewById(R.id.drink_des)
        val fav_image: ImageView = itemView.findViewById(R.id.fav_img)

    }

    fun setData(entities: List<Entity>){
        this.drinks_list = entities

        notifyDataSetChanged()
    }
}