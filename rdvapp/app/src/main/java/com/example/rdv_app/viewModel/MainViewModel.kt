package com.example.rdv_app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rdv_app.model.FilmBean
import com.example.rdv_app.utils.RequestUtils
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {
    var dataShow = MutableLiveData<List<FilmBean>>(ArrayList<FilmBean>())
    var errorMessage = MutableLiveData("")
    var listFilms = ArrayList<FilmBean>()
    var runInProgress = MutableLiveData(false)



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

}