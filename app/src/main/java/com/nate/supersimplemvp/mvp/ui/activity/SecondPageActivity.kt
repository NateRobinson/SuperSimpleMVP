package com.nate.supersimplemvp.mvp.ui.activity

import android.os.Bundle
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.supersimplemvp.databinding.ActivitySecondPageBinding
import com.nate.supersimplemvp.dagger.component.DaggerSecondPageComponent
import com.nate.supersimplemvp.dagger.module.SecondPageModule
import com.nate.supersimplemvp.mvp.contract.SecondPageContract
import com.nate.supersimplemvp.mvp.presenter.SecondPagePresenter

class SecondPageActivity : SSBaseActivity<SecondPagePresenter>(), SecondPageContract.View {

  private lateinit var binding: ActivitySecondPageBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerSecondPageComponent.builder().sSAppComponent(ssAppComponent).secondPageModule(SecondPageModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivitySecondPageBinding.inflate(layoutInflater)
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
