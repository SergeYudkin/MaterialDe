package com.example.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.material.databinding.FragmentSettingsBinding
import com.example.material.pictureoftheday.PictureOfTheDayFragment
import com.example.material.utils.Parameters

class MainActivity : AppCompatActivity() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(Parameters.getInstance().theme)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.container,
                PictureOfTheDayFragment.newInstance()).commit()


        }


    }
}