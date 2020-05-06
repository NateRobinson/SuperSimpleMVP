package com.nate.moduleone.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import com.nate.moduleone.databinding.FragmentBookBinding
import com.nate.moduleone.dagger.component.DaggerBookComponent
import com.nate.moduleone.dagger.module.BookModule
import com.nate.moduleone.mvp.contract.BookContract
import com.nate.moduleone.mvp.presenter.BookPresenter

class BookFragment : SSBaseFragment<BookPresenter>(), BookContract.View {
  private lateinit var binding: FragmentBookBinding

  companion object {
    fun newInstance(): BookFragment {
      val fragment = BookFragment()
      return fragment
    }
  }

  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
    DaggerBookComponent //如找不到该类,请编译一下项目
      .builder().sSAppComponent(ssAppComponent).bookModule(BookModule(this)).build().inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = FragmentBookBinding.inflate(inflater)
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
