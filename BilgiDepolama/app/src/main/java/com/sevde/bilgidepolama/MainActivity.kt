package com.sevde.bilgidepolama

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sevde.bilgidepolama.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    var alinanKullaniciAdi : String? = null

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
        sharedPreferences = this.getSharedPreferences("com.sevde.bilgidepolama", Context.MODE_PRIVATE) //ilke oluşturulacak dosyanın ismi yazılır, genelde karışıklık olmaması için dosyanın ismi verilir, ikincisi ise moddur. Mode Private, oluşturduğumuz dosyaya başka biri erişemiyor demek
        alinanKullaniciAdi = sharedPreferences.getString("isim", "" ) //puttaki ve getteki isim aynı olmalı, o yüzden isim değişkeni verdik. Def value'da da aradığımız anahtarda bir değer yoksa ne yazayım demek
        if (alinanKullaniciAdi == ""){
            binding.textView.text = "Kaydedilen İsim"}
        else{
        }
    }

    fun kaydet (view:View){
        val kullaniciIsmi = binding.editTextText.text.toString()
        if (kullaniciIsmi == ""){
            Toast.makeText(this@MainActivity, "İsminizi Boş Bırakmayınız", Toast.LENGTH_LONG).show()
        }
        else {
            sharedPreferences.edit().putString("isim", kullaniciIsmi).apply()
            binding.textView.text = "Kaydedilen İsim: ${kullaniciIsmi}"
            }
        }

    }
    fun sil (view:View){
        val sharedPrefences = null
        var alinanKullaniciAdi = sharedPrefences.getString("isim", "")

        if(alinanKul
            lanici != "")

    }
