package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.ActivityScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.PostListContract
import com.nate.moduleone.mvp.model.PostListModel

@Module
//构建 PostListModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class PostListModule(private val view: PostListContract.View) {
  @ActivityScope
  @Provides
  fun providePostListView(): PostListContract.View{
    return this.view
  }

  @ActivityScope
  @Provides
  fun providePostListModel(model:PostListModel): PostListContract.Model{
    return model
  }
}