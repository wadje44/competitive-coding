class CustomError extends Error {
  constructor(internalMessage, cause) {
    super();
    this.error = true;
    this.internalMessage = internalMessage;
    this.cause = cause;
  }
}

module.exports = CustomError;
