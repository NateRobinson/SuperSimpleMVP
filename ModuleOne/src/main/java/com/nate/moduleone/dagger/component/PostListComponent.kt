package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.PostListModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.PostListActivity
import dagger.Component

@ActivityScope
@Component(modules = [PostListModule::class],dependencies = [SSAppComponent::class])
interface PostListComponent {
  fun inject(activity:PostListActivity)
}