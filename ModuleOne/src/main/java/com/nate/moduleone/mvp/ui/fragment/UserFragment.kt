package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils

import com.nate.moduleone.dagger.component.DaggerUserComponent
import com.nate.moduleone.dagger.module.UserModule
import com.nate.moduleone.databinding.FragmentUserBinding
import com.nate.moduleone.mvp.contract.UserContract
import com.nate.moduleone.mvp.presenter.UserPresenter

class UserFragment : SSBaseFragment<UserPresenter>(), UserContract.View {
  private lateinit var binding: FragmentUserBinding

  companion object {
    fun newInstance(): UserFragment {
      val fragment = UserFragment()
      return fragment
    }
  }

  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
    DaggerUserComponent //如找不到该类,请编译一下项目
      .builder().sSAppComponent(ssAppComponent).userModule(UserModule(this)).build().inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentUserBinding.inflate(inflater)
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
