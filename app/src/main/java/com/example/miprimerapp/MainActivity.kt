package com.example.miprimerapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit  var firebase:FirebaseFirestore
    private lateinit var txt_run: EditText
    private lateinit var txt_nombres: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txt_run=findViewById(R.id.txt_formRegistro_run)
        txt_nombres=findViewById(R.id.txt_formRegistro_nombres)
        firebase= FirebaseFirestore.getInstance() //Obtener la conexión e Instancia
    }
    fun volverMenu(view: View){
        var intentMenuPrincipal= Intent(this, MenuPrincipal::class.java)
        startActivity(intentMenuPrincipal)
    }
    fun mostrarAlerta(titulo:String, mensaje:String){
        var alerta= AlertDialog.Builder(this)
        alerta.setTitle(titulo)
        alerta.setMessage(mensaje)
        alerta.setPositiveButton("Aceptar",null)
        alerta.setNegativeButton("Negativo",null)
        alerta.setNeutralButton("Neutral",null)

        alerta.show()

    }
    fun procesarRegistro(view: View){
        var runPersonas:String = txt_run.text.toString()
        var nombresPersonas: String= txt_nombres.text.toString()
        //Acá agregamos la estructura del documento que vamos agregar a la collection
        var registroUsuario=hashMapOf(
            "run" to runPersonas,
            "nombres" to nombresPersonas
        )
        firebase.collection("usuarios")
            .add(registroUsuario)
            .addOnSuccessListener {
                mostrarAlerta("Correcto","Registro Insertado Correctamente!")
            }
            .addOnFailureListener {
                mostrarAlerta("Error","Ocurrió un error.")
            }
    }
}