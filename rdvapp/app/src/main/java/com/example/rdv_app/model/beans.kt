package com.example.rdv_app.model

import java.util.*

data class FilmBean(
    var film_key:Int?=null,
    var film_title:String?="",
    var film_released_date: String?="",
    var film_description:String?="",
    var film_img:String?="",
    var film_trailer:String?="",
    var film__display_on: Date?=null,
    var film_director: String?="",
    var film_actors: String?="",
    var film_country: String?="",
    var film_runtime:String?="",
    var film_imdb_id:String?="",
    var film_genre:String?=""
)
