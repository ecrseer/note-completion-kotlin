package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.PersistenciaDadosNotas
import com.example.myapplication.ui.listaimageminicial.ListaNotasViewModel

import com.example.myapplication.ui.tabs.TabFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var listaNotasViewModel: ListaNotasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listaNotasViewModel=ViewModelProvider(this,MainViewModelFactory())
            .get(ListaNotasViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populaImgs()
    }

    private fun populaImgs() {
        PersistenciaDadosNotas.imgs.forEach { img ->
            img.big = getString(R.string.imagemTeste)
        }
    }

    fun setupNavegacao() {
        navController = findNavController(R.id.Nav_hostNovo)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val fabDeveSumir = destination.id == R.id.NotaViewPagerFragment
            if (fabDeveSumir) {
                binding.fab?.visibility = View.GONE
            } else {
                binding.fab?.visibility = View.VISIBLE
            }

        }
        binding.fab.setOnClickListener { view ->
            val isNotaNova = true;
            val imagemPlaceholdr = getString(R.string.imagemTeste)
            val tamanhoDaLista = listaNotasViewModel.criaNota(imagemPlaceholdr)
            if(tamanhoDaLista!=null){
                val action = TabFragmentDirections
                    .actionTabFragmentToNotaViewPagerFragment(tamanhoDaLista, isNotaNova)
                navController.navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.toolbar)
        setupNavegacao()

        //supportActionBar?.setDisplayShowTitleEnabled(false)

    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.Nav_hostNovo)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}