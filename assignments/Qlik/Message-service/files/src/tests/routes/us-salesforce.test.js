const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/us-salesforce');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();
common.chai.use(common.chaiHttp);

describe('GET sac information by sac number API Test', () => {
  before(() => {
    sandbox.stub(service, 'getUsSalesforceBySacNumber')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status Ok for GET sac information by sac number endpoint', () => {
    // ARRANGE
    const url = `${common.constants.US_SALESFORCE_ENDPOINT_TEST_BASE_URL}/12345`;

    // ACT
    common.chai.request(server.app)
      .get(url)

    // ASSERT
      .end((err, res) => res.status.should.equal(200));
  });
});
