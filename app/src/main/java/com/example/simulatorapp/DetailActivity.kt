package com.example.simulatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simulatorapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}