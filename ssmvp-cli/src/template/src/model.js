module.exports = (packageName, pageName, pageMode) => {
  const importPart = () => {
    if (pageMode === 'activity+fragment') {
      return 'import com.nate.ssmvp.dagger.scope.ActivityScope';
    } else if (pageMode === 'only activity') {
      return 'import com.nate.ssmvp.dagger.scope.ActivityScope';
    } else if (pageMode === 'only fragment') {
      return 'import com.nate.ssmvp.dagger.scope.FragmentScope';
    }
  };

  const scopePart = () => {
    if (pageMode === 'activity+fragment') {
      return '@ActivityScope';
    } else if (pageMode === 'only activity') {
      return '@ActivityScope';
    } else if (pageMode === 'only fragment') {
      return '@FragmentScope';
    }
  };

  return `package ${packageName}.mvp.model

import com.nate.ssmvp.data.SSIRepositoryManager
import com.nate.ssmvp.mvp.SSBaseModel

${importPart()}
import javax.inject.Inject
import ${packageName}.mvp.contract.${pageName}Contract

${scopePart()}
class ${pageName}Model
@Inject
constructor(repositoryManager: SSIRepositoryManager): SSBaseModel(repositoryManager), ${pageName}Contract.Model{

  override fun onDestroy() {
    super.onDestroy()
  }
}`;
};
