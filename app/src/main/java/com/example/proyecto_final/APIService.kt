package com.example.proyecto_final

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface  APIService
{
    @GET
    suspend fun getPlayersByName(@Url name:String):Response<AllCampeones>
    @GET
    suspend fun getPlayersById(@Url id:String):Response<Campeon>
    @GET("test")
    suspend fun getAllChamps():Response<AllCampeones>
}