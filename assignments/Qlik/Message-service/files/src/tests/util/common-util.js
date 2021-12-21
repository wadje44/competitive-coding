const util = require('../../util/common-util');
const common = require('../common-setup');

describe('Test updateSourceId function', () => {
  it('Should return sdf as sourceId when real fullsac event is passed', () => {
    // ARRANGE
    const expectedSourceId = 'sdf';

    // ACT && ASSERT
    const updatedSourceId = util.updateSourceId('SE', 'SAMS');
    common.expect(updatedSourceId).to.eql(expectedSourceId);
  });

  it('Should return sdfIntegrationTest as sourceId when test event is passed', () => {
    // ARRANGE
    const expectedSourceId = 'sdf.IntegrationTest';

    // ACT && ASSERT
    const updatedSourceId = util.updateSourceId('ZZ', 'IntegrationTest');
    common.expect(updatedSourceId).to.eql(expectedSourceId);
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
