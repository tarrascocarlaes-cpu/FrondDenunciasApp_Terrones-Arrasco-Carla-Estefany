package com.tuApellido.denunciasapp.network

import com.tuApellido.denunciasapp.model.Denuncia
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface DenunciaApi {

    // Registrar denuncia (estado inicia en 1)
    @POST("denuncias/")
    suspend fun registrarDenuncia(@Body denuncia: Denuncia): Response<Denuncia>

    // Obtener denuncias paginadas
    @GET("denuncias/")
    suspend fun obtenerDenuncias(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<List<Denuncia>>

    // Obtener denuncia por id
    @GET("denuncias/{id}")
    suspend fun obtenerDenuncia(@Path("id") id: Int): Response<Denuncia>

    // Obtener denuncias de un ciudadano por DNI
    @GET("denuncias/ciudadano/{dni}")
    suspend fun obtenerPorCiudadano(@Path("dni") dni: String): Response<List<Denuncia>>

    // Atender denuncia (cambia estado de 1 a 0)
    @PUT("denuncias/{id}")
    suspend fun atenderDenuncia(@Path("id") id: Int): Response<Denuncia>
}
