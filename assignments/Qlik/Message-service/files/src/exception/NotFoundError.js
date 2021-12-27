const CustomError = require('./CustomError');

class NotFoundError extends CustomError {
  constructor(type, cause) {
    super(`Error occurred in ${type} function`, cause);
    this.message = 'Event not found';
    this.code = 404;
  }
}
module.exports = NotFoundError;
