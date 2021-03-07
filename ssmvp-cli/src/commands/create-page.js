const inquirer = require('inquirer');
const { print, printError, printSuccess } = require('../utils');
const path = require('path');
const fs = require('fs');
const activityTmp = require('../template/src/activity');
const componentTmp = require('../template/src/component');
const contractTmp = require('../template/src/contract');
const fragmentTmp = require('../template/src/fragment');
const modelTmp = require('../template/src/model');
const moduleTmp = require('../template/src/module');
const presenterTmp = require('../template/src/presenter');
const simpleTmp = require('../template/res/simple');
const { forIn } = require('lodash');

const validateThePageName = (name) => {
  if (!name || !name.trim()) {
    return 'Please input the page name.';
  }
  if (
    name.trim().toLowerCase().endsWith('activity') ||
    name.trim().toLowerCase().endsWith('fragment')
  ) {
    return 'The page name should not end with activity or fragment.';
  }
  return true;
};

async function getQuestions({}) {
  const questions = [
    {
      type: 'input',
      name: 'pageName',
      message: `Please input the page name.`,
      validate: (name) => validateThePageName(name),
      default: '',
    },
    {
      type: 'list',
      name: 'pageType',
      message: `Please select the page type.`,
      default: 'only activity',
      choices: ['only activity', 'only fragment'],
    },
  ];
  return questions;
}

const askQuestions = async (defaultAnswers = {}) => {
  const questions = await getQuestions(defaultAnswers);
  return inquirer.prompt(questions);
};

// TODO: check all files is exit before create it
exports.run = async ({}) => {
  const dir = process.cwd();
  // check current package name
  // under the `/src/java/main` folder
  if (dir.indexOf('/src/main/java/') === -1) {
    printError('You are in wrong folder, please check the folder.');
    process.exit(0);
  }
  const currentPackageName = dir.split('/src/main/java/')[1].replace(/\//g, '.');

  const { confirmCreate } = await inquirer.prompt({
    type: 'confirm',
    name: 'confirmCreate',
    message: `Please confirm current package name is: (${currentPackageName})`,
    default: false,
  });

  if (!confirmCreate) {
    print('Aborted!');
    process.exit(0);
  }
  const answers = await askQuestions({});
  const { pageName, pageType: pageMode } = answers;

  if (pageMode === 'only activity') {
    // create a activity file
    const activityFolder = path.join(dir, '/mvp/ui/activity');
    if (!fs.existsSync(activityFolder)) {
      fs.mkdirSync(activityFolder);
    }
    fs.writeFileSync(
      path.join(
        dir,
        `/mvp/ui/activity/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Activity.kt`
      ),
      activityTmp(
        currentPackageName,
        pageName.slice(0, 1).toUpperCase() + pageName.slice(1),
        pageName.slice(0, 1).toLowerCase() + pageName.slice(1)
      )
    );
  }

  if (pageMode === 'only fragment') {
    // create fragment file
    const fragmentFolder = path.join(dir, '/mvp/ui/fragment');
    if (!fs.existsSync(fragmentFolder)) {
      fs.mkdirSync(fragmentFolder);
    }
    fs.writeFileSync(
      path.join(
        dir,
        `/mvp/ui/fragment/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Fragment.kt`
      ),
      fragmentTmp(
        currentPackageName,
        pageName.slice(0, 1).toUpperCase() + pageName.slice(1),
        pageName.slice(0, 1).toLowerCase() + pageName.slice(1)
      )
    );
  }

  // create component file
  const componentFolder = path.join(dir, '/dagger/component');
  if (!fs.existsSync(componentFolder)) {
    fs.mkdirSync(componentFolder);
  }
  fs.writeFileSync(
    path.join(
      dir,
      `/dagger/component/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Component.kt`
    ),
    componentTmp(
      currentPackageName,
      pageName.slice(0, 1).toUpperCase() + pageName.slice(1),
      pageMode
    )
  );

  // create contract file
  const contractFolder = path.join(dir, '/mvp/contract');
  if (!fs.existsSync(contractFolder)) {
    fs.mkdirSync(contractFolder);
  }
  fs.writeFileSync(
    path.join(
      dir,
      `/mvp/contract/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Contract.kt`
    ),
    contractTmp(currentPackageName, pageName.slice(0, 1).toUpperCase() + pageName.slice(1))
  );

  // create model file
  const modelFolder = path.join(dir, '/mvp/model');
  if (!fs.existsSync(modelFolder)) {
    fs.mkdirSync(modelFolder);
  }
  fs.writeFileSync(
    path.join(dir, `/mvp/model/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Model.kt`),
    modelTmp(currentPackageName, pageName.slice(0, 1).toUpperCase() + pageName.slice(1), pageMode)
  );

  // create moduleTmp file
  const moduleFolder = path.join(dir, '/dagger/module');
  if (!fs.existsSync(moduleFolder)) {
    fs.mkdirSync(moduleFolder);
  }
  fs.writeFileSync(
    path.join(
      dir,
      `/dagger/module/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Module.kt`
    ),
    moduleTmp(currentPackageName, pageName.slice(0, 1).toUpperCase() + pageName.slice(1), pageMode)
  );

  // create presenter file
  const presenterFolder = path.join(dir, '/mvp/presenter');
  if (!fs.existsSync(presenterFolder)) {
    fs.mkdirSync(presenterFolder);
  }
  fs.writeFileSync(
    path.join(
      dir,
      `/mvp/presenter/${pageName.slice(0, 1).toUpperCase() + pageName.slice(1)}Presenter.kt`
    ),
    presenterTmp(
      currentPackageName,
      pageName.slice(0, 1).toUpperCase() + pageName.slice(1),
      pageMode
    )
  );

  // create simple layout file
  let size = currentPackageName.split('.').length;
  let pathPart = '';
  while (size > 0) {
    size--;
    pathPart += '../';
  }
  const layoutFolder = path.join(dir, `${pathPart}../res/layout`);
  if (!fs.existsSync(layoutFolder)) {
    fs.mkdirSync(layoutFolder);
  }
  fs.writeFileSync(
    path.join(
      dir,
      `${pathPart}../res/layout/${pageMode === 'only activity' ? 'activity' : 'fragment'}_${(
        pageName.slice(0, 1).toLowerCase() + pageName.slice(1)
      )
        .replace(/([A-Z])/g, '_$1')
        .toLowerCase()}.xml`
    ),
    simpleTmp()
  );
  printSuccess('The page files was created success!');
  process.exit(0);
};
