package com.tuApellido.denunciasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.jvm.JvmOverloads
import androidx.lifecycle.viewModelScope
import com.tuApellido.denunciasapp.model.Denuncia
import com.tuApellido.denunciasapp.repository.DenunciaRepository
import kotlinx.coroutines.launch

class DenunciaViewModel @JvmOverloads constructor(
    private val repository: DenunciaRepository = DenunciaRepository()
) : ViewModel() {

    private val _allDenuncias = MutableLiveData<List<Denuncia>>(emptyList())
    private val _denuncias = MutableLiveData<List<Denuncia>>(emptyList())
    val denuncias: LiveData<List<Denuncia>> = _denuncias

    private val _selected = MutableLiveData<Denuncia?>()
    val selected: LiveData<Denuncia?> = _selected

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    var offset: Int = 0
        private set
    val limit: Int = 5

    private var currentFilter: Filter = Filter.ALL

    enum class Filter { ALL, PENDING, ATTENDED }

    private fun applyFilter() {
        val all = _allDenuncias.value.orEmpty()
        _denuncias.value = when (currentFilter) {
            Filter.PENDING -> all.filter { it.estado == 1 }
            Filter.ATTENDED -> all.filter { it.estado == 0 }
            Filter.ALL -> all
        }
    }

    fun loadFirstPage() {
        offset = 0
        _allDenuncias.value = emptyList()
        loadMore()
    }

    fun loadMore() {
        viewModelScope.launch {
            _loading.value = true
            repository.getDenuncias(offset, limit).fold(
                onSuccess = { list ->
                    val current = _allDenuncias.value.orEmpty()
                    _allDenuncias.value = current + list
                    offset += list.size
                    applyFilter()
                },
                onFailure = { _error.value = it.message }
            )
            _loading.value = false
        }
    }

    fun searchByDni(dni: String) {
        if (dni.isBlank()) {
            loadFirstPage()
            return
        }
        viewModelScope.launch {
            _loading.value = true
            repository.getByCiudadano(dni).fold(
                onSuccess = { list ->
                    _allDenuncias.value = list
                    offset = list.size
                    applyFilter()
                },
                onFailure = { _error.value = it.message }
            )
            _loading.value = false
        }
    }

    fun showPending() {
        currentFilter = Filter.PENDING
        applyFilter()
    }

    fun showAttended() {
        currentFilter = Filter.ATTENDED
        applyFilter()
    }

    fun showAll() {
        currentFilter = Filter.ALL
        applyFilter()
    }

    fun loadDetail(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            repository.getDenuncia(id).fold(
                onSuccess = { _selected.value = it },
                onFailure = { _error.value = it.message }
            )
            _loading.value = false
        }
    }

    fun registrar(denuncia: Denuncia, onDone: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            repository.registrar(denuncia).fold(
                onSuccess = {
                    _message.value = "Denuncia registrada"
                    onDone(true)
                },
                onFailure = {
                    _error.value = it.message
                    onDone(false)
                }
            )
            _loading.value = false
        }
    }

    fun atender(id: Int, onDone: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            repository.atender(id).fold(
                onSuccess = { updated ->
                    _selected.value = updated
                    _message.value = "Denuncia atendida"
                    onDone(true)
                },
                onFailure = {
                    _error.value = it.message
                    onDone(false)
                }
            )
            _loading.value = false
        }
    }

    fun clearMessages() {
        _error.value = null
        _message.value = null
    }
}
