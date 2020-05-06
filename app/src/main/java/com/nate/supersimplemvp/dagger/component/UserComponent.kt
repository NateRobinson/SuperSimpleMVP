package com.nate.supersimplemvp.dagger.component

import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.dagger.scope.ActivityScope
import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.supersimplemvp.mvp.ui.activity.MainActivity
import com.nate.supersimplemvp.dagger.module.MainModule
import com.nate.supersimplemvp.dagger.module.UserModule
import com.nate.supersimplemvp.mvp.ui.fragment.UserFragment
import dagger.Component

/**
 * Created by Nate on 2020/5/5
 */
@FragmentScope
@Component(modules = [UserModule::class], dependencies = [SSAppComponent::class])
interface UserComponent {
  fun inject(fragment: UserFragment)
}
