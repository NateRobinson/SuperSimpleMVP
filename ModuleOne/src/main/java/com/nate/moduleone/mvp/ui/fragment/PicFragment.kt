package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.FragmentPicBinding
import com.nate.moduleone.dagger.component.DaggerPicComponent
import com.nate.moduleone.dagger.module.PicModule
import com.nate.moduleone.mvp.contract.PicContract
import com.nate.moduleone.mvp.presenter.PicPresenter

class PicFragment : SSBaseFragment<PicPresenter>(), PicContract.View{
  private lateinit var binding: FragmentPicBinding
  companion object {
    fun newInstance():PicFragment {
      val fragment = PicFragment()
      return fragment
    }
  }
  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
      DaggerPicComponent //如找不到该类,请编译一下项目
          .builder()
          .sSAppComponent(ssAppComponent)
          .picModule(PicModule(this))
          .build()
          .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
    binding = FragmentPicBinding.inflate(inflater)
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