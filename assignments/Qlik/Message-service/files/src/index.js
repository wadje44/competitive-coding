const express = require('express');
const swaggerJSDoc = require('swagger-jsdoc');
const swaggerUi = require('swagger-ui-express');
const {
  ValidationError,
} = require("express-json-validator-middleware");

const app = express();

const health = require('./routes/health');
const palindrome = require('./routes/palindrome');
const logger = require('./config/logger');
const swaggerDefinition = require('./config/swagger-definition');
const errorHandler = require('./exception/ErrorHandler');


function validationErrorMiddleware(error, request, response, next) {
  if (response.headersSent) {
    return next(error);
  }

  const isValidationError = error instanceof ValidationError;
  if (!isValidationError) {
    return next(error);
  }

  response.status(400).json({
    errors: error.validationErrors,
  });

  next();
}

const options = {
  swaggerDefinition,
  apis: ['./routes/**/*.js'],
};

const swaggerSpec = swaggerJSDoc(options);

app.use('/docs', swaggerUi.serve);
app.get('/docs', swaggerUi.setup(swaggerSpec, { explorer: true }));

app.use(express.json());

app.use('/', health);
app.use('/', palindrome);

app.get('/swagger.json', (req, res) => {
  res.setHeader('Content-Type', 'application/json');
  res.send(swaggerSpec);
});


app.use((req, res) => {
  const response = {
    error: `Route ${req.url} Not found.`,
  };
  logger.error('Invalid URL:', response);
  return res.status(404)
    .send(response);
});

process
  .on('unhandledRejection', (reason, p) => {
    logger.error('Unhandled Rejection at Promise', reason, p);
  })
  .on('uncaughtException', (err) => {
    logger.error('app uncaughtException: ', err);
  });

app.use(validationErrorMiddleware);

app.use(errorHandler);

module.exports = {
  app,
  logger,
};
