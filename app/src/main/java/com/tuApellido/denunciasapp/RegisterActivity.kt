package com.tuApellido.denunciasapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tuApellido.denunciasapp.model.Denuncia
import com.tuApellido.denunciasapp.viewmodel.DenunciaViewModel
import java.time.LocalDateTime

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: DenunciaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProvider(this)[DenunciaViewModel::class.java]

        findViewById<android.widget.ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val etDni = findViewById<EditText>(R.id.etDni)
        val etTipo = findViewById<EditText>(R.id.etTipo)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        viewModel.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
        viewModel.message.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }

        btnGuardar.setOnClickListener {
            val dni = etDni.text.toString().trim()
            val tipo = etTipo.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (dni.length != 8 || tipo.isBlank() || direccion.isBlank() || descripcion.isBlank()) {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val denuncia = Denuncia(
                dni = dni,
                fecha = LocalDateTime.now().toString(),
                tipo = tipo,
                direccion = direccion,
                descripcion = descripcion,
                estado = 1
            )

            viewModel.registrar(denuncia) { success ->
                if (success) finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
