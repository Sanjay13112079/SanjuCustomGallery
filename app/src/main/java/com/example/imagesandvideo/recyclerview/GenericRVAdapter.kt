package com.example.imagesandvideo.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesandvideo.App

class GenericRVAdapter :RecyclerView.Adapter<GenericVH<Any>> {

    var itemList :List<FeedItem<*>>?=null
    var clickListner :View.OnClickListener?=null

    constructor(
        itemList: List<FeedItem<*>>?,
        clickContext: View.OnClickListener?
    ) {
        this.itemList = itemList
        this.clickListner = clickContext
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericVH<Any> {
        var holder= ItemTypeHandler.createViewHolder(LayoutInflater.from(App.context), parent, viewType)
        return holder!!
    }

    override fun onBindViewHolder(holder: GenericVH<Any>, position: Int) {
        holder.setClickListener(clickListner)
        holder.bindData(itemList?.get(position)?.data)
    }

    override fun getItemCount(): Int {
        itemList?.let {
            return it?.size
        } ?: kotlin.run {
            return 0
        }
    }

    override fun getItemViewType(position: Int): Int {
        var item=itemList?.get(position) as FeedItem<*>
        if(item!=null && item.itemViewType!=null && item.itemViewType!!.id!=null) return item.itemViewType!!.id!!
        else return -1
    }


    fun getFeeditemAtPosition(position: Int) :FeedItem<*>
    {
        return itemList?.get(position)!!
    }



}