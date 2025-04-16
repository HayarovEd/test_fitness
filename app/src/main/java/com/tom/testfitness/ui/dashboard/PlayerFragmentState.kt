package com.tom.testfitness.ui.dashboard


sealed class PlayerFragmentState {
    data object Loading : PlayerFragmentState()
    class Error(val message: String) : PlayerFragmentState()
    class Success (
        val description: String): PlayerFragmentState()
}