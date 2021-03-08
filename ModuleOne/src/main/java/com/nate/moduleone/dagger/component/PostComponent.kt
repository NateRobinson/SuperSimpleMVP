package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.PostModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.PostActivity
import dagger.Component

@ActivityScope
@Component(modules = [PostModule::class],dependencies = [SSAppComponent::class])
interface PostComponent {
  fun inject(activity:PostActivity)
}