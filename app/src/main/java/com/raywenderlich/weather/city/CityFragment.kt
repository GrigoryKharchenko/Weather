package com.raywenderlich.weather.city

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
    private val adapter: CityAdapter = CityAdapter(onClick = {})

    //когда создается вью
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //вью созданно и выполняется тело
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCites() // TODO в viewModel в init
        //
        binding?.run {
            rvListCity.adapter = adapter
            // по нажатию на иконку стрелочки возвращается к предыдущему фрагменту
            toolBar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        viewModel.citesLiveData.observe(viewLifecycleOwner) { listCity ->
            adapter.cites = listCity
            adapter.notifyDataSetChanged() // TODO переделать, использовать notifyDataSetChanged нельзя! Используй DiffUtil
        }
    }

    companion object {
        fun newInstance() = CityFragment()
    }
}
