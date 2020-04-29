package com.example.cinegood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController//dichiarazione del navController, per comodità lo dichiaro con lo stesso nome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = Navigation.findNavController(this, R.id.fragment)//ottengo il controllo dei fragment(MainActivity)


        bottomNav.setupWithNavController(navController) //imposto il controllo del bottom navigation



        NavigationUI.setupActionBarWithNavController(this, navController)//imposto action bar
    }

        override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)//impostazione del pulsante indietro
    }




}