package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.PicModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.PicFragment
import dagger.Component

@FragmentScope
@Component(modules = [PicModule::class],dependencies = [SSAppComponent::class])
interface PicComponent {
  fun inject(fragment:PicFragment)
}