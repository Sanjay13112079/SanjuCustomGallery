package com.example.imagesandvideo.ui.fragments

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.data.FileType
import com.example.imagesandvideo.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.file_details_fragment.*

class FileDetailsFragment: Fragment() {

    private lateinit var viewModel: FileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.file_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(FileViewModel::class.java)
        getData()
    }


    fun getData()
    {
        viewModel.fileData.observe(this, Observer<FileData>{
            it?.let {
                mapData(it)
            } ?:kotlin.run {  }
        })



    }


    fun mapData(fileData :FileData)
    {
        when(fileData?.type!!)
        {
            FileType.IMAGE.name ->{
                showImage(fileData)
            }

            FileType.VIDEO.name ->{
                playVideo(fileData)
            }
        }
    }





    fun showImage(data : FileData?)
    {
        fileDetails_Image.visibility=View.VISIBLE
        fileDetails_Video.visibility= View.GONE

        Glide.with(this)
            .load(data?.dataPath)
            .apply(RequestOptions().fitCenter())
            .into(fileDetails_Image)
    }



    fun playVideo(data : FileData?)
    {
        fileDetails_Image.visibility=View.GONE
        fileDetails_Video.visibility= View.VISIBLE

        fileDetails_Video?.setVideoPath(data?.dataPath)

        fileDetails_Video.setMediaController(object :MediaController(activity){
            override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
                if(event?.keyCode==KeyEvent.KEYCODE_BACK) activity?.supportFragmentManager?.popBackStackImmediate()
                return super.dispatchKeyEvent(event)
            }

            override fun setAnchorView(view: View?) {
                super.setAnchorView(fileDetails_container)
            }

            override fun show() {
                super.show()
            }
        })
        fileDetails_Video.start()
    }



}