package com.rubenmariaca.viewmodeldemo.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Estado de la pantalla: TODO lo que la vista necesita para dibujarse.
 * Es inmutable (val): para cambiarlo se crea una copia con copy().
 */
data class CounterUiState(
    val count: Int = 0,
    val history: List<String> = emptyList()
)

/**
 * ViewModel = el "cerebro" de la pantalla.
 *
 * - Guarda el estado (contador e historial).
 * - Contiene la lógica (sumar, restar, reiniciar).
 * - Sobrevive a cambios de configuración (girar el teléfono, cambiar idioma, modo oscuro),
 *   porque Android NO lo destruye cuando recrea la Activity.
 * - Solo se destruye cuando la pantalla se cierra definitivamente (ver onCleared()).
 * - No conoce a la vista: no importa nada de Compose ni guarda referencias a la UI.
 */
class CounterViewModel : ViewModel() {

    // Privado y mutable: solo el ViewModel puede modificar el estado.
    private val _uiState = MutableStateFlow(CounterUiState())

    // Público y de solo lectura: la vista solo puede observarlo.
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    // ── Eventos que la vista le envía al ViewModel ──────────────────────────
    fun increment(from: String) = changeCount(+1, "+1 desde $from")

    fun decrement(from: String) = changeCount(-1, "-1 desde $from")

    fun reset() {
        _uiState.update { it.copy(count = 0, history = listOf("Reinicio → 0") + it.history) }
    }

    private fun changeCount(delta: Int, action: String) {
        _uiState.update { state ->
            val newCount = state.count + delta
            state.copy(
                count = newCount,
                history = (listOf("$action → $newCount") + state.history).take(20)
            )
        }
    }

    // Se llama UNA sola vez, cuando el ViewModel se destruye de verdad
    // (por ejemplo, al cerrar la app con "atrás"), NO al girar el teléfono.
    override fun onCleared() {
        super.onCleared()
        println("CounterViewModel destruido")
    }
}
