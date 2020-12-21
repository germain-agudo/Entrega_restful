package com.example.entrega_restful

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptador_custom(var contexto: Context, items: ArrayList<Producto>,val clickListener: ClickListener):
RecyclerView.Adapter<Adaptador_custom.ViewHolder>(){

    lateinit var  items: ArrayList<Producto>

    init {
        this.items=items
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        val vista = LayoutInflater.from(contexto).inflate(R.layout.template_producto, parent,false)
        var viewHolder = ViewHolder(vista, clickListener)

        return viewHolder

    }

    override fun getItemCount(): Int {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        return this.items.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val item = items?.get(position)

        holder.id?.text =item.id.toString()
        holder.descripcion?.text =item.descripcion.toString()
        holder.categoria?.text =item.categoria.toString()
        holder.precio_unitario?.text =item.precio_unitario.toString()
        holder.existencia?.text =item.existencia.toString()
    }


    class ViewHolder (vista : View, listener: ClickListener): RecyclerView.ViewHolder(vista),View.OnClickListener{

        var vista = vista

        var id : TextView? = null
        var descripcion : TextView? = null
        var categoria : TextView? = null
        var precio_unitario : TextView? = null
        var existencia : TextView? = null

        var listener : ClickListener?=null
        init {
            id= vista.findViewById(R.id.tvMostrar_id)
            descripcion= vista.findViewById(R.id.tvMostrar_descripcion)
            categoria= vista.findViewById(R.id.tvMostrar_categoria)
            precio_unitario= vista.findViewById(R.id.tvMostrar_precio_unitario)
            existencia= vista.findViewById(R.id.tvMostrar_existencia)

            this.listener=listener
            vista.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            this.listener?.onClick(v!!,adapterPosition)
        }


    }



}