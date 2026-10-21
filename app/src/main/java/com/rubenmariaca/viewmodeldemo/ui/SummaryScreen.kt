package com.rubenmariaca.viewmodeldemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rubenmariaca.viewmodeldemo.ui.theme.Green
import com.rubenmariaca.viewmodeldemo.viewmodel.CounterViewModel

/**
 * VISTA 2: usa el MISMO ViewModel que la Vista 1.
 * Por eso ve el mismo contador y el historial de lo que se hizo allá.
 */
@Composable
fun SummaryScreen(
    viewModel: CounterViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 20.dp)
    ) {
        item {
            Text("Resumen", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("Esta es otra vista, pero lee el mismo ViewModel.")
        }

        item {
            Card {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Contador del ViewModel", fontWeight = FontWeight.Bold)
                    Text("${uiState.count}", fontSize = 22.sp, color = Green, fontWeight = FontWeight.Bold)
                }
            }
        }

        item {
            Button(
                onClick = { viewModel.increment(from = "Vista 2") },
                modifier = Modifier.fillMaxWidth()
            ) { Text("+1 desde esta vista") }
        }

        item {
            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("📌 Conceptos clave", fontWeight = FontWeight.Bold)
                    Text("• El ViewModel guarda el estado y la lógica de la pantalla.")
                    Text("• Sobrevive a cambios de configuración (rotación, idioma, tema).")
                    Text("• La vista solo observa el estado (StateFlow) y envía eventos.")
                    Text("• Varias vistas pueden compartir el mismo ViewModel.")
                    Text("• Se destruye solo cuando la pantalla se cierra (onCleared).")
                }
            }
        }

        item { Text("Historial", fontWeight = FontWeight.Bold) }

        if (uiState.history.isEmpty()) {
            item { Text("Aún no hay acciones. Vuelve y presiona +1.") }
        }
        items(uiState.history) { entry ->
            Text("• $entry")
        }

        item {
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("← Volver")
            }
        }
    }
}
