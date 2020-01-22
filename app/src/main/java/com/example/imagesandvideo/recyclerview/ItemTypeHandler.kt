package com.example.imagesandvideo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.imagesandvideo.R
import com.example.imagesandvideo.ui.viewholders.FileVH
import com.example.imagesandvideo.ui.viewholders.FolderVH

class ItemTypeHandler {

    //using enum to classify viewholder types
     enum class ItemViewType(id :Int)
     {
         FOLDER_VH(0),
         FILE_VH(1);
         var id :Int?=null

         init {
             this.id=id
         }
     }

     companion object
     {
         fun getType(id :Int) :Int?
         {
             var itemtypeArray=
                 ItemViewType.values()
             for(itemType in itemtypeArray)
             {
                 if(itemType?.id==id) return itemType.id
             }

             return null
         }

     //create view holder for recycler view
     fun createViewHolder (inflater :LayoutInflater,parent :ViewGroup,type :Int? ): GenericVH<Any>?
     {

             if(type==null) return null
             var viewHolder : GenericVH<Any>?=null

             when(type)
             {
                 ItemViewType.FOLDER_VH.id ->
                 {
                     viewHolder=FolderVH(inflater.inflate(R.layout.picture_folder_item,parent,false)) as GenericVH<Any>
                 }

                 ItemViewType.FILE_VH.id ->
                 {
                     viewHolder=FileVH(inflater.inflate(R.layout.picture_file_vh,parent,false)) as GenericVH<Any>
                 }
             }

             return viewHolder
         }
     }





}