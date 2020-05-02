package com.example.cinegood

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeFragment : Fragment(), RecyclerViewClickListener{


    private lateinit var viewModel: FilmsViewModel //mi serve per accedere a recuperoFilm
    private val adapter = FilmsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        viewModel = ViewModelProviders.of(this).get(FilmsViewModel::class.java)    //istanza, posso richiamare
                                                                                            // il metodo recuperofilm() e altri
         return  inflater.inflate(R.layout.fragment_home, container, false)

    }






    //per la search bar


/*


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.menu_search, menu)
        val searchItem= menu.findItem(R.id.search)

        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint = "Cerca"

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchItem.collapseActionView()
                    return true
                }


                override fun onQueryTextChange(newText: String): Boolean {


                    val arrayList = viewModel.titolo
                    val displayList = viewModel.titolo
                    if (newText.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.toLowerCase(Locale.getDefault())
                        arrayList.forEach {
                            if (it.title!!.toLowerCase(Locale.getDefault()).contains(search)){
                                displayList.add(it)
                            }

                        }

                        recycler_view.adapter!!.notifyDataSetChanged()
                    } else {
                        displayList.clear()
                        displayList.addAll(arrayList)
                        recycler_view.adapter!!.notifyDataSetChanged()
                    }

                    return true
            }
        })
            }


        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
*/



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        adapter.listener = this
        recycler_view.adapter = adapter             //setto l'adapter

        viewModel.recuperoFilm()                    //recupero dati
        viewModel.getRealtimeUpdates()

        //prendo films di tipo LiveData e lo inserisco nella lista che sarà visualizzata sul display
        viewModel.films.observe(viewLifecycleOwner,  Observer {
            adapter.setFilms(it)

        })

        viewModel.film.observe(viewLifecycleOwner, Observer {
            adapter.addFilm(it)
        })



    }

    override fun onRecyclerViewClickListener(view: View, film: Film) {
        when (view.id){

            R.id.titolo_film ->{
                DettagliFragment(film).show(childFragmentManager,"")

            }

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







