package com.example.cinegood

import com.google.firebase.database.Exclude

data class Film(
    @get:Exclude        //l' id non verrà memorizzato nel nodo
    var id: String? = null,
    var title: String? = null,
    var date: String? = null,
    var genere: String? = null
)
