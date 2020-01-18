package com.example.imagesandvideo.ui.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.recyclerview.GenericVH
import kotlinx.android.synthetic.main.picture_folder_item.view.*

class FileVH(itemView1 :View) : GenericVH<FileData>(itemView1) {

    var itemView2 :View?=null

    init {
        itemView2=itemView1
    }

    override fun bindData(data: FileData?) {

        Glide.with(App.context)
            .asBitmap()
            .load(data?.dataPath)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background))
            .into(itemView2?.vh_image!!)

        itemView2?.vh_container?.setOnClickListener(clickListner)
        itemView2?.vh_container?.setTag(R.id.uri,data)
    }
}