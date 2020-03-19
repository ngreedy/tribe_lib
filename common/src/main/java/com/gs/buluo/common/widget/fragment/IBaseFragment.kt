package com.gs.buluo.common.widget.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.gs.buluo.common.widget.loading.BaseLoadingDialog
import com.bz.baselibiary.view.IView
import com.gs.buluo.common.widget.loading.LoadingDialog

/**
 *  @Author:        hjn
 * @CreateDate:     2019/4/18 18:28
 * @Description:
 *
 */
abstract class IBaseFragment : Fragment(), IView {
    var loadingDialog: BaseLoadingDialog? = null

    override fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog()
        }
        loadingDialog?.show(childFragmentManager)
    }

    override fun hideLoadingDialog() {
        loadingDialog?.let {
            if (it.fragmentManager != null)
                it.dismissAllowingStateLoss()
            loadingDialog = null
        }
    }

    fun createLoadingDialog(): BaseLoadingDialog {
        return LoadingDialog.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariables(getArguments())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    abstract fun initVariables(bundle: Bundle?)

    /**
     * 获取布局ID
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun initViews()

    /**
     * 初始化数据
     */
    abstract fun initData()

}