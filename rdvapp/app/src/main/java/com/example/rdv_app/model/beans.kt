package com.example.rdv_app.model

import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class FilmBean(
    var film_key:Int?=null,
    var film_name:String?="",
    var film_released_date: String?="",
    var film_description:String?="",
    var film_img:String?="",
    var film_trailer:String?="",
    var film_display_on: ArrayList<String>,
    var film_directors: kotlin.collections.ArrayList<String>,
    var film_actors: ArrayList<String>,
    var film_country: ArrayList<String>,
    var film_runtime:String?="",
    var film_imdb_id:String?="",
    var film_genre:ArrayList<String>
)

data class ShowTimeBean(
    var show_time_key:Int,
    var show_time_date_hour:String,
    var show_time_tickets:Int,
    var film_key:Int,
)


data class DateBean (
    var weekDay: String,
    var day: String,
    var month: String,
){
    var hours = kotlin.collections.ArrayList<String>()
    constructor( weekDay: String, day: String,month: String,
                 hours:kotlin.collections.ArrayList<String>):this(weekDay, day, month)

}




