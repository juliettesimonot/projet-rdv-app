package com.example.rdv_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rdv_app.adapter.FilmCardAdapter
import com.example.rdv_app.databinding.ActivityMainBinding
import com.example.rdv_app.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    // Pour les composants graphiques
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    //Pour les données
    //en cas de recréation de l'activité on retrouvera le même ViewModel
    val model by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}


    val adapter = FilmCardAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        //Dans le onCreate, réglage du Recyclerview
        binding.rvFilmCards.adapter = adapter

        //Combien on veut de colonne
        binding.rvFilmCards.layoutManager = GridLayoutManager(this, 1)


        //recuperer les donnees
        model.loadDataAllFilms()

        //observe changements des donnees
        model.dataShow.observe(this){
            adapter.submitList(it.toList())
        }
    }
}