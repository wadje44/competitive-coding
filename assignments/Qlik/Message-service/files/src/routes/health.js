const express = require('express');
const httpStatus = require('http-status-codes');

const router = express.Router();

/**
 * @swagger
 * paths:
 *   /health:
 *     get:
 *       summary: Get API health information
 *       tags: [General]
 *       produces: [application/json]
 *       responses:
 *         200:
 *           description: Success
 *           schema:
 *             $ref: "#/definitions/Health"
 * definitions:
 *   Health:
 *     type: object
 *     properties:
 *       uptime:
 *         type: "number"
 *       message:
 *         type: string
 *       timestamp:
 *         type: integer
 */
router.use('/health', async (req, res) => {
  const healthcheck = {
    uptime: process.uptime(),
    message: 'OK',
    timestamp: Date.now(),
  };
  return res.status(httpStatus.OK).send(healthcheck);
});

module.exports = router;
