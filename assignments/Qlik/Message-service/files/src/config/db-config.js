const { Datastore } = require('@google-cloud/datastore');
const logger = require('./logger');

const env = process.env.NODE_ENV;

let config = {
  projectId: process.env.PROJECT_ID,
};

if (env === 'local-development') {
  config = {
    projectId: process.env.PROJECT_ID,
    port: process.env.DATASTORE_PORT,
    apiEndpoint: process.env.DATASTORE_HOST,
  };
}

const createDatastoreClient = (configuration) => {
  let datastore;
  try {
    datastore = new Datastore(configuration);
  } catch (err) {
    logger.error('Error occured while connecting to the database ::', err);
  }
  return datastore;
};
module.exports = createDatastoreClient(config);
