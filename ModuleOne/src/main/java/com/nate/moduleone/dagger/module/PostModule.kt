package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.PostContract
import com.nate.moduleone.mvp.model.PostModel

@Module
//构建 PostModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class PostModule(private val view: PostContract.View) {
  @ActivityScope
  @Provides
  fun providePostView(): PostContract.View{
    return this.view
  }

  @ActivityScope
  @Provides
  fun providePostModel(model:PostModel): PostContract.Model{
    return model
  }
}