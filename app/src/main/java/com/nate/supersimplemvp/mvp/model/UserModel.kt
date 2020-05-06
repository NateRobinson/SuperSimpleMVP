package com.nate.supersimplemvp.mvp.model

import com.nate.ssmvp.dagger.scope.FragmentScope
import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel
import com.nate.supersimplemvp.mvp.contract.UserContract
import javax.inject.Inject

/**
 * Created by Nate on 2020/5/5
 */
@FragmentScope
class UserModel @Inject constructor(repositoryManager: SSIRepositoryManager) : SSBaseModel(repositoryManager), UserContract.Model {
  override fun onDestroy() {
    super.onDestroy()
  }
}