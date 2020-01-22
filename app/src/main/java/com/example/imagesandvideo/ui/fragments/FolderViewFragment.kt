package com.example.imagesandvideo.ui.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FolderData
import com.example.imagesandvideo.navigation.AppNavigator
import com.example.imagesandvideo.recyclerview.GenericRVAdapter
import com.example.imagesandvideo.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.fragment_folder_view.*

class FolderViewFragment: Fragment(),View.OnClickListener {

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
        checkpermission()

    }


    fun initViews()
    {
        mLayoutManager=GridLayoutManager(App.context, 2)
        recview?.layoutManager = mLayoutManager
    }


    //run time permission
    fun checkpermission()
    {
       if(ContextCompat.checkSelfPermission(App.context,android.Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED)
       {
           requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1008)
       }
        else
       {
           setUpObservers()
           viewModel.fetchData()
       }
    }


    //setting up observer for folder level data
    fun setUpObservers()
    {
        viewModel.folderList.observe(this,Observer<List<FolderData>>{
            it?.let {
                mapData(it)

            }?:kotlin.run {  }
        })

    }


    //mapping data in RV
    fun mapData(folderList :List<FolderData>)
    {
        if(!folderList.isEmpty())
        {
            var adapter = GenericRVAdapter(viewModel.getFolderFeedItemList(folderList), this)
            recview?.adapter = adapter
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        if (requestCode == 1008) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpObservers()
                viewModel.fetchData()
            }
            else
            {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        super.onResume()
        if(rvState!=null)mLayoutManager.onRestoreInstanceState(rvState)
    }


    override fun onPause() {
        super.onPause()
        if(rvState!=null)rvState =mLayoutManager.onSaveInstanceState()
    }




    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.folder_vh_container ->
            {
                //set clicked Folder data in viewmodel
                var data=v?.getTag(R.id.uri) as FolderData
                viewModel.setFolderData(data)

                AppNavigator.navigate(R.id.f_container,this,FileViewFragment(),null,true,FileViewFragment::class.java.name)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        rvState=null
    }
}