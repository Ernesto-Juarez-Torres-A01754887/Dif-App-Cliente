package com.itesm.difbueno

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

class SharedPreferencesManager(context: Context) {
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

    fun saveQRCodeBitmap(bitmap: Bitmap) {
        val editor = sharedPrefs.edit()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bitmapBytes = byteArrayOutputStream.toByteArray()
        val bitmapString = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
        editor.putString("qrCodeBitmap", bitmapString)
        editor.apply()
    }

    fun getQRCodeBitmap(): Bitmap? {
        val bitmapString = sharedPrefs.getString("qrCodeBitmap", null)
        return if (bitmapString != null) {
            val bitmapBytes = Base64.decode(bitmapString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.size)
        } else {
            null
        }
    }

    fun removeQRCodeBitmap() {
        val editor = sharedPrefs.edit()
        editor.remove("qrCodeBitmap") // Elimina la entrada del código QR
        editor.apply()
    }


}
