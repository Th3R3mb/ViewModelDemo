# ViewModel Demo

Aplicación sencilla en **Kotlin + Jetpack Compose** que explica el concepto de **ViewModel**.

**Autor:** Ruben Esteban Mariaca Bautista · Ingeniería de Sistemas · UNAB

## ¿Qué es un ViewModel?

Es una clase de Android Jetpack que **guarda el estado y la lógica de una pantalla**, separada de la vista (UI).

- **Sobrevive a cambios de configuración:** al girar el teléfono Android destruye y recrea la Activity, pero el ViewModel se conserva.
- **Separa responsabilidades:** la vista solo dibuja y envía eventos; el ViewModel decide qué hacer.
- **Expone estado observable:** con `StateFlow`, la vista se redibuja sola cuando el estado cambia.
- **Se puede compartir:** varias vistas pueden usar la misma instancia.
- **Se destruye solo cuando la pantalla termina de verdad** (se llama `onCleared()`).

## Estructura (1 ViewModel, 2 vistas)

| Archivo | Rol |
|---|---|
| `viewmodel/CounterViewModel.kt` | El único ViewModel: estado (`CounterUiState`) + eventos (`increment`, `decrement`, `reset`) |
| `ui/CounterScreen.kt` | **Vista 1:** compara un contador con `remember` vs. uno en el ViewModel |
| `ui/SummaryScreen.kt` | **Vista 2:** usa el mismo ViewModel; muestra el contador, el historial y los conceptos clave |
| `MainActivity.kt` | Crea el ViewModel con `viewModel()` y navega entre las dos vistas |

## Flujo de datos

```
Vista ──(evento: increment())──▶ ViewModel
Vista ◀──(estado: StateFlow)──── ViewModel
```

## Cómo probarlo

1. Ejecuta la app y presiona **+1** varias veces.
2. **Gira el teléfono:** el contador con `remember` vuelve a 0; el del ViewModel se mantiene.
3. Ve a **Ver resumen**: la segunda vista muestra el mismo valor y el historial.
4. Presiona **+1 desde esta vista**, vuelve, y verás el cambio reflejado en la Vista 1.

## Tecnologías

Kotlin · Jetpack Compose · Material 3 · `lifecycle-viewmodel-compose` · `StateFlow` · Navigation Compose
