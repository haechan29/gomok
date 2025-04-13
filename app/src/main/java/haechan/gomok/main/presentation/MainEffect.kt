package haechan.gomok.main.presentation

import haechan.gomok.model.Owner
import haechan.gomok.model.Preview
import haechan.gomok.model.Record
import haechan.gomok.model.Stone

sealed class MainEffect {
    data object NavigateToGameActivity: MainEffect()

    data class ResumeGameActivity(
        val preview: Preview,
        val owner: Owner,
        val stones: List<Stone>,
    ): MainEffect()
}