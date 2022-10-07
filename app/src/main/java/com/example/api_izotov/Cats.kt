package com.example.api_izotov

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.jvm.Throws
import kotlin.random.Random

class Cats : AppCompatActivity() {
    private lateinit var ib : ImageButton
    private lateinit var btn1 : Button
    private lateinit var imCats : ImageView
    private var rand1 = Random(2)

    var handler = Handler(Looper. getMainLooper())
    var image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cats)


        //https://api.thecatapi.com/v1/images/search
        //https://cdn2.thecatapi.com/images/1.jpg

        btn1 = findViewById(R.id.bn)
        imCats = findViewById(R.id.iw)


        val exec : ExecutorService = Executors.newSingleThreadExecutor()
        val e : ExecutorService = Executors.newSingleThreadExecutor()

        btn1.text = "Random cat"

        ib = findViewById(R.id.iB)

        btn1.setOnClickListener{

            Log.d("qwe", "qwe")

            var temp = exec.submit(Callable {
                 http("https://aws.random.cat/meow")
            }).get()

            Log.d("zxc", temp)

            var cat : JsonCats = Gson().fromJson(temp,JsonCats::class.java)

            e.execute{
                val imageURL = cat.file
                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        imCats.setImageBitmap(image)
                    }
                Log.d("asd", "done")
               }
               catch (e: Exception) {
                   e.printStackTrace()
               }
            }
        }

        ib.setOnClickListener {
            super.onBackPressed()
        }

    }
    fun rand(from: Int, to: Int): Int {
        return rand1.nextInt(to - from) + from
    }

    @Throws(IOException::class)
    fun http(url:String):String{
        val url1 = URL(url)
        val con = url1.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        var data: Int = con.inputStream.read()
        var str = ""
        while (data != -1)
        {
            str += data.toChar()
            data = con.inputStream.read()
        }
        return str
    }
}