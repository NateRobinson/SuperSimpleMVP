package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.ModuleOneFragmentPicDetailBinding
import com.nate.moduleone.dagger.component.DaggerPicDetailComponent
import com.nate.moduleone.dagger.module.PicDetailModule
import com.nate.moduleone.mvp.contract.PicDetailContract
import com.nate.moduleone.mvp.presenter.PicDetailPresenter

class PicDetailFragment : SSBaseFragment<PicDetailPresenter>(), PicDetailContract.View{
  private lateinit var binding: ModuleOneFragmentPicDetailBinding
  companion object {
    fun newInstance():PicDetailFragment {
      val fragment = PicDetailFragment()
      return fragment
    }
  }
  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
      DaggerPicDetailComponent //如找不到该类,请编译一下项目
          .builder()
          .sSAppComponent(ssAppComponent)
          .picDetailModule(PicDetailModule(this))
          .build()
          .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
    binding = ModuleOneFragmentPicDetailBinding.inflate(inflater)
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