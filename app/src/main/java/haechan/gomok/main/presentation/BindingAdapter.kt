package haechan.gomok.main.presentation

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("android:isVisible")
fun isVisible(view: View, isVisible: Boolean?) {
    isVisible ?: return
    view.isVisible = isVisible
}