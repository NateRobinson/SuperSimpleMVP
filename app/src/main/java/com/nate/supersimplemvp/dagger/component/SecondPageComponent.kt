package com.nate.supersimplemvp.dagger.component

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import com.nate.supersimplemvp.dagger.module.SecondPageModule

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.supersimplemvp.mvp.ui.activity.SecondPageActivity

@ActivityScope
@Component(modules = [SecondPageModule::class], dependencies = [SSAppComponent::class])
interface SecondPageComponent {
  fun inject(activity: SecondPageActivity)
}
