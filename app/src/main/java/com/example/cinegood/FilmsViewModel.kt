package com.example.cinegood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*

class FilmsViewModel() :ViewModel() {

  private val dbFilms = FirebaseDatabase.getInstance().getReference("film")             //creo riferimento al db,serve a entrambe le classi

    private val _films = MutableLiveData<List<Film>>()
    val  films: LiveData<List<Film>>
    get() = _films

    private val _film = MutableLiveData<Film>()
    val  film: LiveData<Film>
        get() = _film

    private val _result = MutableLiveData<Exception?>()                                            //mi serve per capire se il libro è stato salvato o meno
    val result: LiveData<Exception?>                                                                      //la uso per accedere a _result fuori da FilmsViewModel
        get() = _result

    fun addFilm(film: Film) {

        film.id = dbFilms.push().key                                                               //con push genero la chiave (=id), con key posso recuperarla

        dbFilms.child(film.id!!).setValue(film)     //setto i valori
            .addOnCompleteListener{
                if (it.isSuccessful) {      //controllo se il film è stato salvato o meno (vedi AggiungiFragment)
                    _result.value = null
                } else {
                    _result.value = it.exception
                }


            }

    }

    private val childEventListener = object : ChildEventListener{
        override fun onCancelled(error: DatabaseError) {}

        override fun onChildMoved(snapshot:  DataSnapshot, p1: String?) {}

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val film = snapshot.getValue(Film::class.java)
            film?.id= snapshot.key
            _film.value=film
        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            val film = snapshot.getValue(Film::class.java)
            film?.id= snapshot.key
            _film.value=film
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val film = snapshot.getValue(Film::class.java)
            film?.id= snapshot.key
            film?.isDELETE = true
            _film.value=film
        }


    }

    fun getRealtimeUpdates(){
        dbFilms.addChildEventListener(childEventListener)
    }

        fun recuperoFilm(){
            dbFilms.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val films = mutableListOf<Film>()
                        for(filmsnapshot in snapshot.children){
                            val film = filmsnapshot.getValue(Film::class.java)
                            film?.id= filmsnapshot.key
                            film?.let { films.add(it) }
                        }
                        _films.value= films
                    }
                }


            })


        }
    fun updateFilm(film: Film){
      //  film.id = dbFilms.push().key                                       //con push genero la chiave (=id), con key posso recuperarla

        dbFilms.child(film.id!!).setValue(film)                                //setto i valori
            .addOnCompleteListener{
                if (it.isSuccessful) {                                      //controllo se il film è stato salvato o meno (vedi AggiungiFragment)
                    _result.value = null
                } else {
                    _result.value = it.exception
                }


            }

    }
      fun eliminaFilm(film: Film){

        dbFilms.child(film.id!!).setValue(null)     //non setto i valori
            .addOnCompleteListener{
                if (it.isSuccessful) {      //controllo se il film è stato salvato o meno (vedi AggiungiFragment)
                    _result.value = null
                } else {
                    _result.value = it.exception
                }


            }



    }


    override fun onCleared() {
        super.onCleared()
        dbFilms.removeEventListener(childEventListener)
    }



}