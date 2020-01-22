package com.example.imagesandvideo.ui.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileData
import com.example.imagesandvideo.navigation.AppNavigator
import com.example.imagesandvideo.recyclerview.GenericRVAdapter
import com.example.imagesandvideo.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.all_files_fragment.*

class AllFilesListFragment :Fragment(),View.OnClickListener {

    private lateinit var viewModel: FileViewModel
    private lateinit var mLayoutManager :LinearLayoutManager


    companion object{
        var rvState :Parcelable?=null

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.all_files_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(FileViewModel::class.java)
        initViews()
        checkpermission()

    }

    fun initViews()
    {
        mLayoutManager=LinearLayoutManager(activity)
        cus_recview?.layoutManager=mLayoutManager
    }

    //run time permission
    fun checkpermission()
    {
        if(ContextCompat.checkSelfPermission(App.context,android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1009)
        }
        else
        {
            setUpObservers()
        }
    }


    fun setUpObservers()
    {
        viewModel?.getAllList()?.observe(this,Observer<List<FileData>>{
            it?.let {
                mapData(it)
            }?:kotlin.run {

            }
        })


    }

    //map the UI
    fun mapData(allFilesList :List<FileData>)
    {
        if(!allFilesList.isEmpty())
        {
            var adapter= GenericRVAdapter(viewModel.getFilesFeedItem(allFilesList), this)
            cus_recview?.adapter=adapter
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if (requestCode == 1009) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpObservers()
            }
            else
            {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.file_vh_container ->
            {
                cus_recview?.releasePlayer()

                var data=v?.getTag(R.id.uri) as FileData
                viewModel.setFileData(data)

                AppNavigator.navigate(R.id.f_container,this,FileDetailsFragment(),null,true,FileDetailsFragment::class.java.name)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(rvState!=null)mLayoutManager.onRestoreInstanceState(rvState)
    }

    override fun onPause() {
        super.onPause()
        if(rvState!=null)rvState =mLayoutManager.onSaveInstanceState()
    }

    override fun onDestroyView() {

        rvState=null
        cus_recview?.releasePlayer()
        super.onDestroyView()
    }





}