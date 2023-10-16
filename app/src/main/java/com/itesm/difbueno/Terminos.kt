package com.itesm.difbueno

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Terminos: DialogFragment() {

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
