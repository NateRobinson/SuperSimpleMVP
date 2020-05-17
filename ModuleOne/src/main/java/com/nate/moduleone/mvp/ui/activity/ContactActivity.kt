package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.ActivityContactBinding
import com.nate.moduleone.dagger.component.DaggerContactComponent
import com.nate.moduleone.dagger.module.ContactModule
import com.nate.moduleone.mvp.contract.ContactContract
import com.nate.moduleone.mvp.presenter.ContactPresenter

class ContactActivity : SSBaseActivity<ContactPresenter>(), ContactContract.View {

  private lateinit var binding: ActivityContactBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerContactComponent.builder().sSAppComponent(ssAppComponent).contactModule(ContactModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityContactBinding.inflate(layoutInflater)
    setContentView(binding.root)
    return 0
  }

  override fun initData(savedInstanceState: Bundle?) {
    binding.testTv.text = "I was been updated"
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun showMessage(message: String) {
    ToastUtils.showShort(message)
  }
}
