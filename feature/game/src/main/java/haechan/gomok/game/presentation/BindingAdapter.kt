package haechan.gomok.game.presentation

import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import haechan.gomok.game.R
import haechan.gomok.model.Cell
import haechan.gomok.model.Owner
import haechan.gomok.model.Preview
import haechan.gomok.model.Stone

@BindingAdapter("android:set_cell")
fun setCell(view: View, cell: Cell?) {
    cell ?: return
    when (cell) {
        is Preview -> R.drawable.shape_rectangle_ff0000
        is Stone -> when (cell.owner) {
            Owner.BLACK -> R.drawable.shape_circle_000000_rectangle_000000
            Owner.WHITE -> R.drawable.shape_circle_ffffff_000000_rectangle_000000
            Owner.NO_OWNER -> R.drawable.shape_rectangle_000000
        }
    }.let {
        view.setBackgroundResource(it)
    }
}

@BindingAdapter("android:textSizeDp")
fun textSizeDP(textView: TextView, textSize: Float?) {
    textSize ?: return
    val scaledSizeInPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        textSize,
        textView.context.resources.displayMetrics
    )
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSizeInPx)
}

@BindingAdapter("android:submitList")
fun <T> submitList(recyclerView: RecyclerView, items: List<T>?) {
    items ?: return
    (recyclerView.adapter as? ListAdapter<T, *>)?.submitList(items)
}