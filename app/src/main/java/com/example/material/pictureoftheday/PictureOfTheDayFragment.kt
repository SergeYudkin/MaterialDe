package com.example.material.pictureoftheday

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.material.R
import com.example.material.databinding.FragmentPictureOfTheDayBinding
import com.example.material.settings.SettingsFragment
import com.example.material.utils.Parameters


class PictureOfTheDayFragment : Fragment() {


    private var _binding : FragmentPictureOfTheDayBinding? = null
    private val binding : FragmentPictureOfTheDayBinding
        get(){
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("").commit()
            android.R.id.home-> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager,"")
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        clickButtonStyle()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }

    private fun clickButtonStyle() {


        binding.buttonBlu.setOnClickListener {
            Parameters.getInstance().theme = R.style.MyBlueTheme
            requireActivity().recreate()

        }

        binding.buttonRed.setOnClickListener {
            Parameters.getInstance().theme = R.style.MyRedTheme
            requireActivity().recreate()

        }

        binding.buttonGreen.setOnClickListener {
            Parameters.getInstance().theme = R.style.MyGreenTheme
            requireActivity().recreate()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PictureOfTheDayFragment()
    }

}