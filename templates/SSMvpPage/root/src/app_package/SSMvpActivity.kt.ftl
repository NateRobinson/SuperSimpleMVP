package ${ativityPackageName}

import android.os.Bundle
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import ${packageName}.databinding.Activity${pageName}Binding
import ${componentPackageName}.Dagger${pageName}Component
import ${moudlePackageName}.${pageName}Module
import ${contractPackageName}.${pageName}Contract
import ${presenterPackageName}.${pageName}Presenter

class ${pageName}Activity : SSBaseActivity<${pageName}Presenter>() , ${pageName}Contract.View {

    private lateinit var binding: Activity${pageName}Binding

    override fun setupActivityComponent(ssAppComponent:SSAppComponent) {
        Dagger${pageName}Component
                .builder()
                .sSAppComponent(ssAppComponent)
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(${pageName}Module(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState:Bundle?):Int {
      binding = Activity${pageName}Binding.inflate(layoutInflater)
      setContentView(binding.root)
      return 0
    }

    override fun initData(savedInstanceState:Bundle?) {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage(message:String) {
        ToastUtils.showShort(message)
    }
}
