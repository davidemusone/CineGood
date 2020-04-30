package com.example.cinegood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_aggiungi.*

/**
 * A simple [Fragment] subclass.
 */
class AggiungiFragment : DialogFragment() {

    private lateinit var viewModel: FilmsViewModel    //mi serve per accedere ad addFilm=metodo per generare la chiave..
                                                        //FilmsViewModel è la classe per prendere il metodo
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)  //istanza della classe, è diversa poichè estende
                                                                                            // la classe view Model
        return inflater.inflate(R.layout.fragment_aggiungi, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {//questo ovverride, ne contiene altri due, (il secondo) serve per
                                                                    // iserire i campi quando premo il bottone
                                                                    // aggiungi e per (primo ovveride) vedere il messaggio di successo
                                                                //o di errore dopo averlo inviato
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {

            val messaggio = if(it ==null){//il film è stato aggiunto nel db
                "Film aggiunto"
            }else{  //il film non è stato aggiunto nel db
                getString(R.string.error_db)
            }
            Toast.makeText(requireContext(),messaggio, Toast.LENGTH_SHORT).show() //mostra l'errore o il messaggio di successo
            dismiss()
        })


        aggiungi_bottone.setOnClickListener{
            val name = edit_text_name_titolo.text.toString().trim()     //metti il titolo del film nella variabile name
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
            //costruisco l'oggetto del film
            val film = Film()
            film.title=name
            film.date=data
            film.genere=genere
            film.voto=votazione
            viewModel.addFilm(film)   //metodo addFilm all'interno di viewModel

        }


    }









}
