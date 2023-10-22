package com.itesm.difbueno.View

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itesm.difbueno.Model.SharedPreferencesManager
import com.itesm.difbueno.R
import com.itesm.difbueno.ViewModel.generar_qr

class no_curp : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextApellidoMaterno: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextFechaNacimiento: EditText
    private lateinit var checkBoxTerms: CheckBox

    // Clase anidada SharedPreferencesManager
    class SharedPreferencesManager(context: Context) {
        private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        private val editor = preferences.edit()

        companion object {
            private const val PREFS_NAME = "MySharedPreferences"
            private const val NOMBRE = "nombre"
            private const val APELLIDO = "apellido"
            private const val APELLIDO_MATERNO = "apellidoMaterno"
            private const val GENERO = "genero"
            private const val FECHA_NACIMIENTO = "fechaNacimiento"
            private const val TERMS_ACCEPTED = "termsAccepted"
        }

        fun getNombre(): String {
            return preferences.getString(NOMBRE, "") ?: ""
        }

        fun saveNombre(nombre: String) {
            editor.putString(NOMBRE, nombre)
            editor.apply()
        }

        fun getApellido(): String {
            return preferences.getString(APELLIDO, "") ?: ""
        }

        fun saveApellido(apellido: String) {
            editor.putString(APELLIDO, apellido)
            editor.apply()
        }

        fun getApellidoMaterno(): String {
            return preferences.getString(APELLIDO_MATERNO, "") ?: ""
        }

        fun saveApellidoMaterno(apellidoMaterno: String) {
            editor.putString(APELLIDO_MATERNO, apellidoMaterno)
            editor.apply()
        }

        fun getGenero(): String {
            return preferences.getString(GENERO, "") ?: ""
        }

        fun saveGenero(genero: String) {
            editor.putString(GENERO, genero)
            editor.apply()
        }

        fun getFechaNacimiento(): String {
            return preferences.getString(FECHA_NACIMIENTO, "") ?: ""
        }

        fun saveFechaNacimiento(fechaNacimiento: String) {
            editor.putString(FECHA_NACIMIENTO, fechaNacimiento)
            editor.apply()
        }

        fun getTermsAccepted(): Boolean {
            return preferences.getBoolean(TERMS_ACCEPTED, false)
        }

        fun saveTermsAccepted(termsAccepted: Boolean) {
            editor.putBoolean(TERMS_ACCEPTED, termsAccepted)
            editor.apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nocurp)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextApellidoMaterno = findViewById(R.id.editTextApellidoMaterno)
        editTextGenero = findViewById(R.id.editTextSexo)
        editTextFechaNacimiento = findViewById(R.id.editTextFechaNacimiento)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        // Crear una instancia de SharedPreferencesManager
        val sharedPreferencesManager = SharedPreferencesManager(this)

        // Cargar los datos guardados desde SharedPreferences y establecerlos en los campos EditText
        editTextNombre.setText(sharedPreferencesManager.getNombre())
        editTextApellido.setText(sharedPreferencesManager.getApellido())
        editTextApellidoMaterno.setText(sharedPreferencesManager.getApellidoMaterno())
        editTextGenero.setText(sharedPreferencesManager.getGenero())
        editTextFechaNacimiento.setText(sharedPreferencesManager.getFechaNacimiento())
        checkBoxTerms.isChecked = sharedPreferencesManager.getTermsAccepted()

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            if (areFieldsValid()) {
                // Guardar todos los campos ingresados en SharedPreferences
                sharedPreferencesManager.saveNombre(editTextNombre.text.toString())
                sharedPreferencesManager.saveApellido(editTextApellido.text.toString())
                sharedPreferencesManager.saveApellidoMaterno(editTextApellidoMaterno.text.toString())
                sharedPreferencesManager.saveGenero(editTextGenero.text.toString())
                sharedPreferencesManager.saveFechaNacimiento(editTextFechaNacimiento.text.toString())

                // Realizar acciones de registro aquí
                // ...

                // Mostrar un mensaje de registro exitoso
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
                // Mostrar un mensaje de error si los campos no están llenos
                Toast.makeText(this, "Por favor, complete todos los campos y acepte los términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }

        val textViewTerms = findViewById<TextView>(R.id.checkBoxTerms)
        textViewTerms.setOnClickListener {
            showTermsDialog()
        }
    }

    private fun areFieldsValid(): Boolean {
        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val apellidoMaterno = editTextApellidoMaterno.text.toString()
        val genero = editTextGenero.text.toString()
        val fechaNacimiento = editTextFechaNacimiento.text.toString()

        // Verificar si todos los campos están llenos y si la casilla de verificación está marcada
        return nombre.isNotEmpty() && apellido.isNotEmpty() && apellidoMaterno.isNotEmpty() &&
                genero.isNotEmpty() && fechaNacimiento.isNotEmpty() && checkBoxTerms.isChecked
    }

    private fun showTermsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_terminos_condiciones)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeButton = dialog.findViewById<Button>(R.id.buttonClose)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
    fun logout(view: View) {
        // Acceder a SharedPreferences para borrar los datos guardados
        val sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.saveNombre("")
        sharedPreferencesManager.saveApellido("")
        sharedPreferencesManager.saveApellidoMaterno("")
        sharedPreferencesManager.saveGenero("")
        sharedPreferencesManager.saveFechaNacimiento("")

        // Eliminar el QR generado (si es necesario)
        val dharedPreferencesManager = com.itesm.difbueno.Model.SharedPreferencesManager(this)
        dharedPreferencesManager.removeQRCodeBitmap()

        // Redirigir al usuario a la actividad 'inicia_sesion_curp'
        val intent = Intent(this, inicia_sesion_curp::class.java)
        startActivity(intent)

        // Finalizar la actividad actual (no_curp)
        finish()
    }
}
