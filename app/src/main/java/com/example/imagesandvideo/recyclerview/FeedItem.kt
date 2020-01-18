package com.example.imagesandvideo.recyclerview

/**
 * generic data item for Recyclerview
 */
class FeedItem<T> {

     var data :T?=null
     var itemViewType : ItemTypeHandler.ItemViewType?=null

    constructor(data: T?, type: ItemTypeHandler.ItemViewType?) {
        this.data = data
        this.itemViewType = type
    }
}