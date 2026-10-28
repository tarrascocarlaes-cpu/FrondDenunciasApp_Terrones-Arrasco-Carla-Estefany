package com.tuApellido.denunciasapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuApellido.denunciasapp.adapter.DenunciaAdapter
import com.tuApellido.denunciasapp.repository.DenunciaRepository
import kotlinx.coroutines.launch

class SearchDenunciasActivity : AppCompatActivity() {

    private val repository = DenunciaRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val etDni = findViewById<EditText>(R.id.etDni)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val adapter = DenunciaAdapter { denuncia ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", denuncia.id)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnSearch.setOnClickListener {
            val dni = etDni.text.toString().trim()
            if (dni.isBlank()) {
                Toast.makeText(this, "Ingrese un DNI", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                repository.getByCiudadano(dni).fold(
                    onSuccess = { adapter.submitList(it) },
                    onFailure = { Toast.makeText(this@SearchDenunciasActivity, it.message, Toast.LENGTH_SHORT).show() }
                )
                progressBar.visibility = View.GONE
            }
        }
    }
}
