package com.nate.moduleone.dagger.component

import dagger.Component
import com.nate.ssmvp.dagger.component.SSAppComponent

import com.nate.moduleone.dagger.module.BookModule

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.BookFragment

@FragmentScope
@Component(modules = [BookModule::class], dependencies = [SSAppComponent::class])
interface BookComponent {
  fun inject(fragment: BookFragment)
}
