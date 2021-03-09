package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.jetpacktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var viewModel : MainViewModel
    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved",0)

        viewModel = ViewModelProviders.of(this,MainViewModelFactory(countReserved)).get(MainViewModel::class.java)



        binding.buttonCount.setOnClickListener {
            viewModel.counter++
            refreshCount()
        }

        binding.buttonClear.setOnClickListener {
            viewModel.counter = 0
            refreshCount()
        }

        refreshCount()
        setContentView(binding.root)
    }

    private fun refreshCount() {
        binding.countTv.text = viewModel.counter.toString()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved",viewModel.counter)
        }
    }
}