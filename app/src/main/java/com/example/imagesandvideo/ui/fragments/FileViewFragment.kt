package com.example.imagesandvideo.ui.fragments

import android.os.Bundle
import android.os.Parcelable
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.data.FolderData
import com.example.imagesandvideo.navigation.AppNavigator
import com.example.imagesandvideo.recyclerview.GenericRVAdapter
import com.example.imagesandvideo.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.fragment_folder_view.*

class FileViewFragment: Fragment(),View.OnClickListener {

    private lateinit var viewModel: FileViewModel
    private lateinit var mLayoutManager :GridLayoutManager
    companion object{
        var rvState : Parcelable?=null

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_folder_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(FileViewModel::class.java)
        initViews()
        getData()
    }

    fun initViews()
    {
        mLayoutManager=GridLayoutManager(App.context, 3)
        recview?.layoutManager = mLayoutManager
    }


    fun getData()
    {
        viewModel.folderData.observe(this,Observer<FolderData>{
            it?.let {
              it.fileList.let {
                  fileList-> if(fileList!=null)mapData(fileList)
              }
            }?:kotlin.run {  }
        })
    }

    fun mapData(fileList :ArrayList<FileData>)
    {
        if(!fileList.isEmpty())
        {
            var adapter= GenericRVAdapter(viewModel.getFilesFeedItem(fileList), this)
            recview.adapter=adapter
        }
    }

    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.file_vh_container ->
            {
                var data=v?.getTag(R.id.uri) as FileData
                viewModel.setFileData(data)

                AppNavigator.navigate(R.id.f_container,this,FileDetailsFragment(),null,true,FileDetailsFragment::class.java.name)

            }
        }
    }


    override fun onResume() {
        super.onResume()
        if(rvState!=null) mLayoutManager.onRestoreInstanceState(rvState)
    }

    override fun onPause() {
        super.onPause()
        if(rvState!=null) rvState =mLayoutManager.onSaveInstanceState()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        rvState=null
    }
}