package com.example.imagesandvideo.repo

import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.loader.content.CursorLoader
import com.example.imagesandvideo.App
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.data.FileType
import com.example.imagesandvideo.data.FolderData
import java.net.URLConnection

class CustomGalleryRepo {

    //each folder containig its own files list
    var folderList :MutableLiveData<List<FolderData>>

    //all files (image, video)
    var filesList :MutableLiveData<List<FileData>>


    init {
        folderList=MutableLiveData()
        filesList=MutableLiveData()
    }



    //get all the data(image , video files)
    fun getData()
    {

        var folderDataList=ArrayList<FolderData>()
        var fileDataList =ArrayList<FileData>()

        var cursor=getCursorForImageVideoFIle()

        var folderPathList=ArrayList<String>()

        if(cursor!=null) {

            cursor.moveToFirst()

            do {

                var folderName = cursor.getString (cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                var datapath =   cursor.getString (cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                var folderPath=  datapath.substring(0, datapath.lastIndexOf(folderName+"/"))+folderName+"/"
                var dateAdded=   cursor.getString (cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))


                //creating fileData
                var fileData= FileData("", datapath, dateAdded)
                fileDataList.add(fileData)

                var mimeType= URLConnection.guessContentTypeFromName(datapath)

                if(mimeType.startsWith(FileType.VIDEO.name.toLowerCase()))
                {
                    fileData.type= FileType.VIDEO.name
                }

                if(mimeType.startsWith(FileType.IMAGE.name.toLowerCase()))
                {
                    fileData.type= FileType.IMAGE.name
                }


                //ffiltering data folderwise
                if(!folderPathList.contains(folderPath))
                {
                    folderPathList.add(folderPath)

                    var list=ArrayList<FileData>()
                    list.add(fileData)
                    var folderData= FolderData(folderPath,folderName,datapath,list)
                    folderDataList.add(folderData)

                }
                else
                {
                    for(folderData in folderDataList)
                    {
                        if(folderData.folderPath.equals(folderPath))
                        {
                            folderData.fileList?.add(fileData)
                        }
                    }
                }

            } while (cursor.moveToNext())
        }

        folderList.value=folderDataList
        filesList.value=fileDataList

    }



    // get the cursor for image and video files
    fun getCursorForImageVideoFIle() :Cursor?
    {
        //uri
        var imageVideosUri= MediaStore.Files.getContentUri("external")

        //selecting coloumns
        var projection = arrayOf(
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
        )


        //selecting rows
        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)

        //sorting rows
        val sort= MediaStore.Files.FileColumns.DATE_ADDED

        var cursorLoader= CursorLoader(App.context,imageVideosUri,projection,selection,null,sort)

        var cursor=cursorLoader.loadInBackground()

        return cursor
    }
}