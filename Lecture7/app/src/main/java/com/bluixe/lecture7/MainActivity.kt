package com.bluixe.lecture7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class MainActivity : AppCompatActivity() {
    private val pages: MutableList<View> = ArrayList()
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.media_view_pager)
        addImage("https://raw.githubusercontent.com/Bluixe/cloudimg/main/1650601752693.jpg")
        addImage("https://raw.githubusercontent.com/Bluixe/cloudimg/main/1648013264238.jpg")
        addImage("https://raw.githubusercontent.com/Bluixe/cloudimg/main/1648033376072.jpg")
        addImage("https://raw.githubusercontent.com/Bluixe/cloudimg/main/1650559931937.jpg")
        addImage("https://raw.githubusercontent.com/Bluixe/cloudimg/main/1650560050091.jpg")
        val adapter = ViewAdapter()
        adapter.setDatas(pages)
        viewPager.adapter = adapter
    }

    private fun addImage(path: String) {
        val imageView =
            layoutInflater.inflate(R.layout.activity_base_image, null) as ImageView
        Glide.with(this)
            .load(path)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            .into(imageView)
        pages.add(imageView)
    }
}