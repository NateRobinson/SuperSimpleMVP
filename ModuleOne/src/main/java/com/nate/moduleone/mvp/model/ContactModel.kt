package com.nate.moduleone.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

import com.nate.ssmvp.dagger.scope.ActivityScope
import javax.inject.Inject

import com.nate.moduleone.mvp.contract.ContactContract

@ActivityScope
class ContactModel
@Inject constructor(repositoryManager: SSIRepositoryManager) : SSBaseModel(repositoryManager), ContactContract.Model {

  override fun onDestroy() {
    super.onDestroy()
  }
}
