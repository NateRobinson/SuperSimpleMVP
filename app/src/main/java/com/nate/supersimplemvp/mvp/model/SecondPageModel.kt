package com.nate.supersimplemvp.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

import com.nate.ssmvp.dagger.scope.ActivityScope
import javax.inject.Inject

import com.nate.supersimplemvp.mvp.contract.SecondPageContract

@ActivityScope
class SecondPageModel
@Inject constructor(repositoryManager: SSIRepositoryManager) : SSBaseModel(repositoryManager), SecondPageContract.Model {

  override fun onDestroy() {
    super.onDestroy()
  }
}
