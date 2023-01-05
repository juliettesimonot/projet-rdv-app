package com.example.rdv_app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rdv_app.model.DateBean
import com.example.rdv_app.model.FilmBean
import com.example.rdv_app.model.ShowTimeBean
import com.example.rdv_app.utils.RequestUtils
import java.time.LocalDate
import kotlin.concurrent.thread

class FilmViewModel : ViewModel() {
    var dataShow = MutableLiveData<FilmBean>()
    var errorMessage = MutableLiveData("")
    var runInProgress = MutableLiveData(false)
    var listFilms = ArrayList<FilmBean>()
    var listShowTimes = ArrayList<ShowTimeBean>()
    var dataShowTime = MutableLiveData<List<ShowTimeBean>>(ArrayList<ShowTimeBean>())
    var dateShow = MutableLiveData<List<DateBean>>(ArrayList<DateBean>())


    fun loadDataFilm(filmkey : Int){
        errorMessage.postValue(null);
        runInProgress.postValue(true);

        thread {
            try {
                listFilms = ArrayList(RequestUtils.getOneFilm(filmkey))
                dataShow.postValue(listFilms[0])
            }
            catch (e: Exception) {
                e.printStackTrace();
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }
            //Tache asynchrone terminé
            runInProgress.postValue(false);
        }
    }


    fun loadDates(filmkey:Int){
        errorMessage.postValue(null);
        runInProgress.postValue(true);

        thread {
            try {
                listShowTimes = ArrayList(RequestUtils.getShowTimesByFilm(filmkey))
                dataShowTime.postValue(listShowTimes)
            }
            catch (e: Exception) {
                e.printStackTrace();
                errorMessage.postValue("Une erreur est survenue : ${e.message}")
            }
            //Tache asynchrone terminé
            runInProgress.postValue(false);
        }
    }


    fun loadDatesFilm(film:FilmBean){
        var calendar= ArrayList<DateBean>()
        var calendarDays = arrayListOf<DateBean>()
        film.film_display_on.forEach{
            var year = it.split('-')[0].toInt()
            var month = it.split('-')[1].toInt()
            var day = it.split('-')[2].slice(0..1).toInt()
            var hour = it.split('T')[1].slice(0..4)
            var hours = arrayListOf<String>()
            hours.add(hour)
            var date = LocalDate.of(year, month, day)
            var dateBean = DateBean(date.dayOfWeek.name.slice(0..2), day.toString(), date.month.name.slice(0..2))
            var dateBean2 = DateBean(date.dayOfWeek.name.slice(0..2), day.toString(), date.month.name.slice(0..2))
            dateBean.hours = hours
            calendar.add(dateBean)
            calendarDays.add(dateBean2)
        }

        var calendarDaysList: List<DateBean>
        calendarDaysList = calendarDays.distinct()


        calendar.forEach {
            var dateInit = it
            calendarDaysList.forEach {
                var dateCheck = it
                if(dateCheck.day == dateInit.day && dateCheck.month==dateInit.month){
                    dateCheck.hours.add(dateInit.hours[0])
                }
            }
        }
        dateShow.postValue(calendarDays)
    }
}