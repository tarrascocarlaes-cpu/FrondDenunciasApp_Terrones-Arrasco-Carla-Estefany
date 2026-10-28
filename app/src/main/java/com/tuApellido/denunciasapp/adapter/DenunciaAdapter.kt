package com.tuApellido.denunciasapp.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuApellido.denunciasapp.R
import com.tuApellido.denunciasapp.model.Denuncia

class DenunciaAdapter(
    private val onClick: (Denuncia) -> Unit
) : ListAdapter<Denuncia, DenunciaAdapter.VH>(DIFF) {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val statusIndicator: View = itemView.findViewById(R.id.statusIndicator)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val tvDni: TextView = itemView.findViewById(R.id.tvDni)
        val tvTipo: TextView = itemView.findViewById(R.id.tvTipo)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
        val tvDireccion: TextView = itemView.findViewById(R.id.tvDireccion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_denuncia, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        holder.tvId.text = "ID: ${item.id}"
        holder.tvDni.text = "DNI: ${item.dni}"
        holder.tvTipo.text = "Tipo: ${item.tipo}"
        holder.tvEstado.text = if (item.estado == 1) "Pendiente" else "Atendida"
        holder.tvDireccion.text = item.direccion

        val color = if (item.estado == 1) Color.parseColor("#E53935") else Color.parseColor("#43A047")
        val drawable = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color)
        }
        holder.statusIndicator.background = drawable

        holder.itemView.setOnClickListener { onClick(item) }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Denuncia>() {
            override fun areItemsTheSame(oldItem: Denuncia, newItem: Denuncia): Boolean =
                oldItem.id == newItem.id && oldItem.id != 0

            override fun areContentsTheSame(oldItem: Denuncia, newItem: Denuncia): Boolean =
                oldItem == newItem
        }
    }
}
