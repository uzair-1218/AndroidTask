package com.example.androidtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.networking.model.Drinks
import com.example.androidtask.utils.OnItemClicked
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class DrinksAdapter(val drinks_list:ArrayList<Drinks>,val item_click:OnItemClicked) : RecyclerView.Adapter<DrinksAdapter.DrinksViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_signle_item,parent,false)
        return DrinksViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinksViewHolder, position: Int) {
        val drink:Drinks = drinks_list.get(position)



        Picasso.get().load(drink.strDrinkThumb).into(holder.image)
        holder.drink_name.text = drink.strDrink
        holder.drink_des.text = drink.strInstructionsDE
        if ( drink.strAlcoholic!!.contains("Non alcoholic")){
            holder.checkBox.isChecked = false
        }else{
            holder.checkBox.isChecked = true
        }

        holder.fav_image.setOnClickListener {
         holder.fav_image.setImageResource(R.drawable.ic_star_fill)
         item_click.onClick(position)
        }


    }

    override fun getItemCount(): Int {
      return drinks_list.size
    }

    class DrinksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkBox:CheckBox = itemView.findViewById(R.id.alcoholic_check)
        val image:CircleImageView = itemView.findViewById(R.id.drink_image)
        val drink_name:TextView = itemView.findViewById(R.id.drink_name_txt)
        val drink_des:TextView = itemView.findViewById(R.id.drink_des)
        val fav_image:ImageView = itemView.findViewById(R.id.fav_img)

    }
}