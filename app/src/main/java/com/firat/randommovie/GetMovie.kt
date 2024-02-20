package com.firat.randommovie

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Random



class GetMovie : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_movie)

        val giveNewMovieButton = findViewById<Button>(R.id.btGiveNewMovie)
        giveNewMovieButton.setOnClickListener{
            getRandomMovie()
        }

        getRandomMovie()

    }

    private fun getRandomMovie() {
        val myLogTag = "GetMovieLog"

        val api = RetrofitHelper.getRetrofitInstance().create(MovieApiService::class.java)
        val apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhNDJkMTljZDU1ZjVkN2JmOTBiYzdjMjRjYzU4ODg2YyIsInN1YiI6IjY1YmFiNjFmOGMzMTU5MDE2MmYzMzM3MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.txdBDTjhWnLqZuM-8yQqcCf_SgW7EI4QIVhXFf-Y7XU"
        val page = Random().nextInt(500)
        val call = api.getMovie(apiKey,page)

        GlobalScope.launch(Dispatchers.IO) {
            val tvMovieTitle = findViewById<TextView>(R.id.tvMovieTitle)
            val response = call.execute()
            Log.d(myLogTag, response.body().toString())
            if (response.isSuccessful) {
                val movieResponse = response.body()
                val movies = movieResponse?.results
                if (!movies.isNullOrEmpty()) {
                    Log.d(myLogTag, "MOVIES SIZE: " + movies.size.toString())
                    val randomMovie = movies[Random().nextInt(movies.size)]
                    Log.d(myLogTag, randomMovie.toString())
                    Log.d(myLogTag, "Movie title size: " + randomMovie.title.length.toString())
                    launch(Dispatchers.Main) {
                        //tvMovieTitle.text = randomMovie.title
                        if (randomMovie.title.length < 25) {
                            tvMovieTitle.text = "\t\t\t" + randomMovie.title
                        } else {
                            val s1a = randomMovie.title.substring(0, (randomMovie.title.length/2))
                            val s1b = randomMovie.title.substring((randomMovie.title.length/2))
                            tvMovieTitle.text = "$s1a \n $s1b"
                            // kelimeyi bölmesini engelle
                            
                        }
                    }
                    val imgUrl = "https://image.tmdb.org/t/p/w500${randomMovie.poster_path}"
                    Log.d(myLogTag, "IMG URL : $imgUrl")
                    val imageView = findViewById<ImageView>(R.id.ivMoviePoster)

                    val tvOverview = findViewById<TextView>(R.id.tvMovieOverview)
                    val bitmap = Picasso.get().load(imgUrl).get()
                    launch(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                        tvOverview.text = "\t\t\t" + randomMovie.overview
                    }

                } //if movieis !null
                else {
                    Log.e(myLogTag, "MOVIE COULDN'T BE FOUND FROM THE API")
                }
            }// if response OK
        }//GlobalScope coroutine

    } //getRandomMovie func
}