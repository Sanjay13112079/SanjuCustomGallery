package com.example.imagesandvideo.recyclerview

import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesandvideo.App
import com.example.imagesandvideo.R
import com.example.imagesandvideo.data.FileType
import com.example.imagesandvideo.ui.viewholders.FileVH
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class AutoPlayRecyclerView :RecyclerView {


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    var thumbnail :ImageView?=null
    private var vhContainer: View? = null
    var videoContainer :FrameLayout?=null
    private var playerView: PlayerView? = null
    private var simpleExoPlayer: SimpleExoPlayer? = null

    private var isVideoViewAdded: Boolean = false

    init {
        playerView = PlayerView(App.context)
        playerView?.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)

        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // 2. Create the player
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Bind the player to the view.
        playerView?.setUseController(false)
        playerView?.setPlayer(simpleExoPlayer)



        //scroll listener
        addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //when no scrollinng
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    val startPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    var endPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()


                    if (startPosition < 0 || endPosition < 0) {
                        return
                    }

                    var currentPosition=0;

                    if(startPosition==0)
                    {
                        //initial views handling

                        var holderStartPosition = recyclerView?.findViewHolderForAdapterPosition(startPosition) as FileVH
                        videoContainer = holderStartPosition!!.itemView.findViewById(R.id.video_container)
                        val location = IntArray(2)
                        videoContainer?.getLocationInWindow(location)

                        if(location[1]>0)
                        {
                            currentPosition=startPosition
                        }
                        else
                        {
                            currentPosition=startPosition+1
                        }
                    }
                    else
                    {
                        //hanling case for in between of first and last
                        if (endPosition - startPosition > 1) {
                            currentPosition = startPosition + 1
                        }
                    }


                    var holder =
                        recyclerView?.findViewHolderForAdapterPosition(currentPosition) as FileVH

                    var fileData = holder?.data
                    if (fileData?.type.equals(FileType.VIDEO.name)) {
                        videoContainer =
                            holder!!.itemView.findViewById(R.id.video_container)
                        var layoutParams = videoContainer?.layoutParams
                        layoutParams?.height = holder!!.itemView2?.height
                        videoContainer?.layoutParams = layoutParams

                        if(isVideoViewAdded)resetVideoView()

                        thumbnail = (holder as FileVH)!!.itemView2?.findViewById(com.example.imagesandvideo.R.id.file_vh_image)
                        vhContainer = holder!!.itemView
                        playerView?.setPlayer(simpleExoPlayer)

                        if (!recyclerView.canScrollVertically(1)) {
                            playVideo(true, currentPosition, holder,fileData?.dataPath)
                        } else {
                            playVideo(false, currentPosition, holder,fileData?.dataPath)
                        }
                    }
                    if (fileData?.type.equals(FileType.IMAGE.name)) {
                        if(isVideoViewAdded)resetVideoView()
                    }


                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


            }
        })

        //player event listener
        simpleExoPlayer?.addListener(object : Player.EventListener {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {

                    Player.STATE_ENDED -> {
                        simpleExoPlayer?.seekTo(0)
                    }
                }
            }

        })

    }


    fun playVideo(isLastView: Boolean, currentPosition:Int, holder :RecyclerView.ViewHolder, mediaUrl:String?) {


        if (!isLastView ) {

            if(!isVideoViewAdded) addVideoView()

            val dataSourceFactory = DefaultDataSourceFactory(
                context, Util.getUserAgent(context, "RecyclerView VideoPlayer")
            )

            if (mediaUrl != null) {
                val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mediaUrl))
                simpleExoPlayer?.prepare(videoSource)
                simpleExoPlayer?.setPlayWhenReady(true)
            }
        }
    }

    private fun removeVideoView(videoView: PlayerView?) {


        val parent = videoView?.parent as ViewGroup ?: return

        val index = parent.indexOfChild(videoView)
        if (index >= 0) {
            parent.removeViewAt(index)
            isVideoViewAdded = false
            simpleExoPlayer?.stop()
            vhContainer?.setOnClickListener(null)
        }

    }

    private fun addVideoView() {
        videoContainer?.addView(playerView)
        playerView?.visibility=View.VISIBLE
        thumbnail?.setVisibility(View.INVISIBLE)
        isVideoViewAdded = true
        playerView?.requestFocus()
        playerView?.setVisibility(VISIBLE)
        playerView?.setAlpha(1f)
        thumbnail?.setVisibility(GONE)
    }


    private fun resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(playerView)
            playerView?.setVisibility(View.GONE)
            thumbnail?.setVisibility(VISIBLE)
            isVideoViewAdded=false
        }
    }

    fun releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer?.release()
            simpleExoPlayer = null
        }
        vhContainer = null
    }



    }
