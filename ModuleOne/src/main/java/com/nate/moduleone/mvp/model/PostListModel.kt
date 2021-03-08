package com.nate.moduleone.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

import com.nate.ssmvp.dagger.scope.ActivityScope
import javax.inject.Inject
import com.nate.moduleone.mvp.contract.PostListContract

@ActivityScope
class PostListModel
@Inject
constructor(repositoryManager: SSIRepositoryManager): SSBaseModel(repositoryManager), PostListContract.Model{

  override fun onDestroy() {
    super.onDestroy()
  }
}