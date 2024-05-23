package com.example.submission_intermediete_dicoding.ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.submission_intermediete_dicoding.R

class MyPasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), View.OnTouchListener {
    private var showButtonDrawable: Drawable
    private var hideButtonDrawable: Drawable
    private var isPasswordVisible : Boolean = false

    init {
        showButtonDrawable = ContextCompat.getDrawable(context, R.drawable.show) as Drawable
        hideButtonDrawable = ContextCompat.getDrawable(context, R.drawable.hide) as Drawable
        setOnTouchListener(this)
        updatePasswordVisibility()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.getString(R.string.myeditText_password_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    private fun showShowButton() {
        setButtonDrawables(endOfTheText = showButtonDrawable)
    }
    private fun showHideButton() {
        setButtonDrawables(endOfTheText = hideButtonDrawable)
    }



    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText:Drawable? = null, endOfTheText:Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
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

}