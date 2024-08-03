package com.sevde.kullaniciarayuzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sevde.kullaniciarayuzu.databinding.ActivityMainBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val maindenGelenIntent = intent //intent- get intent demek, putla yolladığımızı alıp maindenGelenIntent'e atıyoruz
        val yollananIsim = maindenGelenIntent.getStringExtra("isim") //String yolladığımız için stringExtra, yollanan isim de bizim verdiğimiz değişken adı
        binding.editText.text = yollananIsim
        }
    }
}