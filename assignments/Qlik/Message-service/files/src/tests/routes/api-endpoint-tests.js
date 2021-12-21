const sinon = require('sinon');

const sandbox = sinon.createSandbox();
const common = require('../common-setup');
const server = require('../../index');
const logger = require('../../config/logger');

common.chai.should();
common.chai.use(common.chaiHttp);

describe(' Health And Invalid Endpoint Tests', () => {
  before(() => {
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status OK for GET Health endpoint', (done) => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get('/health')

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
        done();
      });
  });

  it('Should return status NOT FOUND for random endpoint', (done) => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get('/random')

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.NOT_FOUND);
        done();
      });
  });
});
