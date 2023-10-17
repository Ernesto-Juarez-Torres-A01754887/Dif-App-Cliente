package com.itesm.difbueno

import android.app.Dialog
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

class Nocurp : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextEdad: EditText
    private lateinit var editTextGenero: EditText
    private lateinit var editTextCircunstancia: EditText
    private lateinit var checkBoxTerms: CheckBox
    private lateinit var termsTextView: TextView

    companion object {
        private const val PREFS_NAME = "MySharedPreferences"
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val NOMBRE = "nombre"
        private const val APELLIDO = "apellido"
        private const val EDAD = "edad"
        private const val GENERO = "genero"
        private const val CIRCUNSTANCIA = "circunstancia"
        private const val LAST_SESSION_SCREEN = "lastSessionScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nocurp)

        val termsLink = findViewById<TextView>(R.id.checkBoxTerms) // Cambia `termsLink` al ID real de tu elemento de interfaz de usuario
        termsLink.setOnClickListener {
            // Cuando se hace clic en el elemento de los términos y condiciones, muestra el diálogo
            showTermsDialog()
        }

        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextEdad = findViewById(R.id.editTextEdad)
        editTextGenero = findViewById(R.id.editTextGenero)
        editTextCircunstancia = findViewById(R.id.editTextCircunstancia)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        // Cargar la información guardada desde SharedPreferences y establecerla en los EditTexts
        editTextNombre.setText(sharedPreferences.getString(NOMBRE, ""))
        editTextApellido.setText(sharedPreferences.getString(APELLIDO, ""))
        editTextEdad.setText(sharedPreferences.getString(EDAD, ""))
        editTextGenero.setText(sharedPreferences.getString(GENERO, ""))
        editTextCircunstancia.setText(sharedPreferences.getString(CIRCUNSTANCIA, ""))

        checkBoxTerms.setOnClickListener {
            // Si se marca o desmarca el CheckBox
            val isChecked = checkBoxTerms.isChecked
            // Puedes realizar acciones basadas en el estado del CheckBox aquí
        }

        val buttonRegistro = findViewById<Button>(R.id.buttonRegistro)
        buttonRegistro.setOnClickListener {
            if (areFieldsValid()) {
                // Guarda la información ingresada en SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString(NOMBRE, editTextNombre.text.toString())
                editor.putString(APELLIDO, editTextApellido.text.toString())
                editor.putString(EDAD, editTextEdad.text.toString())
                editor.putString(GENERO, editTextGenero.text.toString())
                editor.putString(CIRCUNSTANCIA, editTextCircunstancia.text.toString())
                editor.putString(LAST_SESSION_SCREEN, "Nocurp") // Guarda el nombre de la pantalla
                editor.apply()

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
        dialog.show()
    }

    fun logout(view: View) {
        // Accede a SharedPreferences para borrar cualquier dato de sesión o preferencias que desees
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(NOMBRE)
        editor.remove(APELLIDO)
        editor.remove(EDAD)
        editor.remove(GENERO)
        editor.remove(CIRCUNSTANCIA)
        editor.putBoolean(IS_LOGGED_IN, false)

        // Llama a la función para eliminar el código QR
        val sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.removeQRCodeBitmap()


        editor.apply()

        // Limpia los campos de nombre, apellido, etc., si es necesario
        editTextNombre.text.clear()
        editTextApellido.text.clear()
        editTextEdad.text.clear()
        editTextGenero.text.clear()
        editTextCircunstancia.text.clear()

        // Muestra un mensaje de cierre de sesión
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

        // Luego, puedes redirigir al usuario a la pantalla de inicio de sesión, por ejemplo, MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
