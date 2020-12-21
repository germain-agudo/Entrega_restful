package com.example.entrega_restful

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Show_producto : AppCompatActivity() {
    val url : String ="https://moviles-restful.000webhostapp.com/producto"
    var lista : RecyclerView?=null
    var layoutManager: RecyclerView.LayoutManager?=null
    var adaptador: Adaptador_custom?=null
    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
        setContentView(R.layout.show_producto)
        title = "Productos"
comprobarConexion()
    }
    fun comprobarConexion(){
        if (Network.hayRed(this)){
            GET_productos(url, this)
        }else{
            Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show()
        }
    }


    fun GET_productos(url: String, context: Context) {
        val queque = Volley.newRequestQueue(context)
           val url=url
        val JsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null, Response.Listener { response ->
                val json : JSONObject
                json =response
                convertir_datos(json)
                Log.i("LOG_TAG", "response is $response")
            },
            Response.ErrorListener { error ->
                error.printStackTrace()

            }
        )
        queque.add(JsonObjectRequest)
    }




    private fun convertir_datos(json : JSONObject){
        var lista_productos : ArrayList<Producto> = ArrayList()
        val json =json
        val  productos =json.getJSONArray("Productos")
        for (i in 0.. productos.length()-1){
            val id = productos.getJSONObject(i).getInt("id");
            val descripcion = productos.getJSONObject(i).getString("descripcion")
            val categoria = productos.getJSONObject(i).getString("categoria")
            val precio_unitario = productos.getJSONObject(i).getDouble("precio_unitario")
            val existencia = productos.getJSONObject(i).getInt("existencia")
            lista_productos.add(Producto(id,descripcion,categoria,precio_unitario,existencia))
            mostrar_productos(lista_productos)
        }
    }

        fun mostrar_productos(lista_productos: ArrayList<Producto>){
            var lista_productos = lista_productos
            lista = findViewById(R.id.lista_productos)
            lista?.setHasFixedSize(true)
            layoutManager= LinearLayoutManager(this)
            adaptador= Adaptador_custom(this, lista_productos!!,object : ClickListener{
                override fun onClick(vista: View, position: Int) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                    var id : Int = 0
                    id= lista_productos?.get(position)?.id!!
                    val intent : Intent = Intent(applicationContext, Update_producto::class.java)
                    intent.putExtra("id",id)

                    startActivity(intent)


                }
            })
            lista?.layoutManager=layoutManager
            lista?.adapter=adaptador
        }

    fun btnAddPressed(view: View) {
        startActivity(Intent(applicationContext,Create_producto::class.java))
    }


}
