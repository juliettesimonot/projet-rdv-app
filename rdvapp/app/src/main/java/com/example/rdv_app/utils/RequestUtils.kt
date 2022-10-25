package com.example.rdv_app.utils

import com.example.rdv_app.model.FilmBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

object RequestUtils {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body?.string() ?: ""
        }
    }

    fun getAllFilms(): List<FilmBean> {
        val json = sendGet(URL_GET_FILMS)

        //Parser le JSON avec le bon bean et GSON
        val listOfStationBean: Type = object : TypeToken<List<FilmBean?>?>() {}.type

        return gson.fromJson(json, listOfStationBean)

    }

}