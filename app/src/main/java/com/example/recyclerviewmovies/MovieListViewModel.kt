package com.example.recyclerviewmovies

import android.content.res.loader.AssetsProvider
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.json.JSONObject
import java.io.InputStream
import java.nio.channels.AsynchronousFileChannel.open

class MovieListViewModel : ViewModel(){


    var moviesDataJSON : String? = ""
    lateinit var moviesData: MoviesData
    var moviesList = MutableLiveData<List<Movie>>()




    fun makeMoviesData() {
        val gson =  Gson()
        moviesData = gson.fromJson(moviesDataJSON,MoviesData::class.java)
        moviesList.value = moviesData.movies
        val c =2
    }

    fun updateMoviesOnGenre(genre: String){

        moviesList.value = moviesData.movies.filter {
            it.genres.contains(genre)
        }
    }


}