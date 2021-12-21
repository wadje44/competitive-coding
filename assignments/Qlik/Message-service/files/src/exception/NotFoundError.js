const CustomError = require('./CustomError');

class NotFoundError extends CustomError {
  constructor(type, cause) {
    super(`Error occurred ${type} function in dao layer `, cause);
    this.message = 'Event not found';
    this.code = 404;
  }
}
module.exports = NotFoundError;
