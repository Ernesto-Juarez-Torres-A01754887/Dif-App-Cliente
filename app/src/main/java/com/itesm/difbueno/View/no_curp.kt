package com.itesm.difbueno.View

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
import com.itesm.difbueno.R
import com.itesm.difbueno.ViewModel.generar_qr

class no_curp : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextApellidoMaterno: EditText
    //private lateinit var editTextEdad: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextFechaNacimiento: EditText
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var termsTextView: TextView
    private var termsAccepted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nocurp)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextApellidoMaterno = findViewById(R.id.editTextApellidoMaterno)
        editTextGenero = findViewById(R.id.editTextSexo)
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        checkBoxTerms.setOnClickListener {
            // Si se marca o desmarca el CheckBox
            val isChecked = checkBoxTerms.isChecked
            // Puedes realizar acciones basadas en el estado del CheckBox aquí
        }

        val textViewTerms = findViewById<TextView>(R.id.checkBoxTerms)
        textViewTerms.setOnClickListener {
            showTermsDialog()
        }

        checkBoxTerms.setOnCheckedChangeListener { _, isChecked ->
            termsAccepted = isChecked
        }

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            if (areFieldsValid()) {
                // Realiza acciones de registro aquí
                // ...

                // Muestra un mensaje de registro exitoso
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                // Luego, puedes redirigir al usuario a la actividad generar_qr
                val intent = Intent(this, generar_qr::class.java)
                intent.putExtra("nombre", editTextNombre.text.toString())
                intent.putExtra("apellido", editTextApellido.text.toString())
                intent.putExtra("apellidoMaterno", editTextApellidoMaterno.text.toString())
                intent.putExtra("genero", editTextGenero.text.toString())
                intent.putExtra("fechaNacimiento", editTextFechaNacimiento.text.toString())
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
        val apellidoMaterno = editTextApellidoMaterno.text.toString()
        val genero = editTextGenero.text.toString()
        val fechaNacimiento = editTextFechaNacimiento.text.toString()

        // Verifica si todos los campos están llenos y si la casilla de verificación está marcada
        return nombre.isNotEmpty() && apellido.isNotEmpty() && apellidoMaterno.isNotEmpty() &&
                genero.isNotEmpty() && fechaNacimiento.isNotEmpty() && checkBoxTerms.isChecked
    }

    private fun showTermsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_terminos_condiciones) // Utiliza tu diseño de términos y condiciones
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeButton = dialog.findViewById<Button>(R.id.buttonClose)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
