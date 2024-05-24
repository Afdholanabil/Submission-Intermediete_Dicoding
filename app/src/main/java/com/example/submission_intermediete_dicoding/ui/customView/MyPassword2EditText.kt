package com.example.submission_intermediete_dicoding.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submission_intermediete_dicoding.R

class MyPassword2EditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {

    private var showButtonDrawable: Drawable
    private var hideButtonDrawable: Drawable

    private var isPasswordVisible: Boolean = false

    init {
        showButtonDrawable = ContextCompat.getDrawable(context, R.drawable.show) as Drawable
        hideButtonDrawable = ContextCompat.getDrawable(context, R.drawable.hide) as Drawable

        setOnTouchListener(this)
        updatePasswordVisibility()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.getString(R.string.myeditText_password_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }


    private fun showShowButton() {
        setButtonDrawables(endOfTheText = showButtonDrawable)
    }

    private fun showHideButton() {
        setButtonDrawables(endOfTheText = hideButtonDrawable)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText, topOfTheText, endOfTheText, bottomOfTheText
        )
    }

    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        if (compoundDrawables[2] != null) {
            val drawableStart: Float
            val drawableEnd: Float
            var isDrawableClicked = false

            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                drawableEnd = (hideButtonDrawable.intrinsicWidth + paddingStart).toFloat()
                if (event?.x!! < drawableEnd) isDrawableClicked = true
            } else {
                drawableStart = (width - paddingEnd - hideButtonDrawable.intrinsicWidth).toFloat()
                if (event?.x!! > drawableStart) isDrawableClicked = true
            }

            if (isDrawableClicked) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPasswordVisible = !isPasswordVisible
                        updatePasswordVisibility()
                        invalidate()
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updatePasswordVisibility() {
        if (isPasswordVisible) {
            setButtonDrawables(endOfTheText = hideButtonDrawable)
            transformationMethod = HideReturnsTransformationMethod.getInstance()
        } else {
            setButtonDrawables(endOfTheText = showButtonDrawable)
            transformationMethod = PasswordTransformationMethod.getInstance()
        }
        setSelection(text?.length ?: 0)
    }

    private fun validatePassword() {
        val password = text.toString()
        if (password.length < 8) {
            showError()
        } else {
            hideError()
        }
    }

    fun showError() {
        error = context.getString(R.string.error_password_length)
    }

    fun hideError() {
        setError(null, null)
    }
}