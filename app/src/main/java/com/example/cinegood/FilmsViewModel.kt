package com.example.cinegood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class FilmsViewModel :ViewModel() {




    private val _result = MutableLiveData<Exception?>()     //mi serve per capire se il libro è stato salvato o meno
    val result: LiveData<Exception?>                        //la uso per accedere a _result fuori da FilmsViewModel
        get() = _result

    fun addFilm(film: Film) {
        val dbFilms = FirebaseDatabase.getInstance().getReference("film") //creo riferimento al db
        film.id = dbFilms.push().key    //con push genero la chiave (=id), con key posso recuperarla

        dbFilms.child(film.id!!).setValue(film)     //setto i valori
            .addOnCompleteListener{
                if (it.isSuccessful) {      //controllo se il film è stato salvato o meno (vedi AddFilmDialogFragment)
                    _result.value = null
                } else {
                    _result.value = it.exception
                }


            }

    }



}