package com.example.proyecto_final

import com.google.gson.annotations.SerializedName

//SE CREAN DIFERENTES CLASES YA QUE EL JSON QUE DEVUELVE LA API TIENE DIFERENTES OBJETOS ANIDADOS EN EL

data class AllCampeones(
    @SerializedName("data") val campeones: List<Campeon>,
)

data class Campeon(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String,
    @SerializedName("lane") val lane: String,
    @SerializedName("attackType") val attackType: String,
    @SerializedName("difficulty") val difficulty: Int,
    @SerializedName("releaseYear") val releaseYear: Int,
    @SerializedName("lore") val lore: String,
)
