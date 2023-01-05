package com.example.rdv_app

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.VideoView;
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rdv_app.adapter.DateFilmAdapter
import com.example.rdv_app.databinding.ActivityFilmBinding
import com.example.rdv_app.utils.ID_MENU_HOME
import com.example.rdv_app.viewModel.FilmViewModel
import com.squareup.picasso.Picasso


class FilmActivity : AppCompatActivity() {

    // Pour les composants graphiques
    val binding by lazy{ ActivityFilmBinding.inflate(layoutInflater)}

    //Pour les données
    //en cas de recréation de l'activité on retrouvera le même ViewModel
    val model by lazy{ ViewModelProvider(this).get(FilmViewModel::class.java)}

    val dateAdapter = DateFilmAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //------------------------
        ///ACTION BAR
        //------------------------

        // Define ActionBar object
        val actionBar: ActionBar?
        actionBar = supportActionBar

        // Define ColorDrawable object and parse color
        val colorDrawable = ColorDrawable(Color.parseColor("#000000"))

        // Set BackgroundDrawable
        actionBar?.setBackgroundDrawable(colorDrawable)


        //------------------------
        ///Recycler view
        //------------------------


        //Dans le onCreate, réglage du Recyclerview
        binding.rvDates.adapter = dateAdapter

        //Combien on veut de colonne
        binding.rvDates.layoutManager = GridLayoutManager(this, 1)




        //recuperer les donnees
        val filmKey = intent.getIntExtra("film", 0)
        model.loadDataFilm(filmKey)

        model.dataShowTime.observe(this){
            dateAdapter.submitList(it.toList())
        }


        //on click

        binding.btTicket.setOnClickListener {
            Toast.makeText(it.context, "En cours de construction",Toast.LENGTH_SHORT).show()
        }



        //observe changements des donnees
        model.dataShow.observe(this){
            model.loadDates(it.film_key?:0)

            binding.tvHeaderTitle.text = it.film_name
            binding.tvHeaderDescription.text = it.film_description?.slice(0..50)+"..."
            binding.tvFilmTitle.text = it.film_name
            binding.tvFilmDescription.text = it.film_description
            binding.tvRuntime.text = it.film_runtime
            binding.tvRealeasedDate.text = it.film_released_date?.split('-')?.get(0) ?: "-"
            Picasso.get().load(it.film_img).into(binding.ivHeader)


            var actors = ""
            it.film_actors.forEach {
                actors+=it+"- "
            }
            binding.tvActors.text = actors

            var filmDirectors = ""
            it.film_directors.forEach {
                filmDirectors+=it+"- "
            }
            binding.tvFilmDirector.text = filmDirectors

            var genres = ""
            it.film_genre.forEach {
                genres+=it+"- "
            }
            binding.tvGenre.text = genres

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