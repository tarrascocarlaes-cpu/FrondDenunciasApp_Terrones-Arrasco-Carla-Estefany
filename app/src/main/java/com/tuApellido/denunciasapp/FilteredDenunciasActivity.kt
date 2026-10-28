package com.tuApellido.denunciasapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuApellido.denunciasapp.adapter.DenunciaAdapter
import com.tuApellido.denunciasapp.model.Denuncia
import com.tuApellido.denunciasapp.repository.DenunciaRepository
import kotlinx.coroutines.launch

class FilteredDenunciasActivity : AppCompatActivity() {

    private lateinit var adapter: DenunciaAdapter
    private val repository = DenunciaRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtered)

        val estado = intent.getIntExtra("estado", 1)
        val title = intent.getStringExtra("title") ?: "Denuncias"

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        adapter = DenunciaAdapter { denuncia ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("id", denuncia.id)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val all = mutableListOf<Denuncia>()
            var offset = 0
            val limit = 5
            while (true) {
                val result = repository.getDenuncias(offset, limit)
                val page = result.getOrElse {
                    Toast.makeText(this@FilteredDenunciasActivity, it.message, Toast.LENGTH_SHORT).show()
                    break
                }
                if (page.isEmpty()) break
                all.addAll(page)
                offset += page.size
                if (page.size < limit) break
            }
            adapter.submitList(all.filter { it.estado == estado })
            progressBar.visibility = View.GONE
        }
    }
}
