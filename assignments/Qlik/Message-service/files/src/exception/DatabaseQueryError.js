const CustomError = require('./CustomError');

class DatabaseQueryError extends CustomError {
  constructor(type, cause) {
    super(`Error occurred permforming databse query function ${type}`, cause);
    this.message = 'Error occurred permforming databse query';
    this.code = 500;
  }
}
module.exports = DatabaseQueryError;
