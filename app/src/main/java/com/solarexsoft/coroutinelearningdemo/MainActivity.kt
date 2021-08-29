package com.solarexsoft.coroutinelearningdemo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.solarexsoft.coroutinelearningdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener {
            display()
        }
    }

    fun display() {
        runBlocking {
            launch(Handler(Looper.getMainLooper()).asCoroutineDispatcher()) {
                val defer = async(AndroidCommonPool) {
                    val okHttpClient = OkHttpClient()
                    val request = Request.Builder().url("http://www.baidu.com").get().build()
                    val body = okHttpClient.newCall(request).execute().body
                    body?.string()
                }
                binding.tv.text = defer.await()
            }
        }
    }
}