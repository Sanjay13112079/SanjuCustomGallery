package com.example.imagesandvideo.data

import android.os.Parcel
import android.os.Parcelable


data class FileData(
    var type : String?,
    var dataPath : String?,
    var lastModified : String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(dataPath)
        parcel.writeString(lastModified)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FileData> {
        override fun createFromParcel(parcel: Parcel): FileData {
            return FileData(parcel)
        }

        override fun newArray(size: Int): Array<FileData?> {
            return arrayOfNulls(size)
        }
    }
}