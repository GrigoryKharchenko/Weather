package com.raywenderlich.weather.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raywenderlich.weather.databinding.FragmentCustomDialogBinding

class CityFragment : Fragment() {
    private var binding: FragmentCustomDialogBinding? = null
    private val viewModel: CityViewModel by viewModels()
    private val adapter by lazy {
        CityAdapter(choseCity = ::cites)
    }

    //часть фрагмента,которая пишется всегда
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding?.root
    }

    //часть фрагмента,которая пишется всегда
    @SuppressLint("NotifyDataSetChanged") // TODO что за?
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCites() // TODO в viewModel в init
        binding?.rvListCity?.adapter = adapter
        // по нажатию на иконку стрелочки возвращается к предыдущему фрагменту
        binding?.toolBar?.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
        viewModel.citesLiveData.observe(viewLifecycleOwner) {
            adapter.cites = it
            adapter.notifyDataSetChanged() // TODO переделать, использовать notifyDataSetChanged нельзя! Используй DiffUtil
        }
    }

    private fun cites(cites: String) {
    }

    companion object {

        fun newInstance() = CityFragment()
    }
}
