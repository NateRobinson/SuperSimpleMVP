package com.nate.moduleone.dagger.module

import com.nate.ssmvp.dagger.scope.FragmentScope
import dagger.Module
import dagger.Provides
import com.nate.moduleone.mvp.contract.PicDetailContract
import com.nate.moduleone.mvp.model.PicDetailModel

@Module
//构建 PicDetailModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class PicDetailModule(private val view: PicDetailContract.View) {
  @FragmentScope
  @Provides
  fun providePicDetailView(): PicDetailContract.View{
    return this.view
  }

  @FragmentScope
  @Provides
  fun providePicDetailModel(model:PicDetailModel): PicDetailContract.Model{
    return model
  }
}