package com.ar.backgroundlocation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


/**
 * @Author: Abdul Rehman
 * @Date: 06/05/2024.
 */
@Preview
@Composable
fun App() {
    val context = LocalContext.current
    var message by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje") },
            placeholder = { Text("Que le quieres decir?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(4.dp))

        TextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Celular") },
            placeholder = { Text("Ejemplo: 50762295831...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                getCurrentLocation(context) { location ->
                    if (location != null) {
                        val formattedMessage = "$message\nMi ubicación: ${location.latitude}, ${location.longitude}"
                        sendWhatsAppMessage(context, phone, formattedMessage)
                    } else {
                        Toast.makeText(context, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ) {
            Text(text = "Enviar")
        }
    }
}