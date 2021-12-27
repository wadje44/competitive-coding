const util = require('../../util/common-util');
const common = require('../common-setup');

describe('Test checkIfPalindrome function', () => {
  it('Should return true for palidrome string', () => {
    // ARRANGE
    const input = 'abba';

    // ACT && ASSERT
    const result = util.checkIfPalindrome(input);
    common.expect(result).to.eql(true);
  });

  it('Should return false for palidrome string', () => {
    // ARRANGE
    const input = 'abbadabbajabba';

    // ACT && ASSERT
    const result = util.checkIfPalindrome(input);
    common.expect(result).to.eql(false);
  });
});

describe('Test getOffset function', () => {
  it('Should return offset zero when page value is 1', () => {
    // ACT && ASSERT
    const offset = util.getOffset(1, 500);
    common.expect(offset).to.eql(0);
  });

  it('Should return offset 500 when page value is 2', () => {
    // ACT && ASSERT
    const offset = util.getOffset(2, 500);
    common.expect(offset).to.eql(500);
  });
});
