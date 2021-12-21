const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/financial-overview');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();
common.chai.use(common.chaiHttp);

describe('GET List Financial overview API Test', () => {
  before(() => {
    sandbox.stub(service, 'getListFinancialOverview')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status OK for GET list of financial overview endpoint', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL)

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
      // ARRANGE & ACT
      common.chai.request(server.app)
        .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });
});

describe('GET Financial overview by id API Test', () => {
  before(() => {
    sandbox.stub(service, 'getFinancialOverviewById')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status Ok for GET financial overview by id endpoint', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

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
      const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}/5324gdfgre3224`;

      // ACT
      common.chai.request(server.app)
        .get(url).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });

  describe('Test countryCode paramaters for GET list of financial overview API', () => {
    // ARRANGE
    const fixtures = ['/financialoverviews/Aa/2.0.0', '/financialoverviews/ab/2.0.0', '/financialoverviews/AAA/2.0.0', '/financialoverviews/A1/2.0.0'];

    fixtures.forEach((endpoint) => {
      it('Should return status BAD REQUEST when countryCode paramters is not in ISO3166-1 format ', () => {
        // ACT
        common.chai.request(server.app)
          .get(endpoint).query()

          // ASSERT
          .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
      });
    });
  });

  describe('Test region paramaters for GET list of financial overview API', () => {
    // ARRANGE
    const fixtures = ['/financialoverviews/AA/2.0.0?region=aa',
      '/financialoverviews/AA/2.0.0?region=aB',
      '/financialoverviews/AA/2.0.0?region=AA-aa',
      '/financialoverviews/AA/2.0.0?region=aa-AA'];

    fixtures.forEach((endpoint) => {
      it('Should return status BAD REQUEST when region paramters is not in ISO3166-1 and ISO3166-2 format ', () => {
        // ACT
        common.chai.request(server.app)
          .get(endpoint).query()

          // ASSERT
          .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
      });
    });
  });
});

describe('Test GET list of financial overview API for Global Customer Id', () => {
  before(() => {
    sandbox.stub(service, 'getByGlobalCustomerId')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    sandbox.restore();
  });

  it('Should return status OK REQUEST for GET list of financial overview endpoint by Global Customer Id', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url)
      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.OK));
  });

  it('Should return status OK for date query paramters is passed', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ from: '2008-11-17T01:00:00Z', to: '2008-11-17T01:00:00Z' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.OK));
  });

  it('Should return status OK when region query paramters is passed', () => {
    // ARRANGE && ACT
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    common.chai.request(server.app)
      .get(url)
      .query({ region: 'TT-TT' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.OK));
  });

  const fixtures = [
    { from: '2023-02-20T10:35:32Z', to: '2022-02-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-01-20T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-19T10:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T09:35:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:34:32Z' },
    { from: '2023-02-20T10:35:32Z', to: '2023-02-20T10:35:31Z' },
  ];

  fixtures.forEach((filter) => {
    it('Should return status BAD REQUEST when from date is after to date ', () => {
      // ARRANGE
      const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

      // ACT
      common.chai.request(server.app)
        .get(url).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });
});

describe('Test limit and page query paramaters for GET list of financial overviews API', () => {
  before(() => {
    sandbox.stub(service, 'getListFinancialOverview')
      .withArgs('test', 'test').resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getListFinancialOverview.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit query parameter is passed', () => {
    // ARRANGE

    // ACT
    common.chai.request(server.app)
      .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query({ limit: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });

  it('Should return status OK when valid page query parameter is passed', () => {
    // ARRANGE

    // ACT
    common.chai.request(server.app)
      .get(common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL).query({ page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});

describe('Test limit and page query paramaters for GET list of financial overview for Global Customer Id', () => {
  before(() => {
    sandbox.stub(service, 'getByGlobalCustomerId')
      .withArgs('test', 'test').resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getByGlobalCustomerId.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });

  it('Should return status OK when valid page query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });

  it('Should return status OK when valid limit and page query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.FINANCIAL_OVERVIEW_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '1', page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});
