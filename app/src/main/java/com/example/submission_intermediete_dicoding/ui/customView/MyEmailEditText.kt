package com.example.submission_intermediete_dicoding.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submission_intermediete_dicoding.R

class MyEmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    private val errorDrawable: Drawable

    init {
        errorDrawable = ContextCompat.getDrawable(context, R.drawable.danger_circle) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.getString(R.string.email_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun validateEmail(email: String) {
        if (email.isNotEmpty() && !email.contains("@")) {
            showError()
        } else {
            hideError()
        }
    }

    fun showError() {
        setError(context.getString(R.string.invalid_email_error))
        setButtonDrawables(endOfTheText = errorDrawable)
    }

    fun hideError() {
        setError(null)
        setButtonDrawables()
    }

    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText: Drawable? = null, endOfTheText: Drawable? = null, bottomOfTheText: Drawable? = null) {
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val drawableStart: Float
            val drawableEnd: Float
            var isDrawableClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                drawableEnd = (errorDrawable.intrinsicWidth + paddingStart).toFloat()
                if (event?.x!! < drawableEnd) isDrawableClicked = true
            } else {
                drawableStart = (width - paddingEnd - errorDrawable.intrinsicWidth).toFloat()
                if (event?.x!! > drawableStart) isDrawableClicked = true
            }

            if (isDrawableClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {

                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }
}