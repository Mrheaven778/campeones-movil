package com.example.proyecto_final.ResultsViewHolder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Campeon
import com.example.proyecto_final.databinding.ItemSearchedPlayerBinding

class ChampViewHolder(view: View, listener:RecyclerViewInterface):RecyclerView.ViewHolder(view)
{
    private val binding = ItemSearchedPlayerBinding.bind(view)

    init{
        view.setOnClickListener {
            listener.onClick(adapterPosition)
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(player: Campeon)
    {
        // Actualiza las vistas con los datos del jugador
        binding.textName.text = player.name
        binding.textLane.text = player.lane
        binding.textDificulty.text = player.difficulty.toString()


    }


}