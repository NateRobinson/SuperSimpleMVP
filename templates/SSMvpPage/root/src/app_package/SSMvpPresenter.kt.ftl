package ${presenterPackageName}

import android.app.Application

<#if needActivity && needFragment>
import com.nate.ssmvp.dagger.scope.ActivityScope
<#elseif needActivity>
import com.nate.ssmvp.dagger.scope.ActivityScope
<#elseif needFragment>
import com.nate.ssmvp.dagger.scope.FragmentScope
</#if>
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import ${contractPackageName}.${pageName}Contract

<#if needActivity && needFragment>
@ActivityScope
<#elseif needActivity>
@ActivityScope
<#elseif needFragment>
@FragmentScope
</#if>
class ${pageName}Presenter
@Inject
constructor(model: ${pageName}Contract.Model, rootView: ${pageName}Contract.View) :
SSBasePresenter<${pageName}Contract.Model, ${pageName}Contract.View>(model,rootView) {
    
    @Inject
    lateinit var mApplication:Application
    @Inject
    lateinit var mErrorHandler:RxErrorHandler
    @Inject
    lateinit var mExecutorService: ExecutorService


    override fun onDestroy() {
          super.onDestroy();
    }
}
