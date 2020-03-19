package com.gs.buluo.common.widget.fragment

import android.os.Bundle
import android.view.View
import com.gs.buluo.common.widget.fragment.IBaseFragment

/**
 *  @Author:        hjn
 * @CreateDate:     2019/6/13 10:32
 * @Description: 配合ViewPager使用
 *
 */
abstract class IBaseLazyFragment : IBaseFragment() {
    protected var mViewIsPrepared = false// 界面是否准备好
    protected var isLazyLoaded = false// 是否已经加载

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewIsPrepared = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preLoadData()
    }

    final override fun initData() {

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        preLoadData()
    }

    private fun preLoadData() {
        if (userVisibleHint && mViewIsPrepared && !isLazyLoaded) {
            lazyInitData()
            isLazyLoaded = true
        }
    }

    override fun onDestroyView() {
        mViewIsPrepared = false// 界面是否准备好
        isLazyLoaded = false// 是否已经加载
        super.onDestroyView()
    }

    abstract fun lazyInitData()


}