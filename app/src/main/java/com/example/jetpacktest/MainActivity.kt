package com.example.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.edit
import androidx.lifecycle.Lifecycle
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
            viewModel.plusOne()
        }

        binding.buttonClear.setOnClickListener {
           viewModel.clear()
        }

        viewModel.counter.observe(this){ count ->
            binding.countTv.text = count.toString()
        }

        lifecycle.addObserver(MyObserver(this.lifecycle))



        binding.getUserButton.setOnClickListener {
            val userId = (0..1000).random().toString()
            viewModel.getUser(userId)
        }
        viewModel.user.observe(this){user ->
            binding.countTv.text = user.firstName
        }


        setContentView(binding.root)
    }



    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved",viewModel.counter.value?:0)
        }
    }
}