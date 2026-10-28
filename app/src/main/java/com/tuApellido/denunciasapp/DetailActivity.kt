package com.tuApellido.denunciasapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tuApellido.denunciasapp.viewmodel.DenunciaViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DenunciaViewModel
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this)[DenunciaViewModel::class.java]

        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener { finish() }
        id = intent.getIntExtra("id", 0)

        val tvId = findViewById<TextView>(R.id.tvDetailId)
        val tvDni = findViewById<TextView>(R.id.tvDetailDni)
        val tvFecha = findViewById<TextView>(R.id.tvDetailFecha)
        val tvTipo = findViewById<TextView>(R.id.tvDetailTipo)
        val tvDireccion = findViewById<TextView>(R.id.tvDetailDireccion)
        val tvDescripcion = findViewById<TextView>(R.id.tvDetailDescripcion)
        val tvEstado = findViewById<TextView>(R.id.tvDetailEstado)
        val btnAtender = findViewById<Button>(R.id.btnAtender)

        viewModel.selected.observe(this) { d ->
            d ?: return@observe
            tvId.text = "ID: ${d.id}"
            tvDni.text = "DNI: ${d.dni}"
            tvFecha.text = "Fecha: ${d.fecha}"
            tvTipo.text = "Tipo: ${d.tipo}"
            tvDireccion.text = "Dirección: ${d.direccion}"
            tvDescripcion.text = "Descripción: ${d.descripcion}"
            tvEstado.text = "Estado: ${if (d.estado == 1) "Pendiente" else "Atendida"}"
            btnAtender.isEnabled = d.estado == 1
        }

        viewModel.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
        viewModel.message.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        btnAtender.setOnClickListener {
            viewModel.atender(id) { success ->
                if (success) {
                    btnAtender.isEnabled = false
                    setResult(RESULT_OK)
                }
            }
        }

        if (id != 0) viewModel.loadDetail(id)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
