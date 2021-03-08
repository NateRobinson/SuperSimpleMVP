package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerContactListComponent
import com.nate.moduleone.dagger.module.ContactListModule
import com.nate.moduleone.databinding.ActivityContactListBinding
import com.nate.moduleone.mvp.contract.ContactListContract
import com.nate.moduleone.mvp.presenter.ContactListPresenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class ContactListActivity : SSBaseActivity<ContactListPresenter>(), ContactListContract.View {

  private lateinit var binding: ActivityContactListBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerContactListComponent.builder().sSAppComponent(ssAppComponent).contactListModule(ContactListModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityContactListBinding.inflate(layoutInflater)
    setContentView(binding.root)
    return 0
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