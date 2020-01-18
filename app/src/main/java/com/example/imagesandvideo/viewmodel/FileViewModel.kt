package com.example.imagesandvideo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.data.FileType
import com.example.imagesandvideo.data.FolderData
import com.example.imagesandvideo.recyclerview.ItemTypeHandler
import com.example.imagesandvideo.repo.CustomGalleryRepo
import com.example.imagesandvideo.recyclerview.FeedItem

class FileViewModel : ViewModel() {

    var repo :CustomGalleryRepo
    var folderList : MutableLiveData<List<FolderData>>
    var fileList :MutableLiveData<List<FileData>>

    var folderData :MutableLiveData<FolderData>
    var fileData :MutableLiveData<FileData>


    var totalList :MutableLiveData<List<FileData>>

    init {
        repo= CustomGalleryRepo()
        folderList=repo.folderList
        fileList=repo.filesList

        folderData=MutableLiveData()
        fileData=MutableLiveData()

        totalList= MutableLiveData()
    }


    fun fetchData()
    {
        repo.getData()
    }


    fun setFolderData(data :FolderData?)
    {
        folderData.value=data
    }


    fun setFileData(data :FileData)
    {
        fileData.value=data
    }


    //get feed data for the folder view
    fun getFolderFeedItemList(list :List<FolderData>) :List<FeedItem<*>>
    {
        var folderFeedList =ArrayList<FeedItem<*>>()

        for(item in list)
        {
            folderFeedList.add(
                FeedItem(
                    item,
                    ItemTypeHandler.ItemViewType.FOLDER_VH
                )
            )
        }

        return folderFeedList
    }


    //get feed data for files in a folder
    fun getFilesFeedItem(list :List<FileData>) :List<FeedItem<*>>
    {
        var folderFeedList =ArrayList<FeedItem<*>>()

        for(item in list)
        {
            folderFeedList.add(
                FeedItem(
                    item,
                    ItemTypeHandler.ItemViewType.FILE_VH
                )
            )
        }

        return folderFeedList
    }



    //get feed data for all files with every fifth no. specicf file
    fun getAllList() :MutableLiveData<List<FileData>>
    {

        fetchData()

        var allList=ArrayList<FileData>()
        var counter=0

        //add new imageurl at every fifth position
        for(file in fileList?.value!!)
        {
            allList.add(file)
            counter++
            if(counter%4==0)
            {
                var file1=FileData(FileType.IMAGE.name,"https://via.placeholder.com/360/360","")
                allList.add(file1)
            }
        }

        totalList.value=allList
        return totalList
    }

}
