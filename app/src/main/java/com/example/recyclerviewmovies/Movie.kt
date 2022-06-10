package com.example.recyclerviewmovies

import java.io.Serializable

data class Movie(
    val id: Int,
    val title: String,
    val year: Int,
    val runtime: Int,
    val genres: MutableList<String>,
    val director: String,
    val actors: String,
    val plot: String,
    val posterUrl: String
) : Serializable
