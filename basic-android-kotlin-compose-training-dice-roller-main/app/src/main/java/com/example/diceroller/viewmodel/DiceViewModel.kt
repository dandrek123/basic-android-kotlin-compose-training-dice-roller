package com.example.diceroller.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DiceViewModel : ViewModel() {
    private val _diceState = MutableStateFlow(DiceState())
    val diceState: StateFlow<DiceState> = _diceState

    fun rollDice() {
        _diceState.value = _diceState.value.copy(isRolling = true)

        // Simulate a dice roll
        _diceState.value = _diceState.value.copy(
            currentValue = (1..6).random(),
            isRolling = false
        )
    }
}

// Define DiceState as a separate class outside of DiceViewModel
data class DiceState(
    val currentValue: Int = 1,
    val isRolling: Boolean = false,
    val rotation: Float = 0f
)

