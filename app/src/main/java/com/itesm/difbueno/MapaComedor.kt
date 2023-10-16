package com.itesm.difbueno

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MapaComedor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa_comedores)

        val mapa = findViewById<ImageButton>(R.id.mapa)
        // Establece el enlace a la URL cuando se hace clic en el botón
        mapa.setOnClickListener {
            val url = "https://www.google.com/maps/d/viewer?mid=16p4XUJ3OiJqezpHleTAKGHy4Ti_8rYc&ll=19.575418665693352%2C-99.24592264703536&z=13"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton2)

        floatingActionButton.setOnClickListener {
            // Navega a la actividad menu_dif
            val intent = Intent(this@MapaComedor, MenuDifActivity::class.java)
            startActivity(intent)
        }
    }
}
