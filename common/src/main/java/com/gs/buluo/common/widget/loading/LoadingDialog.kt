package com.gs.buluo.common.widget.loading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gs.buluo.common.R

class LoadingDialog : BaseLoadingDialog() {
    companion object {
        fun newInstance(): LoadingDialog {
            var loadingDialog = LoadingDialog()
            return loadingDialog
        }
    }

    override fun genLoadingView(): BaseLoadingView {
        return LoadingViewImp()
    }

    class LoadingViewImp : BaseLoadingView {
        override fun startAnimation() {

        }

        override fun endAnimation() {
        }

        override fun createContentView(context: Context?, inflater: LayoutInflater, container: ViewGroup?): View {
            var contentView = inflater.inflate(R.layout.dialog_loading, container, false)
            return contentView
        }
    }
}