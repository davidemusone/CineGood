package com.example.cinegood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_aggiungi.*
import kotlinx.android.synthetic.main.fragment_aggiungi.input_layout_name_data
import kotlinx.android.synthetic.main.fragment_aggiungi.input_layout_name_genere
import kotlinx.android.synthetic.main.fragment_aggiungi.input_layout_name_titolo
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment(
    private val film: Film

) : DialogFragment() {
    private lateinit var viewModel: FilmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,   // Inflate the layout for this fragment
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this)
            .get(FilmsViewModel::class.java)  //istanza della classe, è diversa poichè estende la classe view Model
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NO_TITLE,
            android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth
        )
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        edit_text_name_titolo.setText(film.title)
        edit_text_name_data.setText(film.date)
        edit_text_name_genere.setText(film.genere)
        ratingbar.rating = film.voto!!.toFloat()


        bottone_edit.setOnClickListener {
            val name = edit_text_name_titolo.text.toString()
                .trim()     //metti il nome del film nella variabile name
            if (name.isEmpty()) {       //se non inserisco il titolo del film ho errore
                input_layout_name_titolo.error = "Inserire il titolo del film"
                return@setOnClickListener
            }
            val genere = edit_text_name_genere.text.toString().trim()
            if (genere.isEmpty()) {
                input_layout_name_genere.error = "Inserire il genere del film"
                return@setOnClickListener
            }
            val data = edit_text_name_data.text.toString().trim()
            if (data.isEmpty()) {
                input_layout_name_data.error = "Inserire la data della visione del Film"
                return@setOnClickListener
            }
            val votazione = ratingbar.rating.toInt()
            if (votazione == 0) {
                input_layout_name_rating.error = "Inserire la valutazione del Film"
                return@setOnClickListener
            }
            //aggiorno l'oggetto del film
            val film = Film()
            film.title = name
            film.date = data
            film.genere = genere
            film.voto = votazione
            viewModel.updateFilm(film)

        }


    }
}