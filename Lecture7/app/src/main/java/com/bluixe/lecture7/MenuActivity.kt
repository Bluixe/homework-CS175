package com.bluixe.lecture7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        findViewById<View>(R.id.image).setOnClickListener {
            val intent = Intent().setClass(this,MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.video).setOnClickListener {
            val intent = Intent().setClass(this, VideoActivity::class.java)
            startActivity(intent)
        }
    }
}