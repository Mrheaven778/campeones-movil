package com.example.proyecto_final

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto_final.ResultsViewHolder.ChampAdapter
import com.example.proyecto_final.ResultsViewHolder.RecyclerViewInterface
import com.example.proyecto_final.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.widget.SearchView



class CampeonActivity : AppCompatActivity() ,  OnQueryTextListener, SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ChampAdapter
    private val ListaJugadores = mutableListOf<Campeon>()
    private val ListaJugadores2 = mutableListOf<Campeon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar la busqueda
        binding.svPlayers.setOnQueryTextListener(this)

        //INICIALIZAR RECYCLERVIEW
        initRecyclerView()
        allChamps()
    }

    private fun allChamps() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getAllChamps()
            val jugadores = call.body()
            val statusCode = call.code()
            if (call.isSuccessful) {
                val players = jugadores?.campeones
                runOnUiThread {
                    ListaJugadores.clear()
                    ListaJugadores.addAll(players!!)
                    adapter.notifyDataSetChanged()
                    adapter.setOnItemClickListener(object : RecyclerViewInterface {
                        override fun onClick(position: Int) {
                            val intent = Intent(this@CampeonActivity, ChampsActivity::class.java)
                            intent.putExtra("IDCampeon", ListaJugadores[position].id)
                            startActivity(intent)
                        }
                    })
                }
            } else {
                showError()
                Log.d("Retrofit1", "URL: ${call.errorBody()}")
                Log.d("Retrofit2", "Status Code: $statusCode")
            }
        }
    }

    //Inicializar RecyclerView
    private fun initRecyclerView()
    {
        adapter = ChampAdapter(ListaJugadores)
        binding.rvPlayers.layoutManager = LinearLayoutManager(this)
        binding.rvPlayers.adapter = adapter
    }

    //Como vamos a obtener la información
    private fun getRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl("https://proyecto-final-api-two.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    //Funcion asincrona que busca a los jugadores por su nombre
    private fun searchByName(name:String)
    {
        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(APIService::class.java).getPlayersByName("champion/name/$name")
            val jugadores = call.body()

            val statusCode = call.code()

            runOnUiThread {
                //si la llamada ha funcionado
                if (call.isSuccessful)
                {
                    val players = jugadores?.campeones
                    runOnUiThread {
                        ListaJugadores.clear()
                        ListaJugadores.addAll(players!!)
                        adapter.notifyDataSetChanged()
                        adapter.setOnItemClickListener(object : RecyclerViewInterface {
                            override fun onClick(position: Int) {
                                val intent = Intent(this@CampeonActivity, ChampsActivity::class.java)
                                intent.putExtra("IDCampeon", ListaJugadores[position].id)
                                startActivity(intent)
                            }
                        })
                    }
                    adapter.notifyDataSetChanged()
                }
                else {
                    //algo ha salido mal, mostrar error
                    showError()
                    Log.d("Retrofit1", "URL: ${call.errorBody()}")
                    Log.d("Retrofit2", "Status Code: $statusCode")
                }
            }
        }
    }

    private fun showError()
    {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(name: String?): Boolean
    {
        if(!name.isNullOrEmpty())
        {
            //Método que busca a los jugadores, está arriba
            searchByName(name)
        }

        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean
    {
        return true
    }
}


