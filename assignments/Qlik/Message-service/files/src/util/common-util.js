module.exports = {

  // Check if message is palindrome
  checkIfPalindrome: (message) => {
    for (let i = 0; i < message.length / 2; i++) {
      if (message[i] != message[message.length - i - 1]) {
        return false;
      }
    }
    return true;
  },

  /*
  * Datastore doesn't support page number 
  * but accepts offset i.e. number of records to be skipped
  * getOffset returns offset using page number and records per page
  */
  getOffset: (page, limit) => {
    let offset = 0;
    if (page && page >= 2) {
      offset = (parseInt(page, 10) - 1) * limit;
    }
    return offset;
  },
};
