package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerModuleOneMainComponent
import com.nate.moduleone.dagger.module.ModuleOneMainModule
import com.nate.moduleone.databinding.ActivityModuleOneMainBinding
import com.nate.moduleone.mvp.contract.ModuleOneMainContract
import com.nate.moduleone.mvp.presenter.ModuleOneMainPresenter

class ModuleOneMainActivity : SSBaseActivity<ModuleOneMainPresenter>(), ModuleOneMainContract.View {

  private lateinit var binding: ActivityModuleOneMainBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerModuleOneMainComponent.builder().sSAppComponent(ssAppComponent).moduleOneMainModule(ModuleOneMainModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityModuleOneMainBinding.inflate(layoutInflater)
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
