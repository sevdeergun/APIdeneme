package com.sevde.androidalertdialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sevde.androidalertdialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
        Toast.makeText(this@MainActivity, "Hoşgeldiniz", Toast.LENGTH_LONG)
            .show() //Tost mesajı aşağıda gösterilen mesajdır. This ile mainActivity'i referans gösteriyoruz, ikinci olarak ne yazmak istediğimizi yazıyoruz, son olarak da duration yazıyoruz. En son show() ile mesajı gösteriyoruz. //onCreat'in içinde
        binding.button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) { //bunu açtığımız zaman altına yapılacak fonksiyonu yazmamız lazım
                println("butona tıklandı")
                this@MainActivity //burada this main activiteye referans vermiyor
            }

        }) //arayüz
    }

    fun kaydet(view: View) {
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Kayıt Et")
        alert.setMessage("Emin misiniz?")

        alert.setPositiveButton("evet") { dialog, which ->
            Toast.makeText(this@MainActivity, "Kayıt edildi", Toast.LENGTH_LONG).show()
        }  //birinci kaydedince ne gösterilecek yani butonın adı ne olacak, ikinci listener

        /*alert.setNegativeButton("hayır") {dialog, which ->
            Toast.makeText(this@MainActivity, "Kaydedilemedi", Toast.LENGTH_LONG).show()
        }*/

        alert.setNegativeButton("Hayır", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(this@MainActivity, "Kayıt İptal edildi", Toast.LENGTH_LONG).show()

            }
        })
        alert.show()
    }
}

