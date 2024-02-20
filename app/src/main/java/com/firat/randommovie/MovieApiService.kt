package com.firat.randommovie

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieApiService {
    @GET("3/trending/movie/day?language=en-US")

    fun getMovie(@Header("Authorization")apiKey: String, @Query("page")page:Int): Call<MovieResponse>

    }

