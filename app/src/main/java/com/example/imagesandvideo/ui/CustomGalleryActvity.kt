package com.example.imagesandvideo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.imagesandvideo.R
import com.example.imagesandvideo.ui.fragments.LandingFragment

class CustomGalleryActvity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_gallery_actvity)

        navigateToLandingFragment()
    }


    fun navigateToLandingFragment()
    {
        var landingFragment= LandingFragment()
        var fragmentManager=supportFragmentManager
        var transcation=fragmentManager.beginTransaction()
        transcation.add(R.id.f_container,landingFragment,
            LandingFragment::class.java.name)
        transcation.commit()

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
