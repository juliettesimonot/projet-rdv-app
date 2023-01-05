package com.example.rdv_app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rdv_app.adapter.CalendarDateAdapter
import com.example.rdv_app.adapter.FilmCardAdapter
import com.example.rdv_app.databinding.ActivityMainBinding
import com.example.rdv_app.utils.ID_MENU_HOME
import com.example.rdv_app.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    // Pour les composants graphiques
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}

    //Pour les données
    //en cas de recréation de l'activité on retrouvera le même ViewModel
    val model by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}


    val filmAdapter = FilmCardAdapter()
    val calendarAdapter = CalendarDateAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Define ActionBar object
        val actionBar: ActionBar?
        actionBar = supportActionBar

        // Define ColorDrawable object and parse color
        val colorDrawable = ColorDrawable(Color.parseColor("#000000"))

        // Set BackgroundDrawable
        actionBar?.setBackgroundDrawable(colorDrawable)



        //Dans le onCreate, réglage du Recyclerview
        binding.rvFilmCards.adapter = filmAdapter
        binding.rvDates.adapter = calendarAdapter

        filmAdapter.onFilmClick = {
            val intent = Intent(this, FilmActivity::class.java)
            intent.putExtra("film", it.film_key)

//            finish()
            // Lance le workflow de changement d'écran
            startActivity(intent);
        }


        calendarAdapter.onDateClick = {
            model.loadFilmsByDay(it)
        }


        //Combien on veut de colonne
        binding.rvFilmCards.layoutManager = GridLayoutManager(this, 1)
        binding.rvDates.layoutManager = GridLayoutManager(this, 1)

        //recycler view horizontal
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDates.setLayoutManager(linearLayoutManager)


        //recuperer les donnees
        model.loadDataAllFilms()
        model.loadDatesMonth()

        //observe changements des donnees
        model.dataShow.observe(this){
            filmAdapter.submitList(it.toList())
        }

        model.dateMonth.observe(this){
            calendarAdapter.submitList(it.toList())
        }

        model.dateSelected.observe(this){
            binding.tvDateTitle.text = it
        }

        model.textInfo.observe(this){
            binding.tvInfo.isVisible = it
        }

    }


    //Afficher le menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menu.add(0, ID_METRO_MENU, 0, "Métro");
        menu.add(0, ID_MENU_HOME, 0, "Home").setIcon(R.drawable.rdv_logo)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        return super.onCreateOptionsMenu(menu)
    }


    //Clic sur le menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.getItemId() == ID_MENU_HOME) {
            val intent = Intent(this, MainActivity::class.java)

            finish()
            // Lance le workflow de changement d'écran
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item)
    }
}