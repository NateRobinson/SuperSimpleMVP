const shell = require("shelljs");
const { symbols } = require("./ui");
/**
 * return json string with indent
 * @param {object} json object
 * @returns {string} stringified json
 */
function prettyStringify(json, { replacer = null, space = 4 } = {}) {
  return JSON.stringify(json, replacer, space);
}

function print(...args) {
  shell.echo.apply(null, args);
}

function printInfo(...args) {
  print.apply(null, [symbols.info, ...args]);
}

function printSuccess(...args) {
  print.apply(null, [symbols.success, ...args]);
}

function printWarning(...args) {
  print.apply(null, [symbols.warning, ...args]);
}

function printError(...args) {
  if (args.length && args[0] instanceof Error) {
    args[0] = args[0].message;
  }

  print.apply(null, [symbols.error, ...args]);
}

module.exports = {
  print,
  printError,
  printInfo,
  printSuccess,
  printWarning,
  prettyStringify,
};
