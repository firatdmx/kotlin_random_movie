package com.firat.randommovie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val myLogTag = "GetMovieLog"
        Log.d(myLogTag, "ONCREATE OLUŞTURULDU")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetMovie = findViewById<Button>(R.id.btnGetMovies)
        btnGetMovie.setOnClickListener{
            Log.d(myLogTag, "DENEMEEEE TIKLAAAA")
            val intent = Intent(this@MainActivity, GetMovie::class.java)
            startActivity(intent)
        }


    }


} //MainActivity