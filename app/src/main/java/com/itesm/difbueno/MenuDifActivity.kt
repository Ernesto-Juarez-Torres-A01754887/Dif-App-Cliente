package com.itesm.difbueno

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MenuDifActivity : AppCompatActivity() {
    // Declarar una constante para manejar el resultado del código QR
    companion object {
        const val QR_CODE_REQUEST = 1
    }

    private var qrCodeBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_dif)

        val buttonQR = findViewById<Button>(R.id.buttonQR)
        val buttonMapa = findViewById<Button>(R.id.buttonMapa)
        val buttonEncuesta = findViewById<Button>(R.id.buttonEncuesta)
        val buttonDerechosObligaciones = findViewById<Button>(R.id.buttonDerechosObligaciones)
        val buttonSalir = findViewById<Button>(R.id.buttonSalir)


        buttonQR.setOnClickListener {
                val intent = Intent(this, CodigoQR::class.java)
                startActivity(intent)
        }

        buttonMapa.setOnClickListener {
            // Lógica para manejar el clic en el botón "Mapa"
            val intent = Intent(this, MapaComedor::class.java)
            startActivity(intent)
        }

        buttonEncuesta.setOnClickListener {
            // Configura la intención para iniciar EncuestaActivity
            val intent = Intent(this, EncuestaSatisfaccion::class.java)
            startActivity(intent)
        }

        buttonDerechosObligaciones.setOnClickListener {
            // Lógica para manejar el clic en el botón "Mapa"
            val intent = Intent(this, DerechosObligaciones::class.java)
            startActivity(intent)
        }

        //buttonSalir.setOnClickListener {
            // Lógica para manejar el clic en el botón "Salir"
            //val intent = Intent(Intent.ACTION_MAIN)
            //intent.addCategory(Intent.CATEGORY_HOME)
            //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //startActivity(intent)
        //}
        buttonSalir.setOnClickListener {
            //finish() // Cierra la actividad actual
            //moveTaskToBack(true) // Mueve la aplicación a segundo plano
            //android.os.Process.killProcess(android.os.Process.myPid()) // Mata el proceso de la aplicación
            //System.exit(1) // Finaliza todos los hilos de la aplicación
        }

    }
}
