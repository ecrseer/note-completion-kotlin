package com.example.myapplication.model

object NoteImagens {
    var imgs  = mutableListOf(
        ImagemPesquisada("pg","nsei","olha eu sem bone","titulo2"),
        ImagemPesquisada("pg","nsei","olha eu com bone","titulo3")

    )
    var imgsPeneiradas = mutableListOf<ImagemPesquisada>()

    val getNotaImgs =
        when(imgsPeneiradas.size){
         0 -> imgs
        else -> imgsPeneiradas
    }






}