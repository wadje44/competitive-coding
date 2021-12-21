const logger = require('../config/logger');
const db = require('../config/db-config');
const constants = require('../constants/constants.json');
const DatabaseQueryError = require('../exception/DatabaseQueryError');

const addQueryFilter = (
  baseQuery,
  limit,
  offset,
  message,
) => {
  if (!baseQuery) {
    throw new DatabaseQueryError('addQueryFilter', 'invalid baseQuery argument');
  }
  let query = baseQuery;
  if (limit) {
    query = query.limit(limit);
  }
  if (offset) {
    query = query.offset(offset);
  }
  if (message) {
    query = query.filter('region', '=', region);
  }
  return query;
};

const runQuery = async (query) => {
  if (!query) {
    throw new DatabaseQueryError('runQuery', 'invalid query argument');
  }
  try {
    const [queryResult] = await db.runQuery(query, {
      gaxOptions:
        { maxRetries: constants.REQUEST_RETRY_LIMIT },
    });
    return queryResult;
  } catch (error) {
    throw new DatabaseQueryError('runQuery', error.stack);
  }
};

const addMessage = async (message) => {
  const namespace = constants.NAMESPACE;
  const kind = constants.KIND;

  const key = db.key({ namespace, path: [kind] });
  const record = {
    key: key,
    data: {
      message,
      createDatetime: new Date(),
    },
  };
  const saveResponse = await db.save(record);

  return saveResponse;
};

const getMessages = async (offset, limit) => {
  const namespace = constants.NAMESPACE;
  const kind = constants.KIND;

  const baseQuery = db.createQuery(namespace, kind);
  const query = addQueryFilter(
    baseQuery,
    limit,
    offset,
  );

  const messages = await runQuery(query);
  // Extract hidden key
  messages.map(message => {
    message.id = parseInt(message[db.KEY].id);
    return message;
  })

  return messages;
};

const getMessageById = async (messageId) => {
  const namespace = constants.NAMESPACE;
  const kind = constants.KIND;

  const query = db.createQuery(namespace, kind)
    .filter('__key__', '=', db.key({ namespace, path: [kind, messageId] }));
  const messages = await runQuery(query);

  return messages;
};

const deleteMessageById = async (messageId) => {
  const namespace = constants.NAMESPACE;
  const kind = constants.KIND;

  const kindKey = db.key({ namespace, path: [kind, messageId] });
  const deleteResponse = await db.delete(kindKey);

  return deleteResponse;
};

module.exports = {
  addMessage,
  getMessages,
  getMessageById,
  deleteMessageById,
  runQuery,
  addQueryFilter,
};
