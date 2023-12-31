package com.itesm.difbueno.ViewModel

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itesm.difbueno.R
import com.itesm.difbueno.Model.SharedPreferencesManager
import com.itesm.difbueno.View.menu_dif_app

class generar_qr : AppCompatActivity() {
    private var qrCodeBitmap: Bitmap? = null
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        sharedPreferencesManager = SharedPreferencesManager(this)

        floatingActionButton.setOnClickListener {
            // Navega a la actividad menu_dif
            val intent = Intent(this@generar_qr, menu_dif_app::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Intenta cargar el código QR desde SharedPreferences
        qrCodeBitmap = sharedPreferencesManager.getQRCodeBitmap()

        // Verificar si hay un código QR almacenado en qrCodeBitmap
        qrCodeBitmap?.let {
            val imageViewQRCode = findViewById<ImageView>(R.id.qrCodeImageView)
            imageViewQRCode.setImageBitmap(it)
        } ?: run {
            // Si no hay código QR en SharedPreferences, genera uno
            val curp = intent.getStringExtra("curp")
            val apellido = intent.getStringExtra("apellido")
            val apellidoMaterno = intent.getStringExtra("apellidoMaterno")
            val nombre = intent.getStringExtra("nombre")
            val sexo = intent.getStringExtra("genero")
            val fechaNacimiento = intent.getStringExtra("fechaNacimiento")

            if (curp != null) {
                val data = "$curp||desconocido|desconocido|desconocido|desconocido|desconocido|desconocido|desconocido|"
                qrCodeBitmap = generateQRCode(data)
            } else if (apellido != null && apellidoMaterno != null && nombre != null &&
                sexo != null && fechaNacimiento != null) {
                val data = "desconocido||$apellido|$apellidoMaterno|$nombre|$sexo|$fechaNacimiento|desconocido|desconocido|"
                qrCodeBitmap = generateQRCode(data)
            }

            // Guarda el código QR en SharedPreferences
            qrCodeBitmap?.let {
                sharedPreferencesManager.saveQRCodeBitmap(it)
                val imageViewQRCode = findViewById<ImageView>(R.id.qrCodeImageView)
                imageViewQRCode.setImageBitmap(it)
            } ?: run {
                Toast.makeText(
                    this,
                    "No se encontraron datos válidos para generar el código QR",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun generateQRCode(data: String): Bitmap {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix = multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 1000, 1000)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }

            return bmp
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al generar el código QR: ${e.message}", Toast.LENGTH_SHORT).show()
            throw RuntimeException("Error al generar el código QR", e)
        }
    }

}
