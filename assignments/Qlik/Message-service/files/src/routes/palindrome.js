const express = require('express');
const { Validator } = require("express-json-validator-middleware");
const { StatusCodes } = require('http-status-codes');
const service = require('../service/palindrome');
const logger = require('../config/logger');
const addMessageSchema = require('../schemas/request/add-message.json');
const getMessagesSchema = require('../schemas/request/get-messages.json');
const requestByIdSchema = require('../schemas/request/request-by-id.json');

const { validate } = new Validator();
const router = express.Router();


/**
 * @swagger
 * paths:
 *   /palindrome:
 *     post:
 *       summary: Add message
 *       produces: [application/json]
 *       parameters:
 *         - in: "body"
 *           name: "message"
 *           type: string
 *           required: true
 *           description: "Message from user"
 *           schema:
 *                  $ref: "#/definitions/AddMessageRequest"
 * 
 *       responses:
 *         201:
 *           description: Message save status
 *           schema:
 *              $ref: "#/definitions/AddMessageResponse"
 *         501:
 *           description: "Internal Server Error"
 *           schema:
 *             $ref: "#/definitions/Error"
 *
 */
router.post('/palindrome', validate({ body: addMessageSchema }), async (req, res, next) => {
  try {
    const result = await service.addMessage(req.body);
    return res.status(StatusCodes.CREATED).send(result);
  } catch (error) {
    return next(error);
  }
});

/**
 * @swagger
 * paths:
 *   /palindrome:
 *     get:
 *       summary: Get previous messages
 *       produces: [application/json]
 *       parameters:
 *         - in: "query"
 *           name: "page"
 *           type: integer
 *           required: false
 *           description: "Page number"
 *         - in: "query"
 *           name: "limit"
 *           type: integer
 *           required: false
 *           description: "Number of records in the page"
 *       responses:
 *         200:
 *           description: Return list of messages
 *           schema:
 *              $ref: "#/definitions/GetMessagesResponse"
 *         400:
 *           description: "Bad request"
 *           schema:
 *             $ref: "#/definitions/Error"
 *
 */
router.get('/palindrome', validate({ query: getMessagesSchema }), async (req, res, next) => {
  try {
    const result = await service.getMessages(req.query);
    res.statusCode = StatusCodes.OK;
    return res.send(result);
  } catch (error) {
    return next(error);
  }
});

/**
 * @swagger
 * paths:
 *   /palindrome/check/{messageId}:
 *     get:
 *       summary: Check if message is palindrome
 *       produces: [application/json]
 *       parameters:
 *         - in: "path"
 *           name: "messageId"
 *           type: integer
 *           required: true
 *           description: "Unique identifier for a message"
 *       responses:
 *         200:
 *           description: Return list of messages
 *           schema:
 *              $ref: "#/definitions/CheckResponse"
 *         404:
 *           description: "Non found"
 *           schema:
 *             $ref: "#/definitions/Error"
 *         400:
 *           description: "Bad request"
 *           schema:
 *             $ref: "#/definitions/Error"
 *
 */
router.get('/palindrome/check/:messageId', validate({ params: requestByIdSchema }), async (req, res, next) => {
  try {
    logger.info(typeof req.params.messageId);
    const result = await service.checkIfPalindrome(req.params);
    return res.status(StatusCodes.OK).send(result);
  } catch (error) {
    return next(error);
  }
});

/**
 * @swagger
 * paths:
 *   /palindrome/{messageId}:
 *     delete:
 *       summary: Delete specific message
 *       produces: [application/json]
 *       parameters:
 *         - in: "path"
 *           name: "id"
 *           type: integer
 *           required: true
 *           description: "Unique identifier for a message"
 *       responses:
 *         200:
 *           description: Return list of messages
 *           schema:
 *              $ref: "#/definitions/DeleteResponse"
 *         404:
 *           description: "Not found"
 *           schema:
 *             $ref: "#/definitions/Error"
 *         400:
 *           description: "Bad request"
 *           schema:
 *             $ref: "#/definitions/Error"
 *
 */
router.delete('/palindrome/:messageId', validate({ params: requestByIdSchema }), async (req, res, next) => {
  try {
    const result = await service.deleteMessage(req.params);
    return res.status(StatusCodes.OK).send(result);
  } catch (error) {
    return next(error);
  }
});

module.exports = router;
