const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/transaction-overview');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();
common.chai.use(common.chaiHttp);

describe('Get List of Transaction Overview API Test', () => {
  before(() => {
    sandbox.stub(service, 'getListTransactionOverview')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status OK for GET list of transaction overview endpoint', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.TRANSACTION_OVERVIEW_ENDPOINT_TEST_BASE_URL)

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
        .get(common.constants.TRANSACTION_OVERVIEW_ENDPOINT_TEST_BASE_URL).query(filter)

      // ASSERT
        .end((err, res) => res.status.should.equal(400));
    });
  });

  it('Should return status OK when valid limit and offse query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.TRANSACTION_OVERVIEW_ENDPOINT_TEST_BASE_URL).query({ limit: '10', page: '1' })

    // ASSERT
      .end((err, res) => {
        res.status.should.equal(200);
      });
  });
});

describe('GET transaction overview by id API Test', () => {
  before(() => {
    sandbox.stub(service, 'getTransacionOverviewId')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status Ok for GET transaction overview by id endpoint', () => {
    // ARRANGE
    const url = `${common.constants.TRANSACTION_OVERVIEW_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

    // ACT
    common.chai.request(server.app)
      .get(url)

    // ASSERT
      .end((err, res) => res.status.should.equal(200));
  });
});
