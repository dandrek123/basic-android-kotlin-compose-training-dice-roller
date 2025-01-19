/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.diceroller

import android.os.Bundle
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme
import com.example.diceroller.viewmodel.DiceViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Composable
fun DiceScreen(viewModel: DiceViewModel = viewModel()) {
    val diceState by viewModel.diceState.collectAsState()

    val rotation by animateFloatAsState(
        targetValue = if (diceState.isRolling) 360f else 0f,
        animationSpec = tween(500)
    )

    val context = LocalContext.current
    val mediaPlayer by remember { mutableStateOf(MediaPlayer.create(context, R.raw.dice_roll)) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display the dice image with animation
        Image(
            painter = painterResource(getDiceDrawable(diceState.currentValue)),
            contentDescription = diceState.currentValue.toString(),
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(rotationZ = rotation) // Rotate with animation
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to roll the dice with sound
        Button(
            onClick = {
                viewModel.rollDice()
                mediaPlayer.start() // Play dice roll sound
            }
        ) {
            Text(text = stringResource(R.string.roll), fontSize = 24.sp)
        }
    }
}

// Helper function to map dice values to drawable resources
fun getDiceDrawable(value: Int): Int {
    return when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceScreen()
}

