/**
 * @swagger
 * definitions:
 *   AddMessageRequest:
 *     type: object
 *     properties:
 *       message:
 *         type: string
 * 
 *   AddMessageResponse:
 *     type: object
 *     properties:
 *       id:
 *         type: integer
 *       message:
 *         type: string
 * 
 *   GetMessagesResponse:
 *     type: object
 *     properties:
 *       count:
 *          type: integer
 *       items:
 *          type: array
 *          items:
 *            type: object
 *            properties:
 *              id:
 *                  type: integer
 *              message:
 *                  type: string
 *              createDatetime:
 *                  type: string
 * 
 *   CheckResponse:
 *      type: "object"
 *      properties:
 *        isPlindrome:
 *           type: boolean
 * 
 *   DeleteResponse:
 *      type: "object"
 *      properties:
 *        deletedId:
 *           type: integer
 *      
 *   Error:
 *     type: object
 *     properties:
 *       error:
 *         type: boolean
 *       message:
 *         type: string
 *       code:
 *         type: integer
 *
 */
