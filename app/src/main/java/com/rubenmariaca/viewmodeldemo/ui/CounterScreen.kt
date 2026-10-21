package com.rubenmariaca.viewmodeldemo.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rubenmariaca.viewmodeldemo.ui.theme.Green
import com.rubenmariaca.viewmodeldemo.ui.theme.Orange
import com.rubenmariaca.viewmodeldemo.viewmodel.CounterViewModel

/**
 * VISTA 1: experimento.
 * Compara un contador guardado con `remember` (vive en la vista)
 * contra uno guardado en el ViewModel.
 */
@Composable
fun CounterScreen(
    viewModel: CounterViewModel,
    onGoToSummary: () -> Unit
) {
    // 1) La vista OBSERVA el estado del ViewModel. Cuando cambia, se redibuja sola.
    val uiState by viewModel.uiState.collectAsState()

    // 2) Contador local con remember: se pierde al girar el teléfono
    //    o al salir de esta pantalla.
    var rememberCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("¿Qué es un ViewModel?", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(
            "Es una clase que guarda el estado y la lógica de una pantalla, " +
                "separada de la vista. Su gran ventaja: sobrevive cuando Android " +
                "recrea la pantalla, por ejemplo al girar el teléfono.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CounterCard(
                title = "Con remember",
                subtitle = "vive en la vista",
                value = rememberCount,
                color = Orange,
                modifier = Modifier.weight(1f)
            )
            CounterCard(
                title = "Con ViewModel",
                subtitle = "vive en el ViewModel",
                value = uiState.count,
                color = Green,
                modifier = Modifier.weight(1f)
            )
        }

        // 3) La vista NO cambia el estado del ViewModel directamente:
        //    le envía eventos (increment, decrement, reset).
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { rememberCount--; viewModel.decrement(from = "Vista 1") },
                modifier = Modifier.weight(1f)
            ) { Text("−1") }
            Button(
                onClick = { rememberCount++; viewModel.increment(from = "Vista 1") },
                modifier = Modifier.weight(1f)
            ) { Text("+1") }
            OutlinedButton(
                onClick = { rememberCount = 0; viewModel.reset() },
                modifier = Modifier.weight(1f)
            ) { Text("Reiniciar") }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7FF))) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("🧪 Prueba", fontWeight = FontWeight.Bold)
                Text("1. Presiona +1 varias veces.")
                Text("2. Gira el teléfono 🔄.")
                Text("3. El contador naranja vuelve a 0; el verde se mantiene.")
                Text("4. Ve al resumen y regresa: pasa lo mismo.")
            }
        }

        Button(onClick = onGoToSummary, modifier = Modifier.fillMaxWidth()) {
            Text("Ver resumen →")
        }
    }
}

@Composable
private fun CounterCard(
    title: String,
    subtitle: String,
    value: Int,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontWeight = FontWeight.Bold, color = color)
            Text(subtitle, fontSize = 12.sp, textAlign = TextAlign.Center)
            Text("$value", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}
