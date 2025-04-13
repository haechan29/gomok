package haechan.gomok.game.presentation

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import haechan.gomok.game.databinding.ActivityGameBinding
import haechan.gomok.model.Owner
import haechan.gomok.model.Preview
import haechan.gomok.model.Stone
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.mainViewModel = gameViewModel

        gameViewModel.setPreview(9, 9)

        setData()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    gameViewModel.gameStateFlow.collect { gameState ->
                        if (gameState == GameState.BLACK_WIN) {
                            Toast.makeText(this@GameActivity, "흑돌이 승리했습니다.", Toast.LENGTH_SHORT).show()
                        } else if (gameState == GameState.WHITE_WIN) {
                            Toast.makeText(this@GameActivity, "백돌이 승리했습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (gameViewModel.isGameFinishedFlow.value) {
                    finish()
                } else {
                    showGameQuitDialog()
                }
            }
        })
    }

    private fun setData() {
        val preview = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_PREVIEW, Preview::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_PREVIEW)
        } ?: return

        val owner = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_OWNER, Owner::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_OWNER)
        } ?: return

        val stones = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(EXTRA_STONES, Stone::class.java)
        } else {
            intent.getParcelableArrayListExtra(EXTRA_STONES)
        } ?: return

        gameViewModel.setData(preview, owner, stones)
    }

    private fun showGameQuitDialog() {
        GameQuitDialogFragment.newInstance()
            .apply {
                onPositiveButtonClick = { dialog ->
                    gameViewModel.saveData()
                    dialog.dismiss()
                    finish()
                }
                onNegativeButtonClick = { dialog ->
                    gameViewModel.clearData()
                    dialog.dismiss()
                    finish()
                }
            }
            .show(supportFragmentManager, GameQuitDialogFragment::class.simpleName)
    }
}

const val EXTRA_PREVIEW = "EXTRA_PREVIEW"
const val EXTRA_OWNER = "EXTRA_OWNER"
const val EXTRA_STONES = "EXTRA_STONE"