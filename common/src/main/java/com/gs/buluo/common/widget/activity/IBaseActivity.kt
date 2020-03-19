package com.gs.buluo.common.widget.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gs.buluo.common.widget.loading.BaseLoadingDialog
import com.bz.baselibiary.view.IView
import com.gs.buluo.common.widget.loading.LoadingDialog

/**
 *  @Author:        hjn
 * @CreateDate:     2019/4/18 18:20
 * @Description: ativity基类
 *
 */
abstract class IBaseActivity : AppCompatActivity(), IView {
    var loadingDialog: BaseLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        onStartInit()
        super.onCreate(savedInstanceState)
        initActivity(savedInstanceState)
        onEndInit()
    }

    internal open fun initActivity(savedInstanceState: Bundle?) {
        initVariables()
        var layoutId = getLayoutId()
        if (layoutId != -1) {
            setContentView(getLayoutId())
        }
        initViews(savedInstanceState)
        initData()
    }

    /**
     * 获取布局ID
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * 初始传递参数
     */
    abstract fun initVariables()

    /**
     * 初始化View
     */
    abstract fun initViews(savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * onCreate方法里面最先调用
     */
    fun onStartInit() {

    }

    /**
     * onCreate方法里面最后调用
     */
    fun onEndInit() {

    }

    override fun showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog()
        }
        loadingDialog?.show(supportFragmentManager)
    }

    fun createLoadingDialog(): BaseLoadingDialog {
        return LoadingDialog.newInstance()
    }

    override fun hideLoadingDialog() {
        loadingDialog?.let {
            if (it.fragmentManager != null)
                it.dismissAllowingStateLoss()
            loadingDialog = null
        }
    }

}