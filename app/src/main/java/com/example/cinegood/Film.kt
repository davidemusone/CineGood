package com.example.cinegood

import com.google.firebase.database.Exclude

data class Film(
    @get:Exclude        //l' id non verrà memorizzato nel nodo
    var id: String? = null,
    var title: String? = null,
    var date: String? = null,
    var genere: String? = null,
    var voto: Int? = null//questi verranno poi settati nellea classe AggiungiFragment
){
    override fun equals(other: Any?): Boolean {
        return if(other is Film){
            other.id == id
        } else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (date?.hashCode() ?: 0)
        result = 31 * result + (genere?.hashCode() ?: 0)
        result = 31 * result + (voto ?: 0)
        return result
    }

}