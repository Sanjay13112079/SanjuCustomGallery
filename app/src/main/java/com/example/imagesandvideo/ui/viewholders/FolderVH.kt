package com.example.imagesandvideo.ui.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FolderData
import com.example.imagesandvideo.recyclerview.GenericVH
import kotlinx.android.synthetic.main.picture_folder_item.view.*

class FolderVH(itemView :View) : GenericVH<FolderData>(itemView) {

    var itemView1 :View
    init {
        itemView1=itemView
    }

    override fun bindData(data: FolderData?) {
        Glide.with(App.context)
            .load(data?.folderPicPath)
            .apply(RequestOptions().centerCrop())
            .into(itemView1.vh_image)

        itemView1?.vh_container?.setOnClickListener(clickListner)
        itemView1?.vh_container?.setTag(R.id.uri,data)
    }
}