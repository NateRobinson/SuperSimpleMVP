package ${fragmentPackageName}

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nate.ssmvp.base.SSBaseFragment
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.blankj.utilcode.util.ToastUtils
import ${packageName}.databinding.Fragment${pageName}Binding
import ${componentPackageName}.Dagger${pageName}Component
import ${moudlePackageName}.${pageName}Module
import ${contractPackageName}.${pageName}Contract
import ${presenterPackageName}.${pageName}Presenter

class ${pageName}Fragment : SSBaseFragment<${pageName}Presenter>() , ${pageName}Contract.View{
    private lateinit var binding: Fragment${pageName}Binding
    companion object {
      fun newInstance():${pageName}Fragment {
        val fragment = ${pageName}Fragment()
        return fragment
      }
    }
    override fun setupFragmentComponent(ssAppComponent:SSAppComponent) {
        Dagger${pageName}Component //如找不到该类,请编译一下项目
                .builder()
                .sSAppComponent(ssAppComponent)
                .${extractLetters(pageName[0]?lower_case)}${pageName?substring(1,pageName?length)}Module(${pageName}Module(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        binding = Fragment${pageName}Binding.inflate(inflater)
        return binding.root
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
