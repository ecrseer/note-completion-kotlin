package com.example.myapplication.view.listaimageminicial

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainViewModel
import com.example.myapplication.MainViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.view.tabs.TabFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var columnCount = 1
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt("ARG_COLUMN_COUNT")
        }
    }
    fun clicarNoItemAbreNota(posicao:Int){
            //todo
        val acao =
            TabFragmentDirections.actionTabFragmentToNotaViewPagerFragment(posicao,false)

        findNavController().navigate(acao)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_imagem_item_list, container, false)

        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())[MainViewModel::class.java]

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }


                adapter = ListaImagemPesquisadaRecyclerViewAdapter(mainViewModel.notasImgs.value!!){
                    posicao->clicarNoItemAbreNota(posicao)
                }
                mainViewModel.notasImgs.observe(viewLifecycleOwner,Observer{
                    adapter = ListaImagemPesquisadaRecyclerViewAdapter(it){
                            posicao->clicarNoItemAbreNota(posicao)
                    }
                    Toast.makeText(activity,"Mudei recyc",Toast.LENGTH_LONG+4242).show()
                })
            }
        }
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}