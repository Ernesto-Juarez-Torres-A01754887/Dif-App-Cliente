package com.itesm.difbueno

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
            // Por ejemplo, puedes iniciar una nueva actividad o realizar alguna acción.
        }

        buttonSalir.setOnClickListener {
            // Lógica para manejar el clic en el botón "Mapa"
            // Por ejemplo, puedes iniciar una nueva actividad o realizar alguna acción.
        }
    }
}
