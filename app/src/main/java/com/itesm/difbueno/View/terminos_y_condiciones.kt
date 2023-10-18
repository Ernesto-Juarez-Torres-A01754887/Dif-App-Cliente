package com.itesm.difbueno.View

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.itesm.difbueno.R

class terminos_y_condiciones: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_terminos_condiciones, container, false)

        val closeButton = view.findViewById<Button>(R.id.buttonClose)
        closeButton.setOnClickListener {
            // Cierra el cuadro de diálogo cuando se hace clic en el botón "Cerrar"
            dismiss()
        }

        return view
    }
}
