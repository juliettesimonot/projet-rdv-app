package com.example.rdv_app.viewModel

import android.text.TextUtils.split
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rdv_app.model.DateBean
import com.example.rdv_app.model.FilmBean
import com.example.rdv_app.utils.RequestUtils
import java.time.LocalDate
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    var dateSelected = MutableLiveData<String>()
    var textInfo = MutableLiveData("")
    var dataShow = MutableLiveData<List<FilmBean>>(ArrayList<FilmBean>())
    var listFilms = ArrayList<FilmBean>()
    var runInProgress = MutableLiveData(false)
    var dateMonth = MutableLiveData<List<String>>(ArrayList<String>())

    fun loadFilmsByDay(day:String){
        textInfo.postValue(null);
        thread {
            try {
                listFilms = ArrayList(RequestUtils.getFilmByDate(day))
                dataShow.postValue(listFilms)
                if(listFilms.size==0){
                    textInfo.postValue("Il n'y a pas de films à l'affiche ce jour-ci")
                }
            }
            catch (e: Exception) {
                e.printStackTrace();
                textInfo.postValue("Une erreur est survenue, veuillez réessayer ultérieurement")
                dataShow.postValue(arrayListOf())
            }
            dateSelected.postValue("le ${day.split('-')[2].slice(0..1)} / ${day.split('-')[1]}")
        }
    }


    fun loadDatesMonth(){
        var day = 1
        var calendar= ArrayList<String>()
        var initDate = LocalDate.now()
        while (day<16){
            var plusDay = initDate.plusDays(day.toLong())

            var date = plusDay.toString()
            calendar.add(date)
            day++
        }
        dateMonth.postValue(calendar)
    }





    fun loadDataAllFilms(){
        textInfo.postValue(null);
        runInProgress.postValue(true);

        thread {
            try {
                listFilms = ArrayList(RequestUtils.getAllFilms())
                dataShow.postValue(listFilms)
            }
            catch (e: Exception) {
                e.printStackTrace();
                textInfo.postValue("Une erreur est survenue, veuillez réessayer ultérieurement")
                dataShow.postValue(arrayListOf())
            }
            //Tache asynchrone terminé
            runInProgress.postValue(false);

        }
    }





    fun loadFilmsDay(date:DateBean){
        var boolean = false

        var newListFilmsDay = arrayListOf<FilmBean>()
        var initDate = LocalDate.now()

        listFilms.forEach{

            var arrayDates = arrayListOf<Boolean>()
            it.film_display_on.forEach {
                //formatter date

                var month = it.split('-')[1]
                month = LocalDate.of(initDate.year, month.toInt(), 1).month.name.slice(0..2)

                var filmDate = DateBean("", it.split('-')[2].slice(0..1), month)

                //ajouter film si date match
               if(date.day==filmDate.day && date.month==filmDate.month) {
                   arrayDates.add(true)
               }
            }
            if(arrayDates.size>0){
                newListFilmsDay.add(it)
            }
        }

        if(newListFilmsDay.size==0){
            boolean = true
        }

        textInfo.postValue("")
        dataShow.postValue(newListFilmsDay)
        dateSelected.postValue("le ${date.day} ${date.month}")
    }

}