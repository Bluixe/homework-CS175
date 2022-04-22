package com.bluixe.lecture7

import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        findView()

    }
    private fun findView(){
        val videoView: CustomVideo = findViewById(R.id.videoView)
        findViewById<View>(R.id.buttonPlay).setOnClickListener { videoView.start() }
        findViewById<View>(R.id.buttonPause).setOnClickListener { videoView.pause() }
        findViewById<View>(R.id.buttonReplay).setOnClickListener { videoView.resume() }
        videoView.setVideoPath(getVideoPath(R.raw.tsh))
        videoView.setMediaController(MediaController(this))
    }

    private fun getVideoPath(resId: Int): String {
        return "android.resource://" + this.packageName + "/" + resId
    }

    @Override
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        Log.d("bluixe","here")
        setContentView(R.layout.activity_video)
        findView()
        val ori = newConfig.orientation
        if (ori == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("bluixe","land")
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
            supportActionBar?.hide()
//            actionBar?.hide()
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.statusBarColor = Color.TRANSPARENT;
        } else {
            Log.d("bluixe","port")
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            supportActionBar?.show()
//            actionBar?.show()
//            window.statusBarColor = Color.BLACK;
        }
    }
}