package com.example.rupeekassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rupeekassignment.R
import com.example.rupeekassignment.extensions.logDebug
import com.example.rupeekassignment.model.Datum
import kotlinx.android.synthetic.main.item_list.view.*


class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    private val list = ArrayList<Datum>()

    fun addImages(tempList : ArrayList<Datum>) {
        list.addAll(tempList)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val obj = list[position]
        holder.bindObject(obj)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ImageHolder(
            inflater.inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    class ImageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        fun bindObject(obj : Datum) {
            Glide.with(itemView)
                .load(obj.url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.itemImage)
                .waitForLayout()

            itemView.itemName.text = obj.place
        }
    }
}