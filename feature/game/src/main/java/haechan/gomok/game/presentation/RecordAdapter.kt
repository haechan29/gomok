package haechan.gomok.game.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import haechan.gomok.game.databinding.ListitemRecordBinding
import haechan.gomok.model.Record

class RecordAdapter: ListAdapter<Record, RecordAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Record, newItem: Record) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListitemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ListitemRecordBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {
            binding.record = record
        }
    }
}