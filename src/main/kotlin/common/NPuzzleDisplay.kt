package common

import Game
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.StopWatch

@Composable
fun NPuzzle(game: Game, stopWatch: StopWatch) {
    val state = game.state
    var gridSize by remember { mutableStateOf("3") }
    var iterationLimit by remember { mutableStateOf("10000") }
    val sliderRange= 100f..1000f

    if (state.isActive) {
        stopWatch.start()
    } else {
        stopWatch.pause()
    }

    Row(modifier = Modifier.fillMaxSize().padding(10.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(3f),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedContainer(
                title = {
                    Text(
                        text = "Settings:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                content = {
                    OutlinedTextField(
                        value = gridSize,
                        onValueChange = {
                            gridSize = it
                        },
                        label = { Text("Grid Size") },
                    )

                    Button(

                        onClick = {
                            stopWatch.reset()
                            game.canScramblePuzzle(gridSize)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Scramble")
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    OutlinedTextField(
                        value = iterationLimit,
                        onValueChange = {
                            iterationLimit = it
                        },
                        label = { Text("Iteration limit") },
                    )

                    Button(
                        onClick = {
                            stopWatch.reset()
                            stopWatch.start()
                            game.canSolvePuzzle(iterationLimit = iterationLimit)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Solve")
                    }
                }
            )

            OutlinedContainer(
                title = {
                    Text(
                        text = "Step duration:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                content = {
                    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Slider(value = state.stepDelay.toFloat(), valueRange = sliderRange, onValueChange = {game.setDelay(it.toLong())})
                        Text(state.stepDelay.toString() + "ms")
                    }

                }

            )

            OutlinedContainer(
                title = {
                    Text(
                        text = "Data:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                content = {
                    Text(text = "Status: ${state.status}")
                    Text(text = "Current step: ${state.currentStep}")
                    Text(text = "Total step count: ${state.stepCount}")
                    Text(text = "Time: ${stopWatch.formattedTime}")
                }
            )
        }

        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().weight(7f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = state.message,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(1.dp).border(1.dp, color = Color(123, 31, 162))
            )
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                GridButtonContainer(
                    grid = state.currentState,
                    onButtonClick = game::buttonPressed
                )
            }
        }
    }
}