package com.itesm.difbueno.Model

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itesm.difbueno.View.MenuDifActivity
import com.itesm.difbueno.R


class EncuestaSatisfaccion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        floatingActionButton.setOnClickListener {
            // Navega a la actividad menu_dif
            val intent = Intent(this@EncuestaSatisfaccion, MenuDifActivity::class.java)
            startActivity(intent)
        }

        // Configurar el clic del ImageButton
        val encuestaButton = findViewById<ImageButton>(R.id.encuesta) // Asegúrate de que el ID sea el correcto
        encuestaButton.setOnClickListener {
            // Abrir el enlace en un navegador web
            val url = "https://forms.gle/s5EL8da9SfEAxRto7"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
