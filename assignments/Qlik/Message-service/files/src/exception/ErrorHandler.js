const logger = require('../config/logger');

const errorHandler = (err, req, res, next) => {
  logger.error('Error calling api', { error: err.message });
  res.status(err.code || 500).send({
    error: err,
    message: err.message,
    code: err.code,
  });
  next();
};

module.exports = errorHandler;
