package com.nate.moduleone.dagger.component

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import com.nate.moduleone.dagger.module.ModuleOneMainModule

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.ModuleOneMainActivity

@ActivityScope
@Component(modules = [ModuleOneMainModule::class], dependencies = [SSAppComponent::class])
interface ModuleOneMainComponent {
  fun inject(activity: ModuleOneMainActivity)
}
