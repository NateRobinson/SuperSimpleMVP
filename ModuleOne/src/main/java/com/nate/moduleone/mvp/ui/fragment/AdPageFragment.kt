package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.FragmentAdPageBinding
import com.nate.moduleone.dagger.component.DaggerAdPageComponent
import com.nate.moduleone.dagger.module.AdPageModule
import com.nate.moduleone.mvp.contract.AdPageContract
import com.nate.moduleone.mvp.presenter.AdPagePresenter

class AdPageFragment : SSBaseFragment<AdPagePresenter>(), AdPageContract.View{
  private lateinit var binding: FragmentAdPageBinding
  companion object {
    fun newInstance():AdPageFragment {
      val fragment = AdPageFragment()
      return fragment
    }
  }
  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
      DaggerAdPageComponent //如找不到该类,请编译一下项目
          .builder()
          .sSAppComponent(ssAppComponent)
          .adPageModule(AdPageModule(this))
          .build()
          .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
    binding = FragmentAdPageBinding.inflate(inflater)
    return binding.root
  }

  override fun initData(savedInstanceState: Bundle?) {

  }

  override fun showLoading() {

  }

  override fun hideLoading() {

  }

  override fun showMessage(message: String) {
    ToastUtils.showShort(message)
  }
}