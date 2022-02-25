// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import common.GridButtonContainer

@Composable
@Preview
fun App() {
    val game = remember { Game() }
    val state = game.state
    var gridSize by remember { mutableStateOf("3") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = gridSize,
                onValueChange = {
                    gridSize = it
                },
                label = { Text("Grid Size") },
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = state.message)
            }

            Box(modifier = Modifier.fillMaxHeight(0.82f), contentAlignment = Alignment.Center) {
                GridButtonContainer(
                    oldGrid = state.oldState,
                    newGrid = state.currentState,
                    onButtonClick = game::buttonPressed
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Text(text = "Current step: ${state.currentStep}")
                Text(text = "Total step count: ${state.totalSteps}")
                Text(text = "Time: ${state.totalTime}")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { game.canScramblePuzzle(gridSize) },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text("Scramble")
                }
                Button(
                    onClick = { game.solvePuzzle() },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    Text("Solve")
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Intelektika_2_8-Delione Antanas_Tamašauskas_PI19C") {
        App()
    }
}
