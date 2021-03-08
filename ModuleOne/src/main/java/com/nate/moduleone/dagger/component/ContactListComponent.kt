package com.nate.moduleone.dagger.component

import com.nate.moduleone.dagger.module.ContactListModule
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.moduleone.mvp.ui.activity.ContactListActivity
      
import dagger.Component

@ActivityScope
@Component(modules = [ContactListModule::class],dependencies = [SSAppComponent::class])
interface ContactListComponent {
	fun inject(activity:ContactListActivity)
}