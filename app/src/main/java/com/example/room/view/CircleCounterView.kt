package com.example.room.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.example.room.R
import com.example.room.databinding.ViewCircleCounterBinding

class CircleCounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr)  {

    private val binding = ViewCircleCounterBinding.inflate(LayoutInflater.from(context), this)

    var done: Int
        get() = binding.done.text.toString().toInt()
        set(value) {
            binding.done.text = done.toString()
        }

    var total: Int
        get() = binding.total.text.toString().toInt()
        set(value) {
            binding.total.text = done.toString()
        }


    init {
        setAttrs(attrs, R.styleable.CircleCounterView) { it ->
            binding.done.text = it.getInt(R.styleable.CircleCounterView_done, 0).toString()
            binding.total.text = it.getInt(R.styleable.CircleCounterView_total, 0).toString()
        }
    }

}

inline fun View.setAttrs(
    attrs: AttributeSet?,
    styleable: IntArray,
    crossinline body: (TypedArray) -> Unit
) {
    context.theme.obtainStyledAttributes(attrs, styleable, 0, 0)
        .apply {
            try {
                body.invoke(this)
            } finally {
                recycle()
            }
        }
}