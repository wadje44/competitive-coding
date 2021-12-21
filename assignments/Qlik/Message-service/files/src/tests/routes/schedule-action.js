const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/schedule-action');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();
common.chai.use(common.chaiHttp);

describe('GET List Schedule Action API Test', () => {
  before(() => {
    sandbox.stub(service, 'getListScheduleAction')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status OK for GET list of schedule action endpoint', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      }).finally(done());
  });

  let fixtures = [
    { from: 'December 17, 1995 03:24:00', to: 'December 17, 1995 03:24:00' },
    { from: '17/12/1995 03:24:00', to: '17/12/1995 03:24:00' },
    { from: '2018/02/20T10:35:32Z', to: '2018/02/20T10:35:32Z' },
  ];

  fixtures.forEach((filter) => {
    it('Should return status BAD REQUEST when date query paramters is not in ISO8601 format ', () => {
      // ARRANGE & ACT
      common.chai.request(server.app)
        .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });

  fixtures = [
    { from: '2023-02-20T10:35:32Z', to: '2022-02-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-01-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-19T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T09:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:34:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:35:31Z' },
  ];

  fixtures.forEach((filter) => {
    it('Should return status BAD REQUEST when from date is after to date ', () => {
      // ACT
      common.chai.request(server.app)
        .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });

  it('Should return status OK for GET list of schedule action endpoint when region is passed', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL)
      .query({ region: 'AA-ZZ' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      })
      .finally(done());
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit and offse query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL).query({ limit: '10', page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});

describe('GET schedule action by id API Test', () => {
  before(() => {
    sandbox.stub(service, 'getScheduleActionById')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status Ok for GET schedule action by id endpoint', () => {
    // ARRANGE
    const url = `${common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

    // ACT
    common.chai.request(server.app)
      .get(url)

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.OK));
  });

  const invalidFromTo = [
    { from: '2023-02-20T10:35:32Z', to: '2022-02-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-01-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-19T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T09:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:34:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:35:31Z' },
  ];

  invalidFromTo.forEach((filter) => {
    it('Should return status BAD REQUEST when from date is after to date ', () => {
      // ARRANGE
      const url = `${common.constants.SCHEDULE_ACTION_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

      // ACT
      common.chai.request(server.app)
        .get(url).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });

  describe('Test countryCode paramaters for GET list of schedule action endpoint', () => {
    // ARRANGE
    const fixtures = ['/scheduleactions/Aa/2.0.0', '/scheduleactions/ab/2.0.0', '/scheduleactions/AAA/2.0.0', '/scheduleactions/A1/2.0.0'];

    fixtures.forEach((endpoint) => {
      it('Should return status BAD REQUEST when countryCode paramters is not in ISO3166-1 format ', () => {
        // ACT
        common.chai.request(server.app)
          .get(endpoint)

          // ASSERT
          .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
      });
    });
  });
});
