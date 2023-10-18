package com.itesm.difbueno.View

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.itesm.difbueno.R
import com.itesm.difbueno.Model.SharedPreferencesManager
import com.itesm.difbueno.ViewModel.generar_qr

class inicia_sesion_curp : AppCompatActivity() {

    private lateinit var editTextCURP: EditText
    private lateinit var checkBoxTerms: CheckBox


    companion object {
        private const val PREFS_NAME = "MySharedPreferences"
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val SAVED_CURP = "savedCURP"
        private const val LAST_SESSION_SCREEN = "lastSessionScreen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        editTextCURP = findViewById(R.id.editTextCURP)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        // Cargar el CURP guardado desde SharedPreferences y establecerlo en el EditText
        val savedCURP = sharedPreferences.getString(SAVED_CURP, "")
        editTextCURP.setText(savedCURP)

        // Agregar un TextWatcher para convertir automáticamente el texto a mayúsculas
        editTextCURP.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Convierte el texto a mayúsculas después de cada cambio
                editTextCURP.removeTextChangedListener(this)
                val curp = s.toString().toUpperCase()
                editTextCURP.setText(curp)
                editTextCURP.setSelection(curp.length) // Mantiene el cursor al final del texto
                editTextCURP.addTextChangedListener(this)

            }
        })

        // Establecer un OnClickListener en el CheckBox
        checkBoxTerms.setOnClickListener {
            // Si se marca o desmarca el CheckBox
            val isChecked = checkBoxTerms.isChecked
            // Puedes realizar acciones basadas en el estado del CheckBox aquí
            showTermsDialog()
        }

        val buttonNoCURP = findViewById<Button>(R.id.buttonNoCURP)
        buttonNoCURP.setOnClickListener {
            // Guarda la información que indica que la última pantalla fue "no_curp"
            val editor = sharedPreferences.edit()
            editor.putString(LAST_SESSION_SCREEN, "no_curp")
            editor.apply()
            // Inicia la actividad NoCURPActivity
            val intent = Intent(this, no_curp::class.java)
            startActivity(intent)
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            if (checkBoxTerms.isChecked) {
                val curp = editTextCURP.text.toString()
                if (curp.isNotEmpty() && isValidCURP(curp)) {
                    // Guarda el CURP en SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString(SAVED_CURP, curp)
                    editor.putString(LAST_SESSION_SCREEN, "inicia_sesion_curp") // Guarda el nombre de la pantalla
                    editor.apply()

                    // Guarda el estado de inicio de sesión
                    editor.putBoolean(IS_LOGGED_IN, true) // Establece el estado de inicio de sesión en verdadero
                    editor.apply()

                    // Inicia la actividad generar_qr
                    val intent = Intent(this, generar_qr::class.java)
                    // Agregar el CURP como dato extra al intent
                    intent.putExtra("curp", curp)
                    startActivity(intent)
                    // Cierra la actividad inicia_sesion_curp para bloquear el regreso
                    finish()
                } else {
                    Toast.makeText(this, "La CURP ingresada no es válida", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Si no se ha aceptado los términos y condiciones, muestra los términos en un cuadro de diálogo
                Toast.makeText(this, "Acepta los términos y condiciones", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun openLink(view: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gob.mx/curp/"))
        startActivity(intent)
    }

    private fun isValidCURP(curp: String): Boolean {
        // Expresión Regular para CURP Mexicana estándar
        val regex1 = Regex("^[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z][0-9]$")
        // Expresión Regular alternativa (permite dos dígitos al final)
        val regex2 = Regex("^[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[0-9]{2}$")
        // EXPRESIÓN Regular alternativa (permite dos letras al final)
        val regex3 = Regex("^[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z]{2}$")
        return regex1.matches(curp) || regex2.matches(curp) || regex3.matches(curp)
    }

    private fun showTermsDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.activity_terminos_condiciones) // Establece el diseño del cuadro de diálogo
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Fondo transparente

        val closeButton = dialog.findViewById<Button>(R.id.buttonClose)
        closeButton.setOnClickListener {
            dialog.dismiss() // Cierra el cuadro de diálogo cuando se hace clic en el botón "Cerrar"
        }
        dialog.show() // Muestra el cuadro de diálogo
    }
    fun logout(view: View) {
        // Accede a SharedPreferences para borrar el CURP y cambiar el estado de inicio de sesión a falso
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(SAVED_CURP)
        editor.putBoolean(IS_LOGGED_IN, false)
        editor.apply()

        // Limpia el campo de CURP
        editTextCURP.text.clear()

        // Elimina el código QR almacenado
        val sharedPreferencesManager = SharedPreferencesManager(this)
        sharedPreferencesManager.removeQRCodeBitmap()

        // Muestra un mensaje de cerrar sesión
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }

}