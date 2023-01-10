package com.example.fishka.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fishka.R
import com.example.fishka.model.Concept
import kotlinx.android.synthetic.main.custom_row.view.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var conceptList = emptyList<Concept>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = conceptList[position]
        holder.itemView.frontSide_txt.text = currentItem.frontSide
        holder.itemView.backSide_txt.text = currentItem.backside

        holder.itemView.rowLayout.setOnClickListener(){
            val action = HomeFragmentDirections.actionNavHomeToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return conceptList.size
    }

    fun setData(concept: List<Concept>){
        this.conceptList = concept
        notifyDataSetChanged()
    }

}