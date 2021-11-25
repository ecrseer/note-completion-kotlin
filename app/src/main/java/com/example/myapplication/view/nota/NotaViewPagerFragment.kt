package com.example.myapplication.view.nota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.MainViewModel
import com.example.myapplication.MainViewModelFactory
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNotaViewPagerBinding
import com.example.myapplication.model.NoteImagens
import com.example.myapplication.model.ImagemPesquisada


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotaViewPagerFragment : Fragment() {

    private var _binding: FragmentNotaViewPagerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val args: NotaViewPagerFragmentArgs by navArgs()
    private var posicao:Int=0
    private lateinit var mainViewModel: MainViewModel
    private var isNotaNovaDeveSerCriada = false

    private fun inicializaArgs(){
        if( args.posicao!=null){
            posicao = args.posicao
        }
        if(args.isNotaNova){
            isNotaNovaDeveSerCriada = args.isNotaNova
            val imagemPlaceholdr = getString(R.string.imagemTeste)
            val notaImgTemporaria = ImagemPesquisada(
                "$imagemPlaceholdr","","","")
            mainViewModel.notasImgs.value?.add(notaImgTemporaria)

            posicao = mainViewModel.notasImgs.value?.size?.minus(1) ?: posicao

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotaViewPagerBinding.inflate(inflater, container, false)

        mainViewModel =
            ViewModelProvider(requireActivity(), MainViewModelFactory())[MainViewModel::class.java]

        if(args!=null){
             inicializaArgs()
        }

        val adaptr = SliderAdapter(requireActivity(),
            mainViewModel.notasImgs.value?.size)
        binding.pager.adapter =  adaptr
        binding.pager.currentItem = posicao

        return binding.root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}