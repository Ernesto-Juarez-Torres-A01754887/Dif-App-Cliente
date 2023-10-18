package com.itesm.difbueno

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var editTextApellidoMaterno: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextFechaNacimiento: EditText
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var termsTextView: TextView
    private var termsAccepted = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nocurp)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextApellidoMaterno = findViewById(R.id.editTextApellidoMaterno)
        editTextGenero = findViewById(R.id.editTextSexo)
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        // Recuperar datos guardados en SharedPreferences
        val nombre = sharedPreferences.getString("nombre", "")
        val apellido = sharedPreferences.getString("apellido", "")
        val apellidoMaterno = sharedPreferences.getString("apellidoMaterno", "")
        val genero = sharedPreferences.getString("genero", "")
        val fechaNacimiento = sharedPreferences.getString("fechaNacimiento", "")
        val termsAccepted = sharedPreferences.getBoolean("termsAccepted", false)

        // Establecer los valores en los EditText
        editTextNombre.setText(nombre)
        editTextApellido.setText(apellido)
        editTextApellidoMaterno.setText(apellidoMaterno)
        editTextGenero.setText(genero)
        editTextFechaNacimiento.setText(fechaNacimiento)
        checkBoxTerms.isChecked = termsAccepted

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
            // Guardar el estado del CheckBox en SharedPreferences
            sharedPreferences.edit().putBoolean("termsAccepted", isChecked).apply()
        }

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            if (areFieldsValid()) {
                // Realiza acciones de registro aquí
                // ...

                // Guardar los datos en SharedPreferences
                sharedPreferences.edit()
                    .putString("nombre", editTextNombre.text.toString())
                    .putString("apellido", editTextApellido.text.toString())
                    .putString("apellidoMaterno", editTextApellidoMaterno.text.toString())
                    .putString("genero", editTextGenero.text.toString())
                    .putString("fechaNacimiento", editTextFechaNacimiento.text.toString())
                    .apply()

                // Muestra un mensaje de registro exitoso
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()

                // Luego, puedes redirigir al usuario a la actividad CodigoQR
                val intent = Intent(this, CodigoQR::class.java)
                startActivity(intent)
            } else {
                // Muestra un mensaje de error si los campos no están llenos
                Toast.makeText(this, "Por favor, complete todos los campos y acepte los términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }
        // Agregar un botón de cierre de sesión y su OnClickListener
        val buttonCerrarSesion = findViewById<Button>(R.id.buttonLogout)
        buttonCerrarSesion.setOnClickListener {
            // Borra los datos guardados en SharedPreferences
            sharedPreferences.edit().clear().apply()
            // Establece el estado de inicio de sesión en falso
            sharedPreferences.edit().putBoolean("isLoggedIn", false).apply()
            // Elimina el código QR generado

            // Redirige al usuario a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual
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
