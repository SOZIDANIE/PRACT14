package com.example.api_izotov

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.google.gson.Gson
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.jvm.Throws

class Room : AppCompatActivity() {
    private lateinit var ib2 : ImageButton
    private lateinit var iw : ImageView
    private lateinit var b2 : Button
    var image: Bitmap? = null
    var handler = Handler(Looper. getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        //https://randomfox.ca/floof/

        iw = findViewById(R.id.iw2)
        b2 = findViewById(R.id.bn2)
        ib2 = findViewById(R.id.iB3)

        val exec : ExecutorService = Executors.newSingleThreadExecutor()
        val e : ExecutorService = Executors.newSingleThreadExecutor()


        b2.setOnClickListener {
            val temp = exec.submit(Callable {
                http("https://randomfox.ca/floof/")
            }).get()

            val fox: JsonFox = Gson().fromJson(temp,JsonFox::class.java)

            e.execute{
                val imageFox = fox.image
                try {
                    val a = java.net.URL(imageFox).openStream()
                    image = BitmapFactory.decodeStream(a)
                    handler.post {
                        iw.setImageBitmap(image)
                    }
                }
                catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }



        ib2.setOnClickListener {
            super.onBackPressed()
        }

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