package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.PicDetailModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.PicDetailFragment
import dagger.Component

@FragmentScope
@Component(modules = [PicDetailModule::class],dependencies = [SSAppComponent::class])
interface PicDetailComponent {
  fun inject(fragment:PicDetailFragment)
}