package com.example.entrega_restful

class Producto(id: Int, descripcion : String, categoria: String, precio_unitario: Double, existencia:Int) {
    var id : Int = 0
    var descripcion : String=""
    var categoria : String =""
    var precio_unitario : Double=0.0
    var existencia : Int = 0


    init {
        this.id=id
        this.descripcion=descripcion
        this.categoria=categoria
        this.precio_unitario=precio_unitario
        this.existencia=existencia
    }


}