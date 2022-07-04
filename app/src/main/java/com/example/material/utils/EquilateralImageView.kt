package com.example.material.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class EquilateralImageView @JvmOverloads constructor (
    context:Context,attributeSet: AttributeSet?=null,defStyleAttributeSet: Int = 0
) : AppCompatImageView(context,attributeSet,defStyleAttributeSet
){
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }


}