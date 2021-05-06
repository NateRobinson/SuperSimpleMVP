module.exports = (packageName, pageName, pageMode, daggerRootName) => {
  const importPart = () => {
    if (pageMode === 'activity+fragment') {
      return `import com.nate.ssmvp.dagger.scope.ActivityScope
import ${packageName}.mvp.ui.activity.${pageName}Activity
import ${packageName}.mvp.ui.fragment.${pageName}Fragment`;
    } else if (pageMode === 'only activity') {
      return `import com.nate.ssmvp.dagger.scope.ActivityScope
import ${packageName}.mvp.ui.activity.${pageName}Activity`;
    } else if (pageMode === 'only fragment') {
      return `import com.nate.ssmvp.dagger.scope.FragmentScope
import ${packageName}.mvp.ui.fragment.${pageName}Fragment`;
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

  const injectPart = () => {
    if (pageMode === 'activity+fragment') {
      return `fun inject(activity:${pageName}Activity)
      fun inject(fragment:${pageName}Fragment)`;
    } else if (pageMode === 'only activity') {
      return `fun inject(activity:${pageName}Activity)`;
    } else if (pageMode === 'only fragment') {
      return `fun inject(fragment:${pageName}Fragment)`;
    }
  };

  return `package ${packageName}.${daggerRootName}.component

import ${packageName}.${daggerRootName}.module.${pageName}Module
import com.nate.ssmvp.dagger.component.SSAppComponent
${importPart()}
import dagger.Component

${scopePart()}
@Component(modules = [${pageName}Module::class],dependencies = [SSAppComponent::class])
interface ${pageName}Component {
  ${injectPart()}
}`;
};
