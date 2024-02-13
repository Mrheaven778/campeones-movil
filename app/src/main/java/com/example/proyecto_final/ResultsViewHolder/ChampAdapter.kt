package com.example.proyecto_final.ResultsViewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Campeon
import com.example.proyecto_final.R

class ChampAdapter(val players:List<Campeon>): RecyclerView.Adapter<ChampViewHolder>()
{
    private lateinit var mListener : RecyclerViewInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChampViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = (layoutInflater.inflate(R.layout.item_searched_player, parent, false))
        return  ChampViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: ChampViewHolder, position: Int)
    {
        val item: Campeon = players[position]
        holder.bind(item)
    }

    fun setOnItemClickListener(listener: RecyclerViewInterface)
    {
        mListener = listener
    }
}