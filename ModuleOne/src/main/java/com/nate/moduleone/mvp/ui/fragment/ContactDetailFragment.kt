package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.FragmentContactDetailBinding
import com.nate.moduleone.dagger.component.DaggerContactDetailComponent
import com.nate.moduleone.dagger.module.ContactDetailModule
import com.nate.moduleone.mvp.contract.ContactDetailContract
import com.nate.moduleone.mvp.presenter.ContactDetailPresenter

class ContactDetailFragment : SSBaseFragment<ContactDetailPresenter>(), ContactDetailContract.View{
  private lateinit var binding: FragmentContactDetailBinding
  companion object {
    fun newInstance():ContactDetailFragment {
      val fragment = ContactDetailFragment()
      return fragment
    }
  }
  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
      DaggerContactDetailComponent //如找不到该类,请编译一下项目
          .builder()
          .sSAppComponent(ssAppComponent)
          .contactDetailModule(ContactDetailModule(this))
          .build()
          .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
    binding = FragmentContactDetailBinding.inflate(inflater)
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