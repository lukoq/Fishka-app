package com.example.fishka.ui.study


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fishka.R
import com.example.fishka.model.Concept

class StudyAdapter() : RecyclerView.Adapter<StudyAdapter.Pager2ViewHolder>() {

    private var conceptList = mutableListOf<Concept>()
    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet


    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val itemTitleFront: TextView = itemView.findViewById(R.id.title_front)
        val itemTitleBack: TextView = itemView.findViewById(R.id.title_back)
        val scale: Float = itemView.context.resources.displayMetrics.density
        var isFront = true

        init {
            itemTitleFront.setOnClickListener {v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "You clicked ${position + 1}", Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyAdapter.Pager2ViewHolder {
        return Pager2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))
    }

    override fun onBindViewHolder(holder: StudyAdapter.Pager2ViewHolder, position: Int) {
        val currentItem = conceptList[position]
        holder.itemTitleFront.text = currentItem.frontSide
        holder.itemTitleBack.text = currentItem.backside

        holder.itemTitleFront.cameraDistance = 8000 * holder.scale
        holder.itemTitleBack.cameraDistance = 8000 * holder.scale


        holder.itemTitleFront.setOnClickListener{
            front_anim = AnimatorInflater.loadAnimator(holder.itemView.context, R.animator.front_animator) as AnimatorSet
            back_anim = AnimatorInflater.loadAnimator(holder.itemView.context, R.animator.back_animator) as AnimatorSet
            if(holder.isFront){
                front_anim.setTarget(holder.itemTitleFront)
                back_anim.setTarget(holder.itemTitleBack)
                front_anim.start()
                back_anim.start()
                holder.isFront = false
            }else{
                front_anim.setTarget(holder.itemTitleBack)
                back_anim.setTarget(holder.itemTitleFront)
                front_anim.start()
                back_anim.start()
                holder.isFront = true
            }
        }


    }


    override fun getItemCount(): Int {
        return conceptList.size
    }

    fun setData(concept: MutableList<Concept>){
        this.conceptList = concept
        notifyDataSetChanged()
    }




}