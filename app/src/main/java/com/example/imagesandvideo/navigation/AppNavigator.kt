package com.example.imagesandvideo.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.imagesandvideo.R

class AppNavigator {

    companion object
    {
        fun navigate(containerId :Int,f1:Fragment, f2 :Fragment, extra :Bundle?,addToBackStack :Boolean,tag :String)
        {

            var enterf2Anim= R.anim.slide_in_right
            var exitf1Anim=R.anim.slide_out_left
            var reEnterf1Anim= R.anim.slide_in_left
            var exitf2Anim=R.anim.slide_out_right
            f2.arguments=extra

            var manager=f1.activity?.supportFragmentManager
            var transcation=manager?.beginTransaction()
            transcation?.setCustomAnimations(enterf2Anim,exitf1Anim,reEnterf1Anim,exitf2Anim)
            transcation?.replace(containerId,f2,tag)
            if(addToBackStack)transcation?.addToBackStack(tag)
            transcation?.commitAllowingStateLoss()
        }
    }


}