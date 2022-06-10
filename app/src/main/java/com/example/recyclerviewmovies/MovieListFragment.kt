package com.example.recyclerviewmovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.FieldPosition


class MovieListFragment : Fragment() , MovieAdapter.onMovieClickListener{


    private lateinit var viewModel: MovieListViewModel
    private  var moviesListJson : String? = null
    lateinit var moviesData:MoviesData
    lateinit var allMoviesList:List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_movie_list, container, false)

        //we never  construct viewModel ourselves, if we did we will end up creating everytime fragment
        //was recreated
        viewModel = ViewModelProvider(this,).get(MovieListViewModel::class.java)

        initAsset()

        var movieList: RecyclerView = view.findViewById(R.id.movieList)

        movieList.layoutManager = LinearLayoutManager(activity)
        var adapter = MovieAdapter(this)
        movieList.adapter = adapter

        viewModel.moviesList.observe(viewLifecycleOwner, Observer {newMoviesList->
                adapter.setData(newMoviesList)
        })


        //dropdown filter
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val dropdownAdapter = ArrayAdapter<String>(
            requireContext(), R.layout.dropdown_item,
            viewModel.moviesData.genres
        )
        autoCompleteTextView.setAdapter(dropdownAdapter)
        autoCompleteTextView.setOnItemClickListener(AdapterView.OnItemClickListener {
                adapterView, view, position, id ->

            val genre = adapterView.getItemAtPosition(position).toString()

            viewModel.updateMoviesOnGenre(genre)

        })

        return view
    }


    override fun onResume() {
        super.onResume()

    }

    private fun initAsset() {
        moviesListJson = context?.assets?.open("movies_list.json")?.bufferedReader().use {
            it?.readText()
        }

        viewModel.moviesDataJSON = moviesListJson
        viewModel.makeMoviesData()

    }

    override fun onItemClick(position: Int) {
        //Toast.makeText(context,"Item clicked # $position ",Toast.LENGTH_SHORT).show()
        val intent = Intent(context,MovieActivity::class.java)
        intent.putExtra("movie", viewModel.moviesList.value?.get(position))
        activity?.startActivity(intent)
    }


}