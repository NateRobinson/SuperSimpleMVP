package com.nate.moduleone.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.dagger.component.DaggerPostListComponent
import com.nate.moduleone.dagger.module.PostListModule
import com.nate.moduleone.databinding.ModuleOneActivityPostListBinding
import com.nate.moduleone.mvp.contract.PostListContract
import com.nate.moduleone.mvp.presenter.PostListPresenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class PostListActivity : SSBaseActivity<PostListPresenter>(), PostListContract.View {

  private lateinit var binding: ModuleOneActivityPostListBinding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerPostListComponent.builder().sSAppComponent(ssAppComponent).postListModule(PostListModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ModuleOneActivityPostListBinding.inflate(layoutInflater)
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