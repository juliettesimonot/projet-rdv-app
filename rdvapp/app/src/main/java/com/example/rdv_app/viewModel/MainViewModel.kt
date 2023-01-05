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
    var dateMonth = MutableLiveData<List<String>>(ArrayList<String>())
    var dateSelected = MutableLiveData<String>()
    var textInfo = MutableLiveData(false)

    var dataShow = MutableLiveData<List<FilmBean>>(ArrayList<FilmBean>())
    var errorMessage = MutableLiveData("")
    var listFilms = ArrayList<FilmBean>()
    var runInProgress = MutableLiveData(false)


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
        errorMessage.postValue(null);
        runInProgress.postValue(true);

        thread {
            try {
                listFilms = ArrayList(RequestUtils.getAllFilms())
                dataShow.postValue(listFilms)
            }
            catch (e: Exception) {
                e.printStackTrace();
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }
            //Tache asynchrone terminé
            runInProgress.postValue(false);

        }
    }


    fun loadFilmsByDay(day:String){

        thread {
            var boolean = false
            try {

                listFilms = ArrayList(RequestUtils.getFilmByDate(day))
                dataShow.postValue(listFilms)
                if(listFilms.size==0){
                    boolean = true
                }

            }
            catch (e: Exception) {
                e.printStackTrace();
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }

            textInfo.postValue(boolean)
            dateSelected.postValue("le ${day.split('-')[2].slice(0..1)} / ${day.split('-')[1]}")

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

        textInfo.postValue(boolean)
        dataShow.postValue(newListFilmsDay)
        dateSelected.postValue("le ${date.day} ${date.month}")
    }

}