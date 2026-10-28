package com.tuApellido.denunciasapp.repository

import com.tuApellido.denunciasapp.model.Denuncia
import com.tuApellido.denunciasapp.network.DenunciaApi
import com.tuApellido.denunciasapp.network.RetrofitClient

class DenunciaRepository(
    private val api: DenunciaApi = RetrofitClient.api
) {

    suspend fun getDenuncias(offset: Int, limit: Int): Result<List<Denuncia>> {
        return try {
            val response = api.obtenerDenuncias(offset, limit)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDenuncia(id: Int): Result<Denuncia> {
        return try {
            val response = api.obtenerDenuncia(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getByCiudadano(dni: String): Result<List<Denuncia>> {
        return try {
            val response = api.obtenerPorCiudadano(dni)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registrar(denuncia: Denuncia): Result<Denuncia> {
        return try {
            val response = api.registrarDenuncia(denuncia)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun atender(id: Int): Result<Denuncia> {
        return try {
            val response = api.atenderDenuncia(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
