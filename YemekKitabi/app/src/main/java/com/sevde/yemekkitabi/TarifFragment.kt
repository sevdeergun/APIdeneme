package com.sevde.yemekkitabi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sevde.yemekkitabi.databinding.FragmentListeBinding
import com.sevde.yemekkitabi.databinding.FragmentTarifBinding

class TarifFragment : Fragment() {
        private var _binding: FragmentTarifBinding? = null
        private val binding get() = _binding!!
        private lateinit var permissionLauncher : ActivityResultLauncher<String>
        private lateinit var activityResultLauncher:ActivityResultLauncher<Intent>
        private var secilenGorsel : Uri? = null
        private var secilenBitmap : Bitmap?= null //uride path gibi bir şey veriyor, onu alıp görsele çevirmek için de bitmap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
    }

    private fun registerLauncher() { //registerları kaydetmek için yazdık, onCreate'te çağıracağız
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result -> //galeriye gitmek için, galeriye gitmenin sonucunu bize gerş döndürcek
            if(result.resultCode == AppCompatActivity.RESULT_OK){ //kullanıcı seçerse
                val intentFromResult = result.data
                if(intentFromResult != null){
                    secilenGorsel = intentFromResult.data
                    try{
                        if(Build.VERSION.SDK_INT >= 28){
                            val source = ImageDecoder.createSource(requireActivity().contentResolver,secilenGorsel!!)
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageView.setImageBitmap(secilenBitmap)

                        }
                        else{
                            secilenBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,secilenGorsel) //parantez içindeki kısım layoutInflater gibi
                            binding.imageView.setImageBitmap(secilenBitmap)
                        }
                    }
                    catch(e:Exception){
                        println(e.localizedMessage)

                    }
                }
            }
        }
        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                //izin verildi
                //galeriye gidebiliriz
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                ActivityResultLauncher.launch(intentToGallery)
            }
        } else{
                //izin verilmedi
            Toast.makeText(requireContext(),"izin verilmedi!",Toast.LENGTH_LONG).show()

            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentTarifBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener{gorselSec(it)}
        binding.kaydetButton.setOnClickListener{kaydet(it)}
        binding.silButton.setOnClickListener { sil(it) }

        arguments?.let{
            val bilgi = TarifFragmentArgs.fromBundle(it).bilgi

            if (bilgi == "yeni"){
                binding.silButton.isEnabled = false
                binding.kaydetButton.isEnabled = true
                binding.isimText.setText("")
                binding.malzemeText.setText("")
            }
            else{
                binding.silButton.isEnabled = true
                binding.kaydetButton.isEnabled = false
            }

        }

       //private fun registerLauncher() { ///registerları kaydetmek için yazdık, onCreate'te çağıracağız

       //}


    }
    fun kaydet(view:View){

    }
    fun sil (view:View){

    }
    fun gorselSec(view:View){
        İF(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) //daha önce izin verilmiş mi diye manifeste kadar kontrol ediyoruz
            //izin verilmemiş,izin istememiz gerekiyor
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) { //kullanıcıya iznin mantığını açıklamamız gerekiyor diyip kontrol ediyor

                    //snackbar göstermemiz lazım, kullanıcıdan neden izin istediğimizi bir kez daha söyleyerek izin istememiz lazım
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction( //duration yerine kullanıcı tuşa tıklayana kadar dedik
                        "İzin ver", //kullanıcı kapatmak için bu butona tıklamalı
                        View.OnClickListener {

                            //izin isteyeceğiz
                        }
                    ).show()
                }
        }

            else{
            //izin isteyeceğiz
                permissionLauncher.launch.(Manifest.permission.READ_EXTERNAL_STORAGE)

            }
        else{
            //izin verilmiş, galeriye gidebilirim
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            ActivityResultLauncher.launch(intentToGallery)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}