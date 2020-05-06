package com.nate.supersimplemvp.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.supersimplemvp.dagger.component.DaggerUserComponent
import com.nate.supersimplemvp.dagger.module.UserModule
import com.nate.supersimplemvp.databinding.FragmentUserBinding
import com.nate.supersimplemvp.mvp.contract.UserContract
import com.nate.supersimplemvp.mvp.presenter.UserPresenter

/**
 * Created by Nate on 2020/5/6
 */
class UserFragment : SSBaseFragment<UserPresenter>(), UserContract.View {
  lateinit var binding: FragmentUserBinding

  companion object {
    fun newInstance():UserFragment {
      val fragment = UserFragment()
      return fragment
    }
  }

  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
    DaggerUserComponent
      .builder()
      .sSAppComponent(ssAppComponent)
      .userModule(UserModule(this))
      .build()
      .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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