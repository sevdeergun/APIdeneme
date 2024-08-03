package com.sevde.kullaniciarayuzu

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sevde.kullaniciarayuzu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        println("onCreate çalıştırıldı.")

    }

    fun sonrakiSayfa (view : View){
        //val kullaniciGirdisi = binding.editText.text.toString() //Kullanıcıdan veri almak - editText'i binding'e bağladık ve text(get text gibi bir anlamı var)
        //binding.textView.text = "İsim: ${kullaniciGirdisi}"
        val intent = Intent(this, SecondActivity::class.java) //this içinde bulunduğumuz sınıfa referans verir, SecondActivity::class.java sınıfına da referans verir
        //startActivity(intent) //hangi aktiviteyi çalıştırdığımızı yazıyoruz
        val kullaniciGirdisi = binding.editText.text.toString()
        intent.putExtra("isim",kullaniciGirdisi) //isim i biz veriyoruz, yollanan verinin ismi o
        startActivity(intent)

    }
    override fun onStart(){
        super.onStart()
        println("onStart çalıştırıldı")
    }

    override fun onResume(){
        super.onResume()
        println("onResume çalıştırıldı")
    }
    override fun onPause(){
        super.onPause()
        println("onPause çalıştırıldı")
    }

    override fun onStop() {
        super.onStop()
        println("onStop çalıştırıldı")
    }
    override fun onDestroy(){
        super.onDestroy()
        println("onDestroy çalıştırıldı")
    }


}