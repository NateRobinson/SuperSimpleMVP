package com.nate.moduleone.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

import com.nate.ssmvp.dagger.scope.FragmentScope
import javax.inject.Inject
import com.nate.moduleone.mvp.contract.PicContract

@FragmentScope
class PicModel
@Inject
constructor(repositoryManager: SSIRepositoryManager): SSBaseModel(repositoryManager), PicContract.Model{

  override fun onDestroy() {
    super.onDestroy()
  }
}