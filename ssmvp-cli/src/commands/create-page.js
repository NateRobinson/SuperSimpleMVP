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

const filePath = (dir, file) => {
  return path.join(dir, file);
};

exports.run = async ({}) => {
  const dir = process.cwd();
  // check current package name
  // under the `/src/java/main` folder
  if (dir.indexOf('/src/main/java/') === -1) {
    printError('You are in wrong folder, please check the folder.');
    process.exit(0);
  }

  const currentPackageName = dir.split('/src/main/java/')[1].replace(/\//g, '.');

  // check the mvp folder with current package name
  const mvpFolder = '/mvp';
  if (!fs.existsSync(filePath(dir, mvpFolder))) {
    printError('Please make sure you have a mvp folder under the package!');
    process.exit(0);
  }

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

  const upperCasePageName = pageName.slice(0, 1).toUpperCase() + pageName.slice(1);
  const lowerCasePageName = pageName.slice(0, 1).toLowerCase() + pageName.slice(1);
  const activityOrFragmentFile =
    pageMode === 'only activity'
      ? `/mvp/ui/activity/${upperCasePageName}Activity.kt`
      : `/mvp/ui/fragment/${upperCasePageName}Fragment.kt`;
  const componentFile = `/dagger/component/${upperCasePageName}Component.kt`;
  const contractFile = `/mvp/contract/${upperCasePageName}Contract.kt`;
  const modelFile = `/mvp/model/${upperCasePageName}Model.kt`;
  const moduleFile = `/dagger/module/${upperCasePageName}Module.kt`;
  const presenterFile = `/mvp/presenter/${upperCasePageName}Presenter.kt`;
  let size = currentPackageName.split('.').length;
  let pathPart = '';
  while (size > 0) {
    size--;
    pathPart += '../';
  }
  const layoutFile = `${pathPart}../res/layout/${
    pageMode === 'only activity' ? 'activity' : 'fragment'
  }_${lowerCasePageName.replace(/([A-Z])/g, '_$1').toLowerCase()}.xml`;

  // check all files is exit before create them
  if (fs.existsSync(filePath(dir, activityOrFragmentFile))) {
    printError('The Activity or Fragment file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, componentFile))) {
    printError('The Component file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, contractFile))) {
    printError('The Contract file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, modelFile))) {
    printError('The Model file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, moduleFile))) {
    printError('The Module file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, presenterFile))) {
    printError('The Presenter file is already exist, please check!');
    process.exit(0);
  }
  if (fs.existsSync(filePath(dir, layoutFile))) {
    printError('The layout xml file is already exist, please check!');
    process.exit(0);
  }

  // create a activity file
  if (pageMode === 'only activity') {
    const activityFolder = path.join(dir, '/mvp/ui/activity');
    if (!fs.existsSync(activityFolder)) {
      fs.mkdirSync(activityFolder);
    }
    fs.writeFileSync(
      filePath(dir, activityOrFragmentFile),
      activityTmp(currentPackageName, upperCasePageName, lowerCasePageName)
    );
  }

  // create fragment file
  if (pageMode === 'only fragment') {
    const fragmentFolder = path.join(dir, '/mvp/ui/fragment');
    if (!fs.existsSync(fragmentFolder)) {
      fs.mkdirSync(fragmentFolder);
    }
    fs.writeFileSync(
      filePath(dir, activityOrFragmentFile),
      fragmentTmp(currentPackageName, upperCasePageName, lowerCasePageName)
    );
  }

  // create component file
  const componentFolder = path.join(dir, '/dagger/component');
  if (!fs.existsSync(componentFolder)) {
    fs.mkdirSync(componentFolder);
  }
  fs.writeFileSync(
    filePath(dir, componentFile),
    componentTmp(currentPackageName, upperCasePageName, pageMode)
  );

  // create contract file
  const contractFolder = path.join(dir, '/mvp/contract');
  if (!fs.existsSync(contractFolder)) {
    fs.mkdirSync(contractFolder);
  }
  fs.writeFileSync(filePath(dir, contractFile), contractTmp(currentPackageName, upperCasePageName));

  // create model file
  const modelFolder = path.join(dir, '/mvp/model');
  if (!fs.existsSync(modelFolder)) {
    fs.mkdirSync(modelFolder);
  }
  fs.writeFileSync(
    filePath(dir, modelFile),
    modelTmp(currentPackageName, upperCasePageName, pageMode)
  );

  // create moduleTmp file
  const moduleFolder = path.join(dir, '/dagger/module');
  if (!fs.existsSync(moduleFolder)) {
    fs.mkdirSync(moduleFolder);
  }
  fs.writeFileSync(
    filePath(dir, moduleFile),
    moduleTmp(currentPackageName, upperCasePageName, pageMode)
  );

  // create presenter file
  const presenterFolder = path.join(dir, '/mvp/presenter');
  if (!fs.existsSync(presenterFolder)) {
    fs.mkdirSync(presenterFolder);
  }
  fs.writeFileSync(
    filePath(dir, presenterFile),
    presenterTmp(currentPackageName, upperCasePageName, pageMode)
  );

  // create simple layout file
  const layoutFolder = path.join(dir, `${pathPart}../res/layout`);
  if (!fs.existsSync(layoutFolder)) {
    fs.mkdirSync(layoutFolder);
  }
  fs.writeFileSync(filePath(dir, layoutFile), simpleTmp());

  printSuccess('The page files was created success!');
  process.exit(0);
};
