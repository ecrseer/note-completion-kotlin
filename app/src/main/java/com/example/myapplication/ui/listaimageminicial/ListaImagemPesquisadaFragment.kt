package com.example.myapplication.ui.listaimageminicial

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentImagemItemListBinding
import com.example.myapplication.ui.tabs.TabFragmentDirections

/**
 * A fragment representing a list of Items.
 */
class ListaImagemPesquisadaFragment : Fragment() {

    private var _binding: FragmentImagemItemListBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())
                .get(MainViewModel::class.java)


    }

    fun estabelecePesquisaPorNotas(searchView: SearchView){

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("Not yet implemented")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return mainViewModel.peneiraNotaPorTexto("$newText")
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        if(findNavController().currentDestination?.id == R.id.NotaViewPagerFragment){
            inflater.inflate(R.menu.menu_nota,menu)
        }
        else{
            inflater.inflate(R.menu.menu_main,menu)
            val searchWdgt = menu.findItem(R.id.app_bar_search)
            val actionViewPesquisa: SearchView = searchWdgt?.actionView as SearchView
            estabelecePesquisaPorNotas(actionViewPesquisa)
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentImagemItemListBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun inscreverObserver() {
        with(binding.list.adapter as ListaImagemPesquisadaRecyclerViewAdapter){
          mainViewModel.notasImgs.observe(viewLifecycleOwner, Observer {
                this.mudarLista(it)
            })

        }
    }

    val clicarNoItemAbreNota={posicao: Int->
        val acao = TabFragmentDirections.actionTabFragmentToNotaViewPagerFragment(posicao, false)
        findNavController().navigate(acao)
    }

    private fun desenhaListaNotas(){

        with(binding.list) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            this.addItemDecoration(
                DividerItemDecoration(
                    requireActivity().baseContext,
                    DividerItemDecoration.VERTICAL
                ),
            )
            val listaNotas= mainViewModel.notasImgs.value
            if(listaNotas!=null) {
                adapter = ListaImagemPesquisadaRecyclerViewAdapter(
                    listaNotas, clicarNoItemAbreNota  )
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        desenhaListaNotas()
        inscreverObserver()
        setHasOptionsMenu(true);

    }

    override fun onResume() {
        super.onResume()
        with(binding.list.adapter as ListaImagemPesquisadaRecyclerViewAdapter){
            this.notifyDataSetChanged()
        }

    }


}