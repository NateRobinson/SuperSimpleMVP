package com.nate.moduleone.dagger.component

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import com.nate.moduleone.dagger.module.UserModule

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.UserFragment

@FragmentScope
@Component(modules = [UserModule::class], dependencies = [SSAppComponent::class])
interface UserComponent {
  fun inject(fragment: UserFragment)
}
