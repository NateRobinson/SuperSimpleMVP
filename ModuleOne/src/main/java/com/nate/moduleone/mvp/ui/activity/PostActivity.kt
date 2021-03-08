package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerPostComponent
import com.nate.moduleone.dagger.module.PostModule
import com.nate.moduleone.databinding.ActivityPostBinding
import com.nate.moduleone.mvp.contract.PostContract
import com.nate.moduleone.mvp.presenter.PostPresenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class PostActivity : SSBaseActivity<PostPresenter>(), PostContract.View {

  private lateinit var binding: ActivityPostBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerPostComponent.builder().sSAppComponent(ssAppComponent).postModule(PostModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityPostBinding.inflate(layoutInflater)
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