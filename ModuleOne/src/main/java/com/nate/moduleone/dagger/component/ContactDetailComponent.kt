package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.ContactDetailModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.moduleone.mvp.ui.fragment.ContactDetailFragment
      
import dagger.Component

@FragmentScope
@Component(modules = [ContactDetailModule::class],dependencies = [SSAppComponent::class])
interface ContactDetailComponent {
  fun inject(fragment:ContactDetailFragment)
}