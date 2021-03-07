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

  return `package ${packageName}.mvp.presenter

import android.app.Application

${importPart()}
import com.nate.ssmvp.mvp.SSBasePresenter
import java.util.concurrent.ExecutorService
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import ${packageName}.mvp.contract.${pageName}Contract

${scopePart()}
class ${pageName}Presenter
@Inject
constructor(model: ${pageName}Contract.Model, rootView: ${pageName}Contract.View) :
SSBasePresenter<${pageName}Contract.Model, ${pageName}Contract.View>(model,rootView) {

  @Inject
  lateinit var mApplication:Application
  @Inject
  lateinit var mErrorHandler:RxErrorHandler
  @Inject
  lateinit var mExecutorService: ExecutorService

  override fun onDestroy() {
    super.onDestroy()
  }
}`;
};
