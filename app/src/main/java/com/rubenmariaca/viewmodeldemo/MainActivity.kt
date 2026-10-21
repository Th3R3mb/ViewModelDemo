package com.rubenmariaca.viewmodeldemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rubenmariaca.viewmodeldemo.ui.CounterScreen
import com.rubenmariaca.viewmodeldemo.ui.SummaryScreen
import com.rubenmariaca.viewmodeldemo.ui.theme.ViewModelDemoTheme
import com.rubenmariaca.viewmodeldemo.viewmodel.CounterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewModelDemoTheme {
                // viewModel() pide el ViewModel a Android:
                // - la primera vez lo crea;
                // - si la Activity se recrea (al girar), devuelve la MISMA instancia.
                // Se pide aquí, fuera del NavHost, para que las dos vistas compartan el mismo.
                val counterViewModel: CounterViewModel = viewModel()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "counter",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("counter") {
                            CounterScreen(
                                viewModel = counterViewModel,
                                onGoToSummary = { navController.navigate("summary") }
                            )
                        }
                        composable("summary") {
                            SummaryScreen(
                                viewModel = counterViewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
