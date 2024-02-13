package com.example.proyecto_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.proyecto_final.databinding.ActivityPlayer2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChampsActivity : AppCompatActivity() {

    private var jugador:Campeon? = null
    private lateinit var idPlayer:String
    //Componentes que cambiarán al abrir la activity
    private lateinit var binding: ActivityPlayer2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player2)

        binding = ActivityPlayer2Binding.inflate(layoutInflater)
        idPlayer = intent.extras?.getString("IDCampeon").orEmpty()
        Log.d("IDCampeon",idPlayer)
        searchPlayerById()

        setContentView(binding.root)

        //inicializar recyclerView de las estadísticas
    }

    //Como vamos a obtener la información
    private fun getRetrofit(): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl("https://proyecto-final-api-two.vercel.app/api/champion/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchPlayerById()
    {

        CoroutineScope(Dispatchers.IO).launch{
            val call = getRetrofit().create(APIService::class.java).getPlayersById(idPlayer)
            //obtener respuesta
            val jugadores = call.body()

            //ejecutar en el hilo principal
            runOnUiThread{
                //si la llamada ha funcionado
                if(call.isSuccessful)
                {
                    //si es nulo, devuelve lista vacia
                    jugador = jugadores
                    //Obtener el jugador del que se va a mostrar la información

                    changeData()
                }
                else
                {
                    Log.d("ERROR","NO HAY RESPUESTA")
                    //algo ha salido mal, mostrar error
                    showError()
                }
            }
        }
    }

    private fun showError()
    {
        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
    }

    private fun changeData()
    {

        binding.textName.text = jugador?.name
        binding.textLinea.text = jugador?.lane
        binding.textDifficulty.text = jugador?.difficulty.toString()
        binding.textLore.text = jugador?.lore
        binding.ID.text = jugador?.id
        binding.textAttack.text = jugador?.attackType
        binding.textLanzamiento.text = jugador?.releaseYear.toString()
        binding.textRole.text = jugador?.role

    }

}