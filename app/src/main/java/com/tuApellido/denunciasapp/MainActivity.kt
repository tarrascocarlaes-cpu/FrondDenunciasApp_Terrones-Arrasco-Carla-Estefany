package com.tuApellido.denunciasapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuApellido.denunciasapp.adapter.DenunciaAdapter
import com.tuApellido.denunciasapp.viewmodel.DenunciaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DenunciaViewModel
    private lateinit var adapter: DenunciaAdapter

    private val detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.loadFirstPage()
    }
    private val registerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        viewModel.loadFirstPage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[DenunciaViewModel::class.java]
        adapter = DenunciaAdapter { denuncia ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", denuncia.id)
            detailLauncher.launch(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val btnBuscarDni = findViewById<Button>(R.id.btnBuscarDni)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnPending = findViewById<Button>(R.id.btnPending)
        val btnAttended = findViewById<Button>(R.id.btnAttended)
        val btnLoadMore = findViewById<Button>(R.id.btnLoadMore)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.denuncias.observe(this) { list ->
            adapter.submitList(list.toList())
        }
        viewModel.loading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        btnBuscarDni.setOnClickListener {
            startActivity(Intent(this, SearchDenunciasActivity::class.java))
        }
        btnRegister.setOnClickListener {
            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
        }
        btnPending.setOnClickListener {
            val intent = Intent(this, FilteredDenunciasActivity::class.java)
            intent.putExtra("estado", 1)
            intent.putExtra("title", "Denuncias Pendientes")
            startActivity(intent)
        }
        btnAttended.setOnClickListener {
            val intent = Intent(this, FilteredDenunciasActivity::class.java)
            intent.putExtra("estado", 0)
            intent.putExtra("title", "Denuncias Atendidas")
            startActivity(intent)
        }
        btnLoadMore.setOnClickListener {
            viewModel.loadMore()
        }

        viewModel.loadFirstPage()
    }
}
