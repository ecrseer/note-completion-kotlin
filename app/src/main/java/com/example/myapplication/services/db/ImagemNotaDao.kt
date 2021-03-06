package com.example.myapplication.services.db

import androidx.room.*
import com.example.myapplication.domain.ImagemNota
import kotlinx.coroutines.flow.Flow

@Dao
interface ImagemNotaDao {
    @Query(value = "SELECT * FROM imagemnota WHERE idNota = :id")
    suspend fun obter(id:Int):ImagemNota

    @Query(value = "SELECT * FROM imagemnota")
    suspend fun listar():List<ImagemNota>

    @Query(value = "SELECT * FROM imagemnota")
    fun listarLiveData(): Flow<List<ImagemNota>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserir(nota:ImagemNota):Long

    @Delete
    suspend fun excluir(nota:ImagemNota)

    @Update
    suspend fun editar(nota:ImagemNota)
}