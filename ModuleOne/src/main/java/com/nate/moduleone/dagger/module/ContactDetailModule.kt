package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.ContactDetailContract
import com.nate.moduleone.mvp.model.ContactDetailModel

@Module
//构建 ContactDetailModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class ContactDetailModule(private val view: ContactDetailContract.View) {
  @FragmentScope
  @Provides
  fun provideContactDetailView(): ContactDetailContract.View{
    return this.view
  }

  @FragmentScope
  @Provides
  fun provideContactDetailModel(model:ContactDetailModel): ContactDetailContract.Model{
    return model
  }
}