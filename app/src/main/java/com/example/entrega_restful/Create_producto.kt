package com.example.entrega_restful

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_producto.*
import org.json.JSONObject

class Create_producto : AppCompatActivity() {
    val url : String ="https://moviles-restful.000webhostapp.com/producto"
 var jsonObject = JSONObject();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_producto)
        title = "Agregar producto"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    //Log.e("TAG", etAddCategoria.text.toString().trim() )

    }

    fun convertir_jsonobject():Boolean{
        //val jsonObject= JSONObject();
        if (!etAddDescripcion.text.toString().trim().isEmpty() && !etAddCategoria.text.toString().trim().isEmpty()
            && !etAddPrecio_unitario.text.toString().trim().isEmpty() && !etAddExistencia.text.toString().isEmpty()
        ) {
        this.jsonObject.put("descripcion", etAddDescripcion.text.toString().trim());
        this.jsonObject.put( "categoria",  etAddCategoria.text.toString().trim());
        this.jsonObject.put( "precio_unitario",etAddPrecio_unitario.text.toString().trim().toDouble() );
        this. jsonObject.put( "existencia", Integer.parseInt(etAddExistencia.text.toString().trim()));
            return  true
        }else{

            Toast.makeText(applicationContext, "Campos Vacios ", Toast.LENGTH_SHORT).show()
            return false
        }

    }


    fun POST_producto(url: String, jsonObject: JSONObject,context: Context) {
        val jsonObject = jsonObject
        val url=url
        val queque = Volley.newRequestQueue(context)
        val JsonObjectRequest= JsonObjectRequest(Request.Method.POST,url,jsonObject
            ,Response.Listener { response ->
                Log.i("LOG_TAG", "response is $response")
                Toast.makeText(applicationContext, "Producto agregado", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        queque.add(JsonObjectRequest)
    }


    fun btnCreatePressed(view: View) {
            if (convertir_jsonobject()){


                    if (Network.hayRed(this)){
                        POST_producto(url,jsonObject,this)

                        startActivity(Intent(this,Show_producto::class.java))
                    }else{
                        Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show()
                    }



            }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                onBackPressed()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

}
