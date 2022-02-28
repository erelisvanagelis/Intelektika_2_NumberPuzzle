// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import common.NPuzzle
import utils.StopWatch

@Composable
@Preview
fun App() {
    val game = remember { Game() }
    val stopWatch = remember { StopWatch() }
    MaterialTheme {
        NPuzzle(game, stopWatch)
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Intelektika_2_8-Delione Antanas_Tamašauskas_PI19C",
        state = WindowState(size= DpSize(800.dp, 640.dp))) {
        App()
    }
}
