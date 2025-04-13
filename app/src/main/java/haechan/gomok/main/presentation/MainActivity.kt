package haechan.gomok.main.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import haechan.gomok.databinding.ActivityMainBinding
import haechan.gomok.game.presentation.EXTRA_OWNER
import haechan.gomok.game.presentation.EXTRA_PREVIEW
import haechan.gomok.game.presentation.EXTRA_STONES
import haechan.gomok.game.presentation.EXTRA_RECORDS
import haechan.gomok.game.presentation.GameActivity
import haechan.gomok.game.presentation.RecordActivity
import haechan.gomok.game.presentation.RecordEffect
import haechan.gomok.game.presentation.RecordViewModel
import haechan.gomok.model.Owner
import haechan.gomok.model.Preview
import haechan.gomok.model.Stone
import haechan.gomok.model.Record
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val recordViewModel: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.recordViewModel = recordViewModel

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    mainViewModel.effectChannelFlow.collect {
                        when (it) {
                            MainEffect.NavigateToGameActivity -> navigateToGameActivity()
                            is MainEffect.ResumeGameActivity -> resumeGameActivity(
                                it.preview,
                                it.owner,
                                it.stones,
                            )
                        }
                    }
                }

                launch {
                    recordViewModel.effectChannelFlow.collect {
                        when (it) {
                            is RecordEffect.NavigateToRecordActivity -> navigateToRecordActivity(it.records)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToGameActivity() {
        startActivity(Intent(this, GameActivity::class.java))
    }

    private fun resumeGameActivity(preview: Preview, owner: Owner, stones: List<Stone>) {
        Intent(this, GameActivity::class.java).apply {
            putExtra(EXTRA_PREVIEW, preview)
            putExtra(EXTRA_OWNER, owner as Parcelable)
            putExtra(EXTRA_STONES, ArrayList(stones))
        }.let(::startActivity)
    }

    private fun navigateToRecordActivity(records: List<Record>) {
        Intent(this, RecordActivity::class.java).apply {
            putExtra(EXTRA_RECORDS, ArrayList(records))
        }.let(::startActivity)
    }
}