package com.example.project_aksis.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_aksis.R

import com.example.project_aksis.databinding.ActivityMainBinding
import com.example.project_aksis.util.BaseFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : AppCompatActivity() {

    @Inject lateinit var fragmentFactory : BaseFragmentFactory
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = fragmentFactory

        setTheme(R.style.AppTheme)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }



}

