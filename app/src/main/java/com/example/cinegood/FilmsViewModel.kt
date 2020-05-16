package com.example.cinegood

import android.app.ListActivity
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import java.util.ArrayList



class FilmsViewModel() :ViewModel() {

   val dbFilms = FirebaseDatabase.getInstance().getReference("film") //creo riferimento al db,serve ad entrambi i metodi
                                                                                  //per questo lo dichiaro qui



    private val _films = MutableLiveData<List<Film>>()    //lista in cui vanno i titoli dei film (cfr fun async); vedi
    val  films: LiveData<List<Film>>
    get() = _films

    private val _film = MutableLiveData<Film>()     //utile per aggiunta libri aggiornamento realtime
    val  film: LiveData<Film>
        get() = _film





    private val _result = MutableLiveData<Exception?>()          //mi serve per capire se il film è stato salvato o meno
    val result: LiveData<Exception?>                             //la uso per accedere a _result fuori da FilmsViewModel
        get() = _result


    //primo passo generare la chiave id e setto i valori e sopra creo una costante result per vedere se il film è stato salvato


    fun addFilm(film: Film) {

        film.id = dbFilms.push().key                             //con push genero la chiave (=id), con key posso recuperarla

        dbFilms.child(film.id!!).setValue(film).addOnCompleteListener{   //setto i valori
                if (it.isSuccessful) {      //controllo se il film è stato salvato o meno (vedi AggiungiFragment)
                    _result.value = null
                } else {
                    _result.value = it.exception
                }


            }

    }

    private val childEventListener = object : ChildEventListener{    //utile per RealTimeUpdates

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

    fun RealtimeUpdates(){    //mi serve per far sì che il display si aggiorni in tempo reale
                                 //ho usato addListenerForSingleValueEvent in recuperoFilm
        dbFilms.addChildEventListener(childEventListener)
    }

        fun recuperoFilm(){   //mi restituisce il titolo del libro
            dbFilms.addListenerForSingleValueEvent(object: ValueEventListener{
                //le due funzioni sottostanti sono asincrone
                override fun onCancelled(error: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) { //snapshot contiene tutto ciò che sta nel nodo film

                    if (snapshot.exists()) {                          //controllo se snapshot contiene qualcosa
                        val titolo = arrayListOf<Film>()
                        val films = mutableListOf<Film>()            //ci vanno i titoli dei film
                        for (filmsnapshot in snapshot.children) {    //scansiono tutti i figli di snapshot=tutti i sottonodi
                            // del nodo film
                            val film =
                                filmsnapshot.getValue(Film::class.java) //converto il dato snapshot nella classe Film,
                            // saranno riempiti tutti i campi tranne l'id
                            film?.id =
                                filmsnapshot.key                           //aggiunta campo id

                            film?.let { films.add(it) }                          //se film non è nullo lo aggiunto alla lista
                        }
                        _films.value = films //inserisco la lista in _films causa fun async
                        _films.value=titolo
                    }



                }



            })


        }
    fun updateFilm(film: Film){
        dbFilms.child(film.id!!).setValue(film)               //setto i valori
            .addOnCompleteListener{
                if (it.isSuccessful) {                       //controllo se il film è stato salvato o meno (vedi AggiungiFragment)
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