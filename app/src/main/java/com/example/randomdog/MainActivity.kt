package com.example.randomdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.randomdog.api.ApiRequest
import com.example.randomdog.api.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var ImageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ImageView = findViewById(R.id.img)
    }

    fun update(view: View) {
        val fab = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)
        fab.animate().rotationBy(360f).apply {
            duration=1000
            start()
        }
        makeApiRequest()
    }
    fun makeApiRequest(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = api.getRandomDog()
                if (response.fileSizeBytes <400000) {
                    withContext(Dispatchers.Main){
                       Glide.with(applicationContext).load(response.url).into(ImageView)
                    }
                }
                else
                    makeApiRequest()
            }
            catch (e:Exception){
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }
}