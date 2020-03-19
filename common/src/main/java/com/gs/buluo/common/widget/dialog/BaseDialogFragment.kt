package com.gs.buluo.common.widget.dialog

import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gs.buluo.common.widget.util.ScreenUtils


/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/17 15:25
 * @Description:
 *
 */
abstract class BaseDialogFragment : DialogFragment() {
    private var isShown = false

    companion object {
        const val WIDTH_PERCENTAGE: Float = 0.8f
        const val TAG: String = "BaseDialogFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariables(getArguments())
    }


    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        initWindow()
        return createLayoutView(inflater, container) ?: inflater.inflate(getLayoutId(), container, false)
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

    open fun createLayoutView(inflater: LayoutInflater, @Nullable container: ViewGroup?): View? {
        return null
    }

    abstract fun initViews()

    /**
     * 初始化数据
     */
    abstract fun initData()


    /**
     * 注意：Fragment需可见，才能生效
     */
    fun setCanceledOnTouchOutside(cancel: Boolean) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(cancel)
            dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event!!.getAction() == KeyEvent.ACTION_DOWN) {
                        return true;
                    }
                    return false
                }
            });
        }
    }


    //默认Window居中，宽x640，高Wrap_Content
    open fun initWindow() {
        configWindow(
            Gravity.CENTER,
            (ScreenUtils.getWindowWidth(activity!!) * WIDTH_PERCENTAGE).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    protected fun configWindow(gravity: Int, width: Int, height: Int) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//        window!!.setDimAmount(0f)
        // 将Dialog显示到屏幕的中间
        window.setGravity(gravity)
        // 去掉Dialog本身的padding
        window.decorView.setPadding(0, 0, 0, 0)
        val layoutParams = window.attributes
        // 设置宽度为屏幕宽度
        layoutParams.width = width
        layoutParams.height = height
        window.attributes = layoutParams
    }

    open fun show(transaction: FragmentTransaction?) {
        /**
         * 如果已经调用 savedInstanceState 不在响应
         */
        if (!childFragmentManager.isStateSaved) {
            if (!isShown) {
                show(transaction, TAG)
                isShown = true
            }
        }
    }

    open fun show(manager: FragmentManager?) {
        /**
         * 如果已经调用 savedInstanceState 不在响应
         */
        if (!(manager?.isStateSaved ?: false)) {
            if (!isShown) {
                show(manager, TAG)
                isShown = true
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        isShown = false
    }
}