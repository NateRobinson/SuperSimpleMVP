module.exports = (packageName, pageName, lowerFirstPageName, upperModuleName, daggerRootName) => {
  return `package ${packageName}.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import ${packageName}.databinding.${upperModuleName}Fragment${pageName}Binding
import ${packageName}.${daggerRootName}.component.Dagger${pageName}Component
import ${packageName}.${daggerRootName}.module.${pageName}Module
import ${packageName}.mvp.contract.${pageName}Contract
import ${packageName}.mvp.presenter.${pageName}Presenter

class ${pageName}Fragment : SSBaseFragment<${pageName}Presenter>(), ${pageName}Contract.View{
  private lateinit var binding: ${upperModuleName}Fragment${pageName}Binding
  companion object {
    fun newInstance():${pageName}Fragment {
      val fragment = ${pageName}Fragment()
      return fragment
    }
  }
  override fun setupFragmentComponent(ssAppComponent: SSAppComponent) {
      Dagger${pageName}Component //如找不到该类,请编译一下项目
          .builder()
          .sSAppComponent(ssAppComponent)
          .${lowerFirstPageName}Module(${pageName}Module(this))
          .build()
          .inject(this)
  }

  override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
    binding = ${upperModuleName}Fragment${pageName}Binding.inflate(inflater)
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
}`;
};
