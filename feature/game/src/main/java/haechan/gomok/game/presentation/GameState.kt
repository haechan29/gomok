package haechan.gomok.game.presentation

sealed class GameState {
    data object IN_PROGRESS: GameState()
    data object BLACK_WIN: GameState()
    data object WHITE_WIN: GameState()
}