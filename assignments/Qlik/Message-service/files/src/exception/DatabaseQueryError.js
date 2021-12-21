const CustomError = require('./CustomError');

class DatabaseQueryError extends CustomError {
  constructor(cause) {
    super(`Error occurred permforming databse query`, cause);
    this.message = 'Error occurred permforming databse query';
    this.code = 500;
  }
}
module.exports = DatabaseQueryError;
