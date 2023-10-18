package com.itesm.difbueno.Model

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.itesm.difbueno.View.MenuDifActivity
import com.itesm.difbueno.R

class DerechosObligaciones : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_derechos_obligaciones)

        // Puedes realizar cualquier configuración adicional aquí si es necesario
        val buttonRegresarMenu = findViewById<Button>(R.id.buttonRegresarMenu)

        buttonRegresarMenu.setOnClickListener {
            val intent = Intent(this, MenuDifActivity::class.java)
            startActivity(intent)
            finish() // Esto cierra la actividad actual y vuelve al menú
        }

    }
}