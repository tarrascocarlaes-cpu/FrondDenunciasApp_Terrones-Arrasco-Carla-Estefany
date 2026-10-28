package com.tuApellido.denunciasapp.model

import com.google.gson.annotations.SerializedName

data class Denuncia(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("dni")
    val dni: String = "",
    @SerializedName("fecha")
    val fecha: String? = null,
    @SerializedName("tipo")
    val tipo: String = "",
    @SerializedName("direccion")
    val direccion: String = "",
    @SerializedName("descripcion")
    val descripcion: String = "",
    @SerializedName("estado")
    val estado: Int = 1
)
