package com.nate.supersimplemvp.dagger.component

import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.supersimplemvp.mvp.ui.activity.MainActivity
import com.nate.supersimplemvp.dagger.module.MainModule
import dagger.Component

/**
 * Created by Nate on 2020/5/5
 */
@ActivityScope
@Component(modules = [MainModule::class], dependencies = [SSAppComponent::class])
interface MainComponent {
  fun inject(activity: MainActivity)
}
