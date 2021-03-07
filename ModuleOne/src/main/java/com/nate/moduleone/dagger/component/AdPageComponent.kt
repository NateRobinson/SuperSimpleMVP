package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.AdPageModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.AdPageFragment
      
import dagger.Component

@FragmentScope
@Component(modules = [AdPageModule::class],dependencies = [SSAppComponent::class])
interface AdPageComponent {
	fun inject(fragment:AdPageFragment)
}