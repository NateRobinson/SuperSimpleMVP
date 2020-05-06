package com.nate.supersimplemvp.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.supersimplemvp.mvp.contract.UserContract
import com.nate.supersimplemvp.mvp.model.UserModel
import dagger.Module
import dagger.Provides

/**
 * Created by Nate on 2020/5/5
 */
@Module
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