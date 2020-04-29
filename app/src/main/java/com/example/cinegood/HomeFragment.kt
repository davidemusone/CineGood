package com.example.cinegood

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), RecyclerViewClickListener{
    private lateinit var viewModel: FilmsViewModel

    private val adapter = FilmsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view.adapter = adapter

        viewModel.recuperoFilm()
        viewModel.getRealtimeUpdates()

        viewModel.films.observe(viewLifecycleOwner,  Observer {
            adapter.setFilms(it)

        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            adapter.addFilm(it)
        })



    }

    override fun onRecyclerViewClickListener(view: View, film: Film) {
        when (view.id){

            R.id.bottone_modifica  -> {
                EditFragment(film).show(childFragmentManager, "")
            }
            R.id.bottone_elimina -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.conferma_eliminazione))
                    it.setPositiveButton(getString(R.string.si) ){ dialog, which ->
                        viewModel.eliminaFilm(film)
                    }
                }.create().show()

            }

        }


    }
}


