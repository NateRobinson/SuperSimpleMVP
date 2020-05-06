package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.UserContract
import com.nate.moduleone.mvp.model.UserModel

@Module
//构建UserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class UserModule(private val view: UserContract.View) {
  @FragmentScope
  @Provides
  fun provideUserView(): UserContract.View {
    return this.view
  }

  @FragmentScope
  @Provides
  fun provideUserModel(model: UserModel): UserContract.Model {
    return model
  }
}
