package com.example.gymtrackermobile


sealed class Destino(val route: String, val title: String) {
    object Ecra01 : Destino(route = "ecra01",  title = "Ecra01")
    object Ecra02 : Destino(route = "ecra02", title = "Ecra02")
    object Ecra03 : Destino(route = "ecra03",  title = "Ecra03")
    companion object {
        val toList = listOf(Ecra01, Ecra02, Ecra03,)
    }
}