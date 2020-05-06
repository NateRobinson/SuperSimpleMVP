package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.ModuleOneMainContract
import com.nate.moduleone.mvp.model.ModuleOneMainModel

@Module
//构建ModuleOneMainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ModuleOneMainModule(private val view: ModuleOneMainContract.View) {
  @ActivityScope
  @Provides
  fun provideModuleOneMainView(): ModuleOneMainContract.View {
    return this.view
  }

  @ActivityScope
  @Provides
  fun provideModuleOneMainModel(model: ModuleOneMainModel): ModuleOneMainContract.Model {
    return model
  }
}
