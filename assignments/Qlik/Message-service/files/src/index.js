const express = require('express');
const cors = require('cors');
const swaggerJSDoc = require('swagger-jsdoc');

// To create automatic swagger docs
const swaggerUi = require('swagger-ui-express');

const {
  ValidationError,
} = require("express-json-validator-middleware");

const app = express();
app.use(cors());
app.options('*', cors());

const health = require('./routes/health');
const palindrome = require('./routes/palindrome');
const logger = require('./config/logger');

// Get all swagger definitions to be showed in swagger docs
const swaggerDefinition = require('./config/swagger-definition');

// Common exceptional handler to be used by all layers
const errorHandler = require('./exception/ErrorHandler');

/*
* NODE_ENV is not set, then execution should be treated as local developement
* For production, production env can be used
*/
process.env.NODE_ENV ? process.env.NODE_ENV : 'local-development';


/*
* All requests are validated using json schema using middleware
*/
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

// Endpoint declaration for Swagger Docs
app.use('/docs', swaggerUi.serve);
app.get('/docs', swaggerUi.setup(swaggerSpec, { explorer: true }));

app.use(express.json());

app.use('/', health);
app.use('/', palindrome);

// To get auto generated swagger json which can be used on any swagger editor
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

// Setting up validation middleware
app.use(validationErrorMiddleware);

app.use(errorHandler);

module.exports = {
  app,
  logger,
};
