package com.example.cinegood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_dettagli.*

/**
 * A simple [Fragment] subclass.
 */
class DettagliFragment(private val film : Film) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dettagli, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            dettaglo_titolo1.text = film.title
            dettaglo_data1.text = film.date
            dettaglo_genere1.text = film.genere
            dettaglio_ratingb.rating = film.voto!!.toFloat()

    }




}
