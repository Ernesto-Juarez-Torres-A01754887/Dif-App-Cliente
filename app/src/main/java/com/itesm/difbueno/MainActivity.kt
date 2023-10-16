package com.itesm.difbueno

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editTextCURP: EditText
    private lateinit var checkBoxTerms: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextCURP = findViewById(R.id.editTextCURP)
        checkBoxTerms = findViewById(R.id.checkBoxTerms)

        val textViewTerms = findViewById<TextView>(R.id.checkBoxTerms)
        textViewTerms.setOnClickListener {
            showTermsDialog()
        }

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
            //showTermsDialog()
        }

        val buttonNoCURP = findViewById<Button>(R.id.buttonNoCURP)
        buttonNoCURP.setOnClickListener {
            // Inicia la actividad NoCURPActivity
            val intent = Intent(this, Nocurp::class.java)
            startActivity(intent)
        }

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            if (checkBoxTerms.isChecked) {
                val curp = editTextCURP.text.toString()
                if (curp.isNotEmpty() && isValidCURP(curp)) {
                    val intent = Intent(this, CodigoQR::class.java)
                    // Agregar el CURP como dato extra al intent
                    intent.putExtra("curp", curp)
                    // La CURP es válida, aquí puedes iniciar sesión con el CURP proporcionado.
                    Toast.makeText(this, "Iniciando sesión con CURP: $curp", Toast.LENGTH_SHORT).show()
                    startActivity(intent) // Inicia la actividad CodigoQR
                } else {
                    Toast.makeText(this, "La CURP ingresada no es válida", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Si no se ha aceptado los términos y condiciones, muestra los términos en un cuadro de diálogo
                Toast.makeText(this, "Acepta los terminos y condiciones", Toast.LENGTH_SHORT).show()
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
}