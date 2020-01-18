package com.example.imagesandvideo.ui.fragments

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.Fade
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import com.example.imagesandvideo.R
import com.example.imagesandvideo.navigation.AppNavigator
import com.example.imagesandvideo.viewmodel.FileViewModel
import kotlinx.android.synthetic.main.landing_fragment.*

class LandingFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: FileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.landing_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FileViewModel::class.java)

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.button1 ->
            {
                //navigate to folder view
                AppNavigator.navigate(R.id.f_container,this,FolderViewFragment(),null,true,FolderViewFragment::class.java.name)
            }



            R.id.button2 ->
            {
                //navigate to all files view
                AppNavigator.navigate(R.id.f_container,this,AllFilesListFragment(),null,true,AllFilesListFragment::class.java.name)

            }

        }
    }
}
