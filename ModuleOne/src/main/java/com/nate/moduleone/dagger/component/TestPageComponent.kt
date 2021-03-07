package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.TestPageModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.TestPageActivity
      
import dagger.Component

@ActivityScope
@Component(modules = [TestPageModule::class],dependencies = [SSAppComponent::class])
interface TestPageComponent {
	fun inject(activity:TestPageActivity)
}