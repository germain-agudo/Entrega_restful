package com.example.entrega_restful

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Request.Method.PUT
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_create_producto.*
import kotlinx.android.synthetic.main.activity_update_producto.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ObjectOutputStream
import kotlin.concurrent.thread
import kotlin.math.sign

class Update_producto : AppCompatActivity() {
    var id : Int = 0
    val url : String ="https://app-348c5655-c4f9-4446-905e-2302eee44209.cleverapps.io/productos"
    var jsonObject  = JSONObject();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_producto)
        Toast.makeText(applicationContext, "Cargando...", Toast.LENGTH_SHORT).show()

        val intent : Intent = intent
        id = intent.getIntExtra("id",0)
        title = "Producto: $id"



        if (comprobarConexion()){
            GET_producto(url,id,this)
        }



    }




    private fun GET_producto(url: String, id:Int,context: Context) {
         val url=url+"/$id"

        val queque = Volley.newRequestQueue(context)
        val JsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
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
        val json =json
        val  productos =json.getJSONArray("Productos")
            val id = productos.getJSONObject(0).getInt("id");
            val descripcion = productos.getJSONObject(0).getString("descripcion")
            val categoria = productos.getJSONObject(0).getString("categoria")
            val precio_unitario = productos.getJSONObject(0).getDouble("precio_unitario")
            val existencia = productos.getJSONObject(0).getInt("existencia")
        etUpdateDescripcion.setText(descripcion.toString())
        etUpdateCategoria.setText(categoria.toString())
        etUpdatePrecio_unitario.setText(precio_unitario.toString())
        etUpdateExistencia.setText(existencia.toString())
    }



    /*


     */

    fun convertir_jsonobject():Boolean{
        val jsonObject_d :JSONObject;
        if (!etUpdateDescripcion.text.toString().trim().isEmpty() && !etUpdateCategoria.text.toString().trim().isEmpty()
            && !etUpdatePrecio_unitario.text.toString().trim().isEmpty() && !etUpdateExistencia.text.toString().isEmpty()
        ) {
            this.jsonObject.put("descripcion", etUpdateDescripcion.text.toString().trim());
            this.jsonObject.put( "categoria",  etUpdateCategoria.text.toString().trim());
            this.jsonObject.put( "precio_unitario",etUpdatePrecio_unitario.text.toString().trim().toDouble() );
            this. jsonObject.put( "existencia", Integer.parseInt(etUpdateExistencia.text.toString().trim()));
            return  true
        }else{

            Toast.makeText(applicationContext, "Campos Vacios ", Toast.LENGTH_SHORT).show()
            return false
        }

    }


    fun PUT_producto(url: String, id : Int,jsonObject: JSONObject,context: Context) {

        val jsonObject = jsonObject
        val url=url+"/"+id
        val queque = Volley.newRequestQueue(this)
        val JsonObjectRequest= JsonObjectRequest( Request.Method.PUT,url,jsonObject
            ,Response.Listener { response ->
                Log.i("LOG_TAG", "response is $response")

                Toast.makeText(applicationContext, "Producto actualizado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,Show_producto::class.java))

            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }
        )
        queque.add(JsonObjectRequest)

    }


    /*

     */
    fun btnUpdatePressed(){

        if(convertir_jsonobject()){
            if (comprobarConexion()){
                PUT_producto(url,id,jsonObject,this)
            }



       }else{

        }

    }



    fun comprobarConexion():Boolean{
        if (Network.hayRed(this)){
            return true
        }else{
            return true
            Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show()
        }
    }

     fun DELETE_producto(url: String, id:Int, context: Context) {
        /* val thread= Thread(Runnable {
             Thread.sleep(5000)
         })
         thread.start()*/



        val id =id
        val context=context
        val url =url


           val queque : RequestQueue
          queque = Volley.newRequestQueue(context)
        val JsonObjectRequest= JsonObjectRequest(Request.Method.DELETE,url+"/$id",null,
            Response.Listener {response ->

                Toast.makeText(applicationContext, "Producto eliminado", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,Show_producto::class.java))

                Log.i("LOG_TAG", "response is $response")
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                //Log.i("LOG_TAG", "response is "+ error.message)
                /*Log.i("jsonObjectRequest", "Error, Status Code " + error.networkResponse.statusCode);
//                Log.i("jsonObjectRequest", "URL: " + error.cause);
                Log.i("jsonObjectRequest", "Payload: " + error.localizedMessage);
                Log.i("jsonObjectRequest", "Net Response to String: " + error.networkResponse.toString());
                Log.i("jsonObjectRequest", "Error bytes: " +(error.networkResponse.data));*/

            }
        )


        queque.add(JsonObjectRequest)

    }


    fun btnDeletePressed(){

        if (Network.hayRed(this)){

            DELETE_producto(url,id,this)
            // finish()


        }else{
            Toast.makeText(this,"Error de conexion",Toast.LENGTH_SHORT).show()
        }




    }

   fun btnNewPressed(){
       startActivity(Intent(applicationContext,Create_producto::class.java))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_put_delete,menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            R.id.opcion_editar_producto->{
                //Log.e("TAG","opcion editar")
                btnUpdatePressed()
                true
            }

            R.id.opcion_eliminar_producto->{
                // Log.e("TAG","opcion eliminar")
                btnDeletePressed()
                true
            }

            R.id.opcion_agregar_producto->{
                //Log.e("TAG","opcion agregar")
                btnNewPressed()
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }




}
