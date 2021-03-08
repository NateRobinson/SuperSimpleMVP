package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerPreviewPicComponent
import com.nate.moduleone.dagger.module.PreviewPicModule
import com.nate.moduleone.databinding.ModuleOneActivityPreviewPicBinding
import com.nate.moduleone.mvp.contract.PreviewPicContract
import com.nate.moduleone.mvp.presenter.PreviewPicPresenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class PreviewPicActivity : SSBaseActivity<PreviewPicPresenter>(), PreviewPicContract.View {

  private lateinit var binding: ModuleOneActivityPreviewPicBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerPreviewPicComponent.builder().sSAppComponent(ssAppComponent).previewPicModule(PreviewPicModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ModuleOneActivityPreviewPicBinding.inflate(layoutInflater)
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