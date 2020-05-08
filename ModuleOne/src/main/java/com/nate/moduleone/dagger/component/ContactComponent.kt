package com.nate.moduleone.dagger.component

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import com.nate.moduleone.dagger.module.ContactModule

import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.ContactActivity

@ActivityScope
@Component(modules = [ContactModule::class], dependencies = [SSAppComponent::class])
interface ContactComponent {
  fun inject(activity: ContactActivity)
}
