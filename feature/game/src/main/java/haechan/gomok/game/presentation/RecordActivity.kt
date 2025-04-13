package haechan.gomok.game.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import haechan.gomok.game.R
import haechan.gomok.game.databinding.ActivityRecordBinding
import haechan.gomok.model.Record

class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra(EXTRA_RECORDS, Record::class.java)
        } else {
            intent.getParcelableArrayListExtra(EXTRA_RECORDS)
        }?.let { records->
            binding.records = records
        }

        binding.recyclerRecord.adapter = RecordAdapter()
    }
}

const val EXTRA_RECORDS = "EXTRA_RECORDS"