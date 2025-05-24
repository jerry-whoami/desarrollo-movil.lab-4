package com.ar.backgroundlocation

import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import androidx.core.net.toUri
import java.net.URLEncoder

fun getCurrentLocation(context: Context, onResult: (Location?) -> Unit) {
    val gpsClient = GPSLocationClient()

    gpsClient.setLocationUpdatesCallBack(object : LocationUpdatesCallBack {
        override fun onLocationUpdate(location: Location) {
            onResult(location)
            gpsClient.setLocationUpdatesCallBack(null)
        }

        override fun locationException(message: String) {
            Toast.makeText(context, "Error al buscar la ubicación: $message", Toast.LENGTH_SHORT).show()
            onResult(null)
            gpsClient.setLocationUpdatesCallBack(null)
        }
    })

    gpsClient.getLocationUpdates(context)
}

fun sendWhatsAppMessage(context: Context, phone: String, message: String) {
    try {
        val encodedMessage = URLEncoder.encode(message, "UTF-8")
        val uri = String.format(
            "https://api.whatsapp.com/send?phone=%s&text=%s",
            phone.trim(),
            encodedMessage
        )
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = uri.toUri()
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
    }
}
