package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerTestPageComponent
import com.nate.moduleone.dagger.module.TestPageModule
import com.nate.moduleone.databinding.ActivityTestPageBinding
import com.nate.moduleone.mvp.contract.TestPageContract
import com.nate.moduleone.mvp.presenter.TestPagePresenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class TestPageActivity : SSBaseActivity<TestPagePresenter>(), TestPageContract.View {

  private lateinit var binding: ActivityTestPageBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerTestPageComponent.builder().sSAppComponent(ssAppComponent).testPageModule(TestPageModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityTestPageBinding.inflate(layoutInflater)
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