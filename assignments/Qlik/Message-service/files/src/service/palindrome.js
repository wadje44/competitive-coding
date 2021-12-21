const logger = require('../config/logger');
const lib = require('../lib/palindrome');
const util = require('../util/common-util');
const NonFoundError = require('../exception/NonFoundError');

module.exports = {

  addMessage: async (reqBody) => {
    const addResponse = await lib.addMessage(reqBody.message);
    const messageId = parseInt(addResponse[0].mutationResults[0].key.path[0].id);
    logger.info(`Message saved: ${messageId}`);
    return {
      id: messageId,
      message: reqBody.message,
    };
  },

  getMessages: async (reqQueryParams) => {
    const page = reqQueryParams.page ? reqQueryParams.page : 1;
    const limit = reqQueryParams.limit ? reqQueryParams.limit : 500;
    const offset = util.getOffset(page, limit);

    const messageList = await lib.getMessages(offset, limit);

    logger.info(`Fetched number of messages :${messageList.length}`);
    const response = {
      count: messageList.length,
      items: messageList,
    };
    return response;
  },

  checkIfPalindrome: async (reqPathParams) => {
    const messageId = parseInt(reqPathParams.messageId);
    const messageDetails = await lib.getMessageById(messageId);
    if (messageDetails.length === 0) {
      const errorMessage = `Message not found for messageId: ${messageId}`;
      logger.error(errorMessage);
      throw new NonFoundError(`checkifPalindrome`, errorMessage);
    }
    const isPalindrome = util.checkIfPalindrome(messageDetails[0].message);
    return { isPalindrome };
  },

  deleteMessage: async (reqPathParams) => {
    const messageId = parseInt(reqPathParams.messageId);
    const deleteResponse = await lib.deleteMessageById(messageId);
    if (deleteResponse[0].indexUpdates === 0) {
      const errorMessage = `Message not found for messageId: ${messageId}`;
      logger.error(errorMessage);
      throw new NonFoundError(`checkifPalindrome`, errorMessage);
    }
    logger.info(`Deleted message with messageId:${messageId}`);
    return { deletedId: messageId };
  },
};
