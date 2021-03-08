package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.ContactListContract
import com.nate.moduleone.mvp.model.ContactListModel

@Module
//构建 ContactListModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class ContactListModule(private val view: ContactListContract.View) {
  @ActivityScope
  @Provides
  fun provideContactListView(): ContactListContract.View{
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideContactListModel(model:ContactListModel): ContactListContract.Model{
    return model
  }
}