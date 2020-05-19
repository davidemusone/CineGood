package com.example.cinegood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment(
    private val film: Film

) : DialogFragment() {
    private lateinit var viewModel: FilmsViewModel          //mi serve per accedere ad aggiungiFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this)
            .get(FilmsViewModel::class.java) //istanza della classe, è diversa poichè estende la classe view Model
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

        edit_titolo.setText(film.title)
        edit_data.setText(film.date)
        edit_genere.setText(film.genere)
        ratingb.rating = film.voto!!.toFloat()


        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                "Film modificato!"
            } else {
                "Non è possiile modificare il film "
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })



        bottone_edit.setOnClickListener {
            val title = edit_titolo.text.toString().trim() //metti il titolo del film nella variabile title
            if (title.isEmpty()) {                      //se non inserisco il titolo ho errore
                layout_titolo.error = "Inserire il titolo del film"
                return@setOnClickListener
            }
            val genere = edit_genere.text.toString().trim()
            if (genere.isEmpty()) {
                layout_genere.error = "Inserire il genere del film"
                return@setOnClickListener
            }
            val data = edit_data.text.toString().trim()
            if (data.isEmpty()) {
                layout_data.error = "Inserire la data della visione del Film"
                return@setOnClickListener
            }
            val votazione = ratingb.rating.toInt()
            if (votazione == 0) {
                layout_ratingb.error = "Inserire la valutazione del Film"
                return@setOnClickListener
            }
            //aggiorno l'oggetto del film

            film.title = title
            film.date = data
            film.genere = genere
            film.voto = votazione
            viewModel.updateFilm(film)

        }


    }
}