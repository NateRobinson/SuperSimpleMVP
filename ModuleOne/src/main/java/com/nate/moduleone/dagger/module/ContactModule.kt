package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.ContactContract
import com.nate.moduleone.mvp.model.ContactModel

@Module
//构建ContactModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ContactModule(private val view: ContactContract.View) {
  @ActivityScope
  @Provides
  fun provideContactView(): ContactContract.View {
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideContactModel(model: ContactModel): ContactContract.Model {
    return model
  }
}
