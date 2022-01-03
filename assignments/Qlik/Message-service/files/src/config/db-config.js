const { Datastore } = require('@google-cloud/datastore');
const logger = require('./logger');

const env = process.env.NODE_ENV;

// As only one datastore instance is alloted for one gcp project
// only project id is sufficient for connection
let config = {
  projectId: process.env.PROJECT_ID,
};

// For local environment, connect to emulator
// Emulator port and url needs to be added in config
if (env === 'local-development') {
  config = {
    projectId: process.env.PROJECT_ID,
    port: process.env.DATASTORE_PORT,
    apiEndpoint: process.env.DATASTORE_HOST,
  };
}

// Creating reusable client object to connect databse
const createDatastoreClient = (configuration) => {
  let datastore;
  try {
    datastore = new Datastore(configuration);
  } catch (err) {
    logger.error('Error occured while connecting to the database ::', err);
  }
  return datastore;
};

// Exporting object instead of method as creating client is one time job
module.exports = createDatastoreClient(config);
