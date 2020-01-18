package com.example.imagesandvideo.navigation

import android.os.Bundle
import android.transition.Fade
import androidx.fragment.app.Fragment

class AppNavigator {

    companion object
    {
        fun navigate(containerId :Int,f1:Fragment, f2 :Fragment, extra :Bundle?,addToBackStack :Boolean,tag :String)
        {
            f2.enterTransition=Fade()
            f2.exitTransition=Fade()
            f2.arguments=extra

            var manager=f1.activity?.supportFragmentManager
            var transcation=manager?.beginTransaction()
            transcation?.replace(containerId,f2,tag)
            if(addToBackStack)transcation?.addToBackStack(tag)
            transcation?.commit()
        }
    }


}