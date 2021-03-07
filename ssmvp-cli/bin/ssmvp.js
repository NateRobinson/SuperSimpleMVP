#!/usr/bin/env node
// bin 文件需要加上上面的头，否则脚本不会再 Node 环境下执行

(async () => {
  const commander = require("commander");
  const last = require("lodash/last");

  const { version } = require("../package.json");
  const create = require("../src/commands/create-page");
  const { print } = require("../src/utils");

  const program = new commander.Command();
  program.passCommandToAction(false);
  program.version(version);

  const handleUnexpectedError = (error) => {
    print();
    console.error(error);
    process.exit(1);
  };

  process
    .on("uncaughtException", handleUnexpectedError)
    .on("unhandledRejection", handleUnexpectedError);

  const parseOptions = (handler) => async (...args) => {
    const commonOptions = program.opts();
    const options = last(args);
    const allOptions = { ...commonOptions, ...options };
    await handler(...args.slice(0, args.length - 1), allOptions);
  };

  program
    .command("create-page")
    .description("Create Android MVP page by ssmvp cli")
    .action(parseOptions(create.run));

  program.on("--help", () => {
    console.log("");
  });

  program.parse(process.argv);

  if (program.args.length === 0) {
    program.help();
  }
})();
