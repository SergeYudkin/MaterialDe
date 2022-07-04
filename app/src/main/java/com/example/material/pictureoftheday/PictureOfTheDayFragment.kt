package com.example.material.pictureoftheday

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
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
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.switchmaterial.SwitchMaterial
import java.text.SimpleDateFormat
import java.util.*


class PictureOfTheDayFragment : Fragment() {


    private var isMain = true

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
        switchNightMode()
        clickChip()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar,menu)
    }
    private fun switchNightMode(){
        requireActivity().findViewById<SwitchMaterial>(R.id.switch_night_mode).setOnCheckedChangeListener{ buttonView, isChecked->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DAY_OF_MONTH, count)
        val format1 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format1.timeZone = TimeZone.getTimeZone("EST")
        return format1.format(currentDate.time)
    }

    private fun clickChip(){
        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.yesterday ->{viewModel.sendServerRequest(takeDate(-1))}
                R.id.day_before_yesterday->{viewModel.sendServerRequest(takeDate(-2))}
                R.id.today ->{viewModel.sendServerRequest()}

            }
        }
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
               // binding.imageView.load(drawable.)
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