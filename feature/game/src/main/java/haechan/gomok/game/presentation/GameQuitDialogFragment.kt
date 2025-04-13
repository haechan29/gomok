package haechan.gomok.game.presentation

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import haechan.gomok.game.databinding.DialogFragmentGameQuitBinding
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels

class GameQuitDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentGameQuitBinding
    
    var onPositiveButtonClick: ((DialogFragment) -> Unit)? = null
    var onNegativeButtonClick: ((DialogFragment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentGameQuitBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewPositive.setOnClickListener{
            onPositiveButtonClick?.invoke(this)
        }
        binding.textviewNegative.setOnClickListener{
            onNegativeButtonClick?.invoke(this)
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            val width = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300f, resources.displayMetrics
            ).toInt()
            val height = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 200f, resources.displayMetrics
            ).toInt()
            setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            setLayout(width, height)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameQuitDialogFragment()
    }
}