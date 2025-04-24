package com.example.gamesshare.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.Calendar

fun formatVoucherCarLess(currentDate: Date = Date()): String {
    val format = SimpleDateFormat("dd/MM/yyyy - h:mm a", Locale.getDefault())
    val newDate = format.format(currentDate)
    return newDate.lowercase()
        .replace("a. m.", "AM")
        .replace("p. m.","PM")
        .replace("a.m.", "AM")
        .replace("p.m.","PM")
        .uppercase()
}





@SuppressLint("NewApi")
fun replaceDate(dateString: String): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - h:mm a", Locale.ENGLISH)

    // Convertir el string a LocalDateTime
    val currentDate = LocalDateTime.parse(dateString, formatter)

    // Obtener la fecha y hora actual
    val now = LocalDateTime.now()

    // Comparar las fechas
    return when {
        isSameDay(now, currentDate) -> {
            // Si es el mismo día, devolver "Hoy - hora"
            DateTimeFormatter.ofPattern("h:mm a").format(currentDate).replace(" ","")
        }
        isYesterday(now, currentDate) -> {
            // Si es el día anterior, devolver "Ayer - hora"
            "Ayer - ${DateTimeFormatter.ofPattern("h:mm a").format(currentDate)}".replace(" ","")
        }
        else -> {
            // Si no es ni hoy ni ayer, devolver la fecha completa
             DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("ES")).format(currentDate)
        }
    }
}

// Función para verificar si dos fechas son del mismo día
@RequiresApi(Build.VERSION_CODES.O)
fun isSameDay(now: LocalDateTime, eventDate: LocalDateTime): Boolean {
    return now.toLocalDate() == eventDate.toLocalDate()
}

// Función para verificar si una fecha es del día anterior
@RequiresApi(Build.VERSION_CODES.O)
fun isYesterday(now: LocalDateTime, eventDate: LocalDateTime): Boolean {
    val yesterday = now.minus(1, ChronoUnit.DAYS)
    return yesterday.toLocalDate() == eventDate.toLocalDate()
}
