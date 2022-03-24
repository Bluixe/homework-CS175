package com.bluixe.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bluixe.translator.api.Youdao
import com.bluixe.translator.api.YoudaoChar
import com.bluixe.translator.api.YoudaoRes
import com.bluixe.translator.api.YoudaoSentence
import com.bluixe.translator.interceptor.TimeConsumeInterceptor
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var requestBtn: Button? = null
    private var showText: TextView? = null
    private var editText: EditText? = null
    private val client: OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(TimeConsumeInterceptor())
//        .eventListener(okhttpListener)
        .build()
    private val gson = GsonBuilder().create()
//    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//        Log.d("bluixelog","what")
//        if (event?.keyCode == KeyEvent.KEYCODE_ENTER) {
//            Log.d("bluixelog", "init")
//            translate()
//        }
//        return super.dispatchKeyEvent(event)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestBtn = findViewById(R.id.translate)
        showText = findViewById(R.id.show_text)
        editText = findViewById(R.id.edit)
        editText?.setOnKeyListener { view, i, keyEvent -> run {
            Log.d("bluixelog","what")
            if(keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.d("bluixelog", "init")
                translate()
                true
            } else {
                false
            }
        } }
        requestBtn?.setOnClickListener { translate() }
    }
    private fun updateShowTextView(text: String, append: Boolean = true) {
        if (Looper.getMainLooper() !== Looper.myLooper()) { //如果在子线程，则提交到主线程
            Log.d("bluixelog", "son")
            runOnUiThread { updateShowTextView(text, append) }
        } else {
            Log.d("bluixelog", "main")
            showText?.text = if (append) showText?.text.toString() + text else text
        }
    }
    private fun translate() {
//        var url = "https://movie.querydata.org/api?id=25845392"
        val url = "https://dict.youdao.com/jsonapi?q=${editText?.text}"
        Log.d("bluixelog", "onClick")
        val request = Request.Builder().url(url).build()
        // 异步请求
        client.newCall(request).enqueue(object : Callback {   // 添加到请求队列，添加回调函数（回调在主线程中运行）
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                updateShowTextView(e.message.toString(), false)
                Log.d("bluixelog", "failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body?.string()
//                var format = gson.fromJson(string, YoudaoChar::class.java)
                val responseText = if (response.isSuccessful) {
                    var res: String = "No data"
                    val detect = gson.fromJson(string, Youdao::class.java)
                    if("fanyi" in detect.meta.dicts) {
                        val format = gson.fromJson(string, YoudaoSentence::class.java)
                        res = format.fanyi.tran
                    } else if("blng_sents_part" in detect.meta.dicts) {
                        try {
                            val format = gson.fromJson(string, YoudaoChar::class.java)
                            res = format.blng_sents_part.trs[1].tr
                        } catch (e:Exception) {
                            res = "No data"
                        }
                    } else if(res === "No data" && "web_trans" in detect.meta.dicts){
                        try {
                            val format = gson.fromJson(string, YoudaoRes::class.java)
                            res = format.webTrans.webTranslation[0].value
                        } catch (e:Exception) {
                            res = "No data"
                        }
                    } else {
                        res = "No data"
                    }
//                    val bodyString = string
                    "\n${res}"
//                    val doubanBean = gson.fromJson(bodyString, DoubanBean::class.java)
//                    "\n\n\nOriginalname: ${doubanBean.originalName}\nAlias: ${doubanBean.alias}"
                } else {
                    "\n\n\nResponse fail: ${string}, http status code: ${response.code}."
                }
                Log.d("bluixelog", "${string}")
                updateShowTextView(responseText, false)
            }
        })
    }

}