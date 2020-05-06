package ${componentPackageName}

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import ${moudlePackageName}.${pageName}Module

<#if needActivity && needFragment>
import com.nate.ssmvp.dagger.scope.ActivityScope
import ${ativityPackageName}.${pageName}Activity
import ${fragmentPackageName}.${pageName}Fragment
<#elseif needActivity>
import com.nate.ssmvp.dagger.scope.ActivityScope
import ${ativityPackageName}.${pageName}Activity
<#elseif needFragment>
import com.nate.ssmvp.dagger.scope.FragmentScope
import ${fragmentPackageName}.${pageName}Fragment
</#if>

<#if needActivity && needFragment>
@ActivityScope
<#elseif needActivity>
@ActivityScope
<#elseif needFragment>
@FragmentScope
</#if>
@Component(modules = [${pageName}Module::class],dependencies = [SSAppComponent::class])
interface ${pageName}Component {
  <#if needActivity && needFragment>
	fun inject(activity:${pageName}Activity)
	fun inject(fragment:${pageName}Fragment)
  <#elseif needActivity || needFragment>
    fun inject(<#if needFragment>fragment:${pageName}Fragment<#else>activity:${pageName}Activity</#if>)
  </#if>
}
