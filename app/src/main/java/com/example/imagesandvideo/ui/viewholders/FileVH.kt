package com.example.imagesandvideo.ui.viewholders

import android.media.MediaPlayer
import android.view.KeyEvent
import android.view.View
import android.widget.MediaController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.data.FileType
import com.example.imagesandvideo.recyclerview.GenericVH
import kotlinx.android.synthetic.main.picture_file_vh.view.*

class FileVH(itemView1 :View) : GenericVH<FileData>(itemView1) {

    var itemView2 :View?=null
    var data :FileData?=null

    init {
        itemView2=itemView1
    }

    override fun bindData(data: FileData?) {



                showImage(data)



    }

    fun showImage(data: FileData?)
    {
        this.data=data
        itemView2?.file_vh_image?.visibility=View.VISIBLE

        Glide.with(App.context)
            .asBitmap()
            .load(data?.dataPath)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background))
            .into(itemView2?.file_vh_image!!)

        itemView2?.file_vh_container?.setOnClickListener(clickListner)
        itemView2?.file_vh_container?.setTag(R.id.uri,data)
    }

}