package com.example.cinegood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_aggiungi.*

/**
 * A simple [Fragment] subclass.
 */
class AggiungiFragment : DialogFragment() {

    private lateinit var viewModel: FilmsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aggiungi, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {

            val messaggio = if(it ==null){
                "Film aggiunto"
            }else{
                getString(R.string.error_db)
            }
            Toast.makeText(requireContext(),messaggio, Toast.LENGTH_SHORT).show()
            dismiss()
        })


        aggiungi_bottone.setOnClickListener{
            val name = edit_text_name_titolo.text.toString().trim()     //metti il nome del film nella variabile name
            if (name.isEmpty()) {       //se non inserisco il nome dell'autore ho errore
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
            /*val numStar = ratingbar.rating.toInt()
            if (numStar == 0) {
                input_layout_name_rating.error = "Inserire la valutazione del Film"
                return@setOnClickListener
            }*/
            val film = Film()
            film.title=name
            film.date=data
            film.genere=genere
            viewModel.addFilm(film)

        }


    }









}
