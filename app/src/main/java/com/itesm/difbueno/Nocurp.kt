package com.itesm.difbueno

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Nocurp : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextCircunstancia: EditText
    private lateinit var checkBoxTerms: CheckBox
    private var avanzoMasAlla = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nocurp)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextGenero = findViewById(R.id.editTextGenero)
        editTextCircunstancia = findViewById(R.id.editTextCircunstancia)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)


        checkBoxTerms.setOnClickListener {
            // Si se marca o desmarca el CheckBox
            val isChecked = checkBoxTerms.isChecked
            // Puedes realizar acciones basadas en el estado del CheckBox aquí
            showTermsDialog()
            avanzoMasAlla = true
        }

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            if (areFieldsValid()) {
                // Realiza acciones de registro aquí
                // ...

                // Muestra un mensaje de registro exitoso
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                // Luego, puedes redirigir al usuario a la actividad CodigoQR
                val intent = Intent(this, CodigoQR::class.java)
                intent.putExtra("nombre", editTextNombre.text.toString())
                intent.putExtra("apellido", editTextApellido.text.toString())
                intent.putExtra("edad", editTextEdad.text.toString())
                intent.putExtra("genero", editTextGenero.text.toString())
                intent.putExtra("circunstancia", editTextCircunstancia.text.toString())
                startActivity(intent)
            } else {
                // Muestra un mensaje de error si los campos no están llenos
                Toast.makeText(this, "Por favor, complete todos los campos y acepte los términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun areFieldsValid(): Boolean {
        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val edad = editTextEdad.text.toString()
        val genero = editTextGenero.text.toString()
        val circunstancia = editTextCircunstancia.text.toString()

        // Verifica si todos los campos están llenos y si la casilla de verificación está marcada
        return nombre.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty() &&
                genero.isNotEmpty() && circunstancia.isNotEmpty() && checkBoxTerms.isChecked
    }

    private fun showTermsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_terminos_condiciones) // Utiliza tu diseño de términos y condiciones
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeButton = dialog.findViewById<Button>(R.id.buttonClose)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}
