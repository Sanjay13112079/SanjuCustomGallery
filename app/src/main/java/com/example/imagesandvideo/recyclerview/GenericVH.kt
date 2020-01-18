package com.example.imagesandvideo.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class GenericVH<T>(itemView :View) :RecyclerView.ViewHolder(itemView) {

    var clickListner :View.OnClickListener?=null

    abstract fun bindData(data :T?)


    fun setClickListener(listner :View.OnClickListener?)
    {
        clickListner=listner
    }



}