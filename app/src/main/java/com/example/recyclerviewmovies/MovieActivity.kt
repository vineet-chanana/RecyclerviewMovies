package com.example.recyclerviewmovies

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        var moviePoster = findViewById<ImageView>(R.id.movie_poster)
        var movieTitle = findViewById<TextView>(R.id.movie_title)
        var movieGenres = findViewById<TextView>(R.id.movie_genres)
        var movieReleasedYear = findViewById<TextView>(R.id.movie_releasedYear)
        var moviePlot = findViewById<TextView>(R.id.movie_plot)
        var movieDirector = findViewById<TextView>(R.id.movie_director)
        var movieActors = findViewById<TextView>(R.id.movie_actors)
        var movieRuntime = findViewById<TextView>(R.id.movie_duration)

        var movie = intent.extras?.get("movie") as Movie


        movieTitle.text = movie.title

        val media = movie.posterUrl
        if (!media.isNullOrEmpty()) {
            Glide.with(applicationContext)
                .load(media)
                .error(R.drawable.not_found_image2)
                .fallback(ColorDrawable(Color.GRAY))
                .into(moviePoster)
        } else {
            moviePoster.setImageResource(R.drawable.ic_launcher_background)
        }

        movieGenres.text = movie.genres.toString().replace("[", "").replace("]", "")
        movieReleasedYear.text = movie.year.toString()
        moviePlot.text = movie.plot
        movieDirector.text = movie.director
        movieActors.text = movie.actors.toString().replace("[", "").replace("]", "")
        val hours = movie.runtime/60;
        val mins = movie.runtime%60;
        movieRuntime.text = "${hours}h ${mins}m"



    }
}