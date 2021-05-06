module.exports = (packageName, pageName, pageMode, daggerRootName) => {
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

  return `package ${packageName}.${daggerRootName}.module

${importPart()}
import dagger.Module
import dagger.Provides
import ${packageName}.mvp.contract.${pageName}Contract
import ${packageName}.mvp.model.${pageName}Model

@Module
//构建 ${pageName}Module 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 presenter
class ${pageName}Module(private val view: ${pageName}Contract.View) {
  ${scopePart()}
  @Provides
  fun provide${pageName}View(): ${pageName}Contract.View{
    return this.view
  }

  ${scopePart()}
  @Provides
  fun provide${pageName}Model(model:${pageName}Model): ${pageName}Contract.Model{
    return model
  }
}`;
};
