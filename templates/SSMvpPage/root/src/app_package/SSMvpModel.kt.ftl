package ${modelPackageName}

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

<#if needActivity && needFragment>
import com.nate.ssmvp.dagger.scope.ActivityScope
<#elseif needActivity>
import com.nate.ssmvp.dagger.scope.ActivityScope
<#elseif needFragment>
import com.nate.ssmvp.dagger.scope.FragmentScope
</#if>
import javax.inject.Inject

import ${contractPackageName}.${pageName}Contract

<#if needActivity && needFragment>
@ActivityScope
<#elseif needActivity>
@ActivityScope
<#elseif needFragment>
@FragmentScope
</#if>
class ${pageName}Model
@Inject
constructor(repositoryManager: SSIRepositoryManager) : SSBaseModel(repositoryManager), ${pageName}Contract.Model{

    override fun onDestroy() {
      super.onDestroy()
    }
}
