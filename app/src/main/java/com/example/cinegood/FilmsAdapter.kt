package com.example.cinegood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.recycler_view_film.view.*

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.FilmsViewModel>() {   //mi serve per visualizzare la recycler view

    private var films = mutableListOf<Film>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilmsViewModel(  //faccio sì che sarà visualizzato sul display solo il titolo del libro
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_film, parent, false)
    )

    override fun getItemCount() = films.size

    override fun onBindViewHolder(
        holder: FilmsViewModel,
        position: Int
    ) {
        holder.view.titolo_film.text = films[position].title
        holder.view.titolo_film.setOnClickListener {
            listener?.onRecyclerViewClickListener(it, films[position])
        }

        holder.view.bottone_modifica.setOnClickListener {
            listener?.onRecyclerViewClickListener(it, films[position])
        }
        holder.view.bottone_elimina.setOnClickListener {
            listener?.onRecyclerViewClickListener(it, films[position])
        }
    }

    fun setFilms(films: List<Film>) { //setto la lista
        this.films = films as MutableList<Film> //cast come MutableList
        notifyDataSetChanged()   //visualizzo sul display

    }
    //utile per aggiornamenti realtime
    fun addFilm(film: Film) {
        if (!films.contains(film)) {
            films.add(film)
        } else {
            val index = films.indexOf(film)
            if (film.isDELETE) {
                films.removeAt(index)
            } else {
                films[index] = film
            }
            notifyDataSetChanged()
        }

    }

        class FilmsViewModel(val view: View) : RecyclerView.ViewHolder(view)

    }
