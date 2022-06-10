package com.example.recyclerviewmovies

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MovieAdapter(private var listener: onMovieClickListener) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>(){

    lateinit var moviesList: List<Movie>

    fun setData(data: List<Movie>){
        moviesList = data
        notifyDataSetChanged()
    }

    //will make ViewHolder for us
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.movieTitle.text = moviesList[position].title
        holder.movieYear.text = moviesList[position].year.toString()
        holder.moviePlot.text = moviesList[position].plot
        holder.movieGenres.text = moviesList[position].genres.toString().replace("[", "").replace("]", "")

        val hours = moviesList[position].runtime/60;
        val mins = moviesList[position].runtime%60;
        holder.movieRuntime.text = "${hours}h ${mins}m"

        val media = moviesList[position].posterUrl
        if (!media.isNullOrEmpty()) {

            Glide.with(holder.itemView.context)
                .load(media)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.not_found_image2)
                .fallback(ColorDrawable(Color.GRAY))
                .into(holder.moviePoster)
        } else {
            holder.moviePoster.setImageResource(R.drawable.ic_launcher_background)
        }

    }

    override fun getItemCount(): Int {
        //return moviesData?.movies?.size
        return moviesList.size
    }



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var movieTitle = itemView.findViewById<TextView>(R.id.item_title)
        var movieYear = itemView.findViewById<TextView>(R.id.movie_year)
        var movieRuntime = itemView.findViewById<TextView>(R.id.movie_runtime)
        var moviePlot = itemView.findViewById<TextView>(R.id.item_plot)
        var movieGenres = itemView.findViewById<TextView>(R.id.movie_genre)
        var moviePoster = itemView.findViewById<ImageView>(R.id.movie_poster)

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(adapterPosition)
        }


    }

    interface onMovieClickListener{
        fun onItemClick(position: Int);
    }
}


