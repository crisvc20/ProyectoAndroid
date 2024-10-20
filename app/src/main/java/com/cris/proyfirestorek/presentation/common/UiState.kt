package com.cris.proyfirestorek.presentation.common

import androidx.core.app.NotificationCompat.MessagingStyle.Message

//clase sellada y generica
sealed class UiState<T>{
    //data class Loading<T>: UiState<T>()
    data class Success<T>(val data: T): UiState<T>()
    data class Error<T>(val message: String): UiState<T>()
}