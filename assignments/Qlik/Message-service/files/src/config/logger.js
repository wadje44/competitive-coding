const { transports, createLogger, format } = require('winston');
const { LoggingWinston } = require('@google-cloud/logging-winston');

const consoleLogger = new transports.Console({
  format: format.combine(
    format.colorize(),
    format.simple(),
  ),
});

// To send logs on common gcp logging service stackdriver
const stackdriverLogger = new LoggingWinston({
  projectId: process.env.PROJECT_ID,
  serviceContext: {
    service: 'message-service',
  },
});

module.exports = createLogger({
  format: format.combine(
    format.timestamp(),
    format.json(),
  ),
  transports: ((env) => {
    // For running locally, only console log is used
    if (env === 'local-development') {
      return [consoleLogger];
    }

    return [
      consoleLogger,
      stackdriverLogger,
    ];
  })(process.env.NODE_ENV),
});
