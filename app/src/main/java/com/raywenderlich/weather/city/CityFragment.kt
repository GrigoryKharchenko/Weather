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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: CityViewModel by viewModels()
        viewModel.loadCites()

        viewModel.citesLiveData.observe(viewLifecycleOwner) {
            binding?.listCity?.adapter = CityAdapter(it)
        }
    }

    companion object {
        const val TAG = "FragmentCity"

        fun newInstance() = CityFragment()
    }
}