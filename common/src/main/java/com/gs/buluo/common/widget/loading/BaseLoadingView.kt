package com.gs.buluo.common.widget.loading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable

interface BaseLoadingView {
    fun startAnimation()
    fun endAnimation()
    fun createContentView(context:Context?,inflater: LayoutInflater, @Nullable container: ViewGroup?): View
}