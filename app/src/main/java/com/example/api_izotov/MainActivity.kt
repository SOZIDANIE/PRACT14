package com.example.api_izotov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.jvm.Throws


class MainActivity : AppCompatActivity() {
    private lateinit var btn : Button
    private lateinit var b2 : Button
    private lateinit var btn2 : Button
    private lateinit var btn3 : Button
    private lateinit var btn4 : Button
    private lateinit var btn5 : Button
    private lateinit var btn6 : Button
    private lateinit var btn7 : Button
    private lateinit var eT: EditText
    private lateinit var tv : TextView
    private lateinit var ib1 : ImageButton
    private lateinit var ib2 : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)
        b2 = findViewById(R.id.b2)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        eT = findViewById(R.id.eT)
        tv = findViewById(R.id.tv)
        ib1 = findViewById(R.id.i1)
        ib2 = findViewById(R.id.i3)
        val intent = Intent(this, Room::class.java)
        val intent1 = Intent(this, Cats::class.java)


        btn.text = "OFF LIGHT"
        b2.text = "SET TEMPERATURE"
        btn2.text = "WEAK LIGHT"
        btn3.text = "NORMAL LIGHT"
        btn4.text = "FULL LIGHT"
        btn5.text = "NORMAL COLOR"
        btn6.text = "HOT COLOR"
        btn7.text = "COLD COLOR"

        val exec : ExecutorService = Executors.newSingleThreadExecutor()

        btn.setOnClickListener{
            var tempa = eT.text
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/Room/Conditioner/SetTemperature/${tempa}")
            }).get()
        }


        ib1.setOnClickListener {
            startActivity(intent)
        }

        ib2.setOnClickListener {
            startActivity(intent1)
        }

        btn.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/poweron/0")
            }).get()
        }
        btn2.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/poweron/1")
            }).get()
        }
        btn3.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/poweron/2")
            }).get()
        }
        btn4.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/poweron/3")
            }).get()
        }
        btn5.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/setcolor/0")
            }).get()
        }
        btn6.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/setcolor/1")
            }).get()
        }
        btn7.setOnClickListener{
            tv.text = exec.submit(Callable {
                http("http://smartroom.ectsserver.edu/api/room/light/setcolor/2")
            }).get()
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