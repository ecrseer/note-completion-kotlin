package com.example.myapplication

import ImagemPesquisada
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync

class FirstFragmentViewModel : ViewModel() {
    private val  apiurl = "https://notecompletion.herokuapp.com/pesquisaImagem"



    private val _input = MutableLiveData<String>().apply {
        value = "Textfield"
    }
    val input: LiveData<String> = _input
    val pl: String = ""
    private val _ldImagem = MutableLiveData<String>().apply {
        value = ""
    }
    fun setLdImagem(txt:String){
        _ldImagem.value=txt
    }

    val ldImagem: LiveData<String> = _ldImagem

    fun testan(){
        Log.d("HomeFragt","ttttttttttt")
    }
    fun pesquisaImagemDe(palavrachave: String){
        val client = OkHttpClient()

        val request = Request.Builder().url(
            "$apiurl?palavraChave=$palavrachave").get().build()


        doAsync{
            val response = client.newCall(request).execute()
            Log.d("HomeFragt","getand$response")
            var json = "nadinha"
            if(response.body()!==null) {
                json = response.body()!!.string();
                val imagemDaApi = Gson().fromJson(json,ImagemPesquisada::class.java)
                val imagemGrande = "${imagemDaApi.big}"
                val thumbP = "${imagemDaApi.thumb}"
                _ldImagem.postValue( imagemGrande)
            }


            Log.d("HomeFragt","ldImg eh ${_ldImagem.value}")

        }



    }
}