package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.BookContract
import com.nate.moduleone.mvp.model.BookModel

@Module
//构建BookModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BookModule(private val view: BookContract.View) {
  @FragmentScope
  @Provides
  fun provideBookView(): BookContract.View {
    return this.view
  }

  @FragmentScope
  @Provides
  fun provideBookModel(model: BookModel): BookContract.Model {
    return model
  }
}
