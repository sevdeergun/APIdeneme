package com.sevde.denemeg

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sevde.denemeg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var numara1 : Double? = null
    var numara2 : Double? = null
    var sonuc : Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun topla (view: View) {
        numara1 = binding.number1Text.text.toString().toDoubleOrNull()
        numara2 = binding.number2Text.text.toString().toDoubleOrNull()
        if (numara1 != null && numara2 != null) {
            sonuc = numara1!! + numara2!!
            binding.textView5.text = "Sonuç: ${sonuc}"
        } else {
            binding.textView5.text = "Numaraları giriniz"
        }
    }

    }
