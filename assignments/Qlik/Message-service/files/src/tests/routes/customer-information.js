const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/customer-information');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();
common.chai.use(common.chaiHttp);

describe('Get List of Customer Information API Test', () => {
  before(() => {
    sandbox.stub(service, 'getListCustomerInformation')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status OK for GET list of customer information endpoint', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.CUSTOMER_INFORMATION_ENDPOINT_TEST_BASE_URL)

    // ASSERT
      .end((err, res) => {
        res.status.should.equal(200);
      }).finally(done());
  });

  const fixtures = [
    { limit: '-1' },
    { page: 'xyz' },
  ];

  fixtures.forEach((filter) => {
    it('Should return status BAD REQUEST when invalid limit and page query parameter is passed', () => {
      // ACT
      common.chai.request(server.app)
        .get(common.constants.CUSTOMER_INFORMATION_ENDPOINT_TEST_BASE_URL).query(filter)

      // ASSERT
        .end((err, res) => res.status.should.equal(400));
    });
  });

  it('Should return status OK when valid limit and offse query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.CUSTOMER_INFORMATION_ENDPOINT_TEST_BASE_URL).query({ limit: '10', page: '1' })

    // ASSERT
      .end((err, res) => {
        res.status.should.equal(200);
      });
  });
});

describe('GET customer information by id API Test', () => {
  before(() => {
    sandbox.stub(service, 'getCustomerInformationById')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status Ok for GET customer information by id endpoint', () => {
    // ARRANGE
    const url = `${common.constants.CUSTOMER_INFORMATION_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

    // ACT
    common.chai.request(server.app)
      .get(url)

    // ASSERT
      .end((err, res) => res.status.should.equal(200));
  });
});
