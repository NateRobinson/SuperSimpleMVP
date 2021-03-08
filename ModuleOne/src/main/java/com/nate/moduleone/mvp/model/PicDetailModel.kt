package com.nate.moduleone.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

import com.nate.ssmvp.dagger.scope.FragmentScope
import javax.inject.Inject
import com.nate.moduleone.mvp.contract.PicDetailContract

@FragmentScope
class PicDetailModel
@Inject
constructor(repositoryManager: SSIRepositoryManager): SSBaseModel(repositoryManager), PicDetailContract.Model{

  override fun onDestroy() {
    super.onDestroy()
  }
}