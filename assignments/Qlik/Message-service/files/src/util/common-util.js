module.exports = {

  checkIfPalindrome: (message) => {
    for (let i = 0; i < message.length / 2; i++) {
      if (message[i] != message[message.length - i - 1]) {
        return false;
      }
    }
    return true;
  },

  getOffset: (page, limit) => {
    let offset = 0;
    if (page && page >= 2) {
      offset = (parseInt(page, 10) - 1) * limit;
    }
    return offset;
  },
};
