package com.example.material.pictureoftheday

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.material.MainActivity
import com.example.material.R
import com.example.material.databinding.FragmentPictureOfTheDayBinding
import com.example.material.settings.SettingsFragment
import com.example.material.utils.Parameters
import com.example.material.viewmodel.AppState
import com.example.material.viewmodel.PictureOfTheDayViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior


class PictureOfTheDayFragment : Fragment() {


    private var _binding : FragmentPictureOfTheDayBinding? = null
    private val binding : FragmentPictureOfTheDayBinding
        get(){
            return _binding!!
        }

    private val viewModel: PictureOfTheDayViewModel by lazy{
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
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
        request()
        clickButtonStyle()
        BSB ()
        clickWiki()
        actionBar()
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

    private fun BSB (){
        val params = (binding.lifeHack.bottomSheetContainer.layoutParams as CoordinatorLayout.LayoutParams)
        val behavior =  params.behavior as BottomSheetBehavior
        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        behavior. addBottomSheetCallback(object: BottomSheetBehavior
        .BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState){

                    /*   BottomSheetBehavior.STATE_COLLAPSED -> { TOD() }
                       BottomSheetBehavior.STATE_DRAGGING -> { TOD() }
                       BottomSheetBehavior.STATE_EXPANDED -> { TOD() }
                       BottomSheetBehavior.STATE_HALF_EXPANDED -> { TOD() }
                       BottomSheetBehavior.STATE_HIDDEN -> { TOD() }
                       BottomSheetBehavior.STATE_SETTLING -> { TOD() }*/
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("@@@","$slideOffset slideOffset")
            }

        })
    }

    private fun actionBar(){
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
    }

    private fun clickWiki(){

        binding.inputLayout.setEndIconOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }

    }


    private fun request(){
        viewModel.getLiveDataForViewToObserve().observe(viewLifecycleOwner) {
            renderData(it)
        }
        viewModel.sendServerRequest()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {}
            is AppState.Loading -> {
                BottomSheetBehavior.STATE_HALF_EXPANDED
               // binding.imageView.load(drawable)
            }
            is AppState.Success -> {

                BottomSheetBehavior.STATE_HIDDEN
                binding.imageView.load(appState.serverResponseData.hdurl){

                }

                binding.lifeHack.title.text = appState.serverResponseData.title
                binding.lifeHack.explanation.text = appState.serverResponseData.explanation

            }
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