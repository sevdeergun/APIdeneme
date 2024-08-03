package com.sevde.superkahramankitab

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sevde.superkahramankitab.databinding.RecyclerRowBinding

class SuperKahramanAdapter(val superKahramanListesi : ArrayList<SuperKahramanSinifi>):RecyclerView.Adapter<SuperKahramanAdapter.SuperKahramanViewHolder>() { //superkahramanadapterini oluşturmaya çalışınca bizden superkahramanlistesini isteyecek
    class SuperKahramanViewHolder(val binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){ //gibi bir gösterim var, bu oluştuğu için SuperKahramanViewHolder'ı yazabildik üste

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperKahramanViewHolder { //initialize etmek için
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return SuperKahramanViewHolder(binding)
    }

    override fun getItemCount(): Int { //recycle row'dan kaç tane oluşturacağını göstermek için (return 10 dersek 10 tane oluşturulacak)
        return superKahramanListesi.size
    }

    override fun onBindViewHolder(holder: SuperKahramanViewHolder, position: Int) { //işlemler bitince ne yapacak,genelde ilkler çok değiştirilmez ama baştakiler değiştirilir
        holder.binding.textViewRecyclerView.text=superKahramanListesi[position].isim

        holder.itemView.setOnLongClickListener {
            val intent = Intent(holder.itemView.context, TanitimAktivitesi::class.java)
            intent.putExtra("secilenKahraman",superKahramanListesi[position])
            holder.itemView.context.startActivity(intent)
            true
        }


        }
    }
