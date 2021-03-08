module.exports = (packageName, pageName, lowerFirstPageName, upperModuleName) => {
  return `package ${packageName}.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.ToastUtils
import ${packageName}.dagger.component.Dagger${pageName}Component
import ${packageName}.dagger.module.${pageName}Module
import ${packageName}.databinding.${upperModuleName}Activity${pageName}Binding
import ${packageName}.mvp.contract.${pageName}Contract
import ${packageName}.mvp.presenter.${pageName}Presenter
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent

class ${pageName}Activity : SSBaseActivity<${pageName}Presenter>(), ${pageName}Contract.View {

  private lateinit var binding: ${upperModuleName}Activity${pageName}Binding

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    Dagger${pageName}Component.builder().sSAppComponent(ssAppComponent).${lowerFirstPageName}Module(${pageName}Module(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ${upperModuleName}Activity${pageName}Binding.inflate(layoutInflater)
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
}`;
};
