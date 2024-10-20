package com.cris.proyfirestorek.presentation.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

//crar una funcion generica y esta ejecutará una corrutina..un hilo secundario

suspend fun <T> makeCall(call: suspend () -> T): UiState<T> {
    return withContext(Dispatchers.IO) {
        try {
            UiState.Success(call())
        } catch (e: UnknownHostException) {
            UiState.Error(e.message.orEmpty())
        } catch (e: Exception) {
            UiState.Error(e.message.orEmpty())
        }
    }
}
