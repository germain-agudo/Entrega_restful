package com.example.entrega_restful

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "REQUEST_METHOD"

    }

    fun btnMostrar(view: View) {
        startActivity(Intent(applicationContext,Show_producto::class.java))
        Toast.makeText(this,"Espere pofavor, esto puede tomar algunos minutos", Toast.LENGTH_LONG).show()
    }


}
