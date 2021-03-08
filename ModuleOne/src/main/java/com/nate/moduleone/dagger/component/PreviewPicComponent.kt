package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.PreviewPicModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.PreviewPicActivity
import dagger.Component

@ActivityScope
@Component(modules = [PreviewPicModule::class],dependencies = [SSAppComponent::class])
interface PreviewPicComponent {
  fun inject(activity:PreviewPicActivity)
}