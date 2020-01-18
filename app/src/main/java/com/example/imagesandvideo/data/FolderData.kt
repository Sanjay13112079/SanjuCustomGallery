package com.example.imagesandvideo.data

import android.os.Parcel
import android.os.Parcelable

data class FolderData (
    var folderPath :String?,
    var folderName :String?,
    var folderPicPath :String?,
    var fileList :ArrayList<FileData>?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(FileData)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(folderPath)
        parcel.writeString(folderName)
        parcel.writeString(folderPicPath)
        parcel.writeTypedList(fileList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FolderData> {
        override fun createFromParcel(parcel: Parcel): FolderData {
            return FolderData(parcel)
        }

        override fun newArray(size: Int): Array<FolderData?> {
            return arrayOfNulls(size)
        }
    }
}