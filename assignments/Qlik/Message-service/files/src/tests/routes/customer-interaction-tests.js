const sinon = require('sinon');
const common = require('../common-setup');
const server = require('../../index');
const service = require('../../service/customer-interaction');
const logger = require('../../config/logger');
const ReadEventError = require('../../exception/ReadEventError');
const NotFoundError = require('../../exception/NotFoundError');

const sandbox = sinon.createSandbox();

common.chai.use(common.chaiHttp);

describe('Customer Interaction Tests', () => {
  before(() => {
    sandbox.stub(service, 'getListCustomerInteraction')
      .withArgs('test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });
  after(() => {
    service.getListCustomerInteraction.restore();
    sandbox.restore();
  });

  it(' Should return status NOT FOUND for POST interactions endpoint', (done) => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .post(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.NOT_FOUND);
        done();
      });
  });

  it('Should return status OK for GET interactions endpoint', (done) => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      }).finally(done());
  });

  it('Should return status NOT FOUND for POST get customer interactions by id endpoint ', (done) => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}/${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .post(url)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.NOT_FOUND);
        done();
      });
  });

  it('Should return status NOT FOUND for POST get interactions by ikea family number endpoint ', (done) => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .post(url)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.NOT_FOUND);
        done();
      });
  });
});

describe('Test date query paramaters for GET list of interaction API', () => {
  before(() => {
    sandbox.stub(service, 'getListCustomerInteraction')
      .withArgs('test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getListCustomerInteraction.restore(); // Unwraps the spy
    sandbox.restore();
    sinon.restore();
  });

  it('Should return status BAD REQUEST when date query paramters is not in ISO8601 format ', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ from: 'December 17, 1995 03:24:00', to: 'December 17, 1995 03:24:00' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK for valid date query paramters is passed', () => {
    // ARRANGE && ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ from: '2008-11-17T01:00:00Z', to: '2008-11-17T01:00:00Z' })

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
      // ACT
      common.chai.request(server.app)
        .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });
});

describe('Test date query paramaters for GET list of interaction API for Global Customer Id', () => {
  before(() => {
    sandbox.stub(service, 'getInteractionByGlobalCustomerId')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getInteractionByGlobalCustomerId.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when date query paramters is not in ISO8601 format ', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ from: 'December 17, 1995 03:24:00', to: 'December 17, 1995 03:24:00' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK for valid date query paramters is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ from: '2008-11-17T01:00:00Z', to: '2008-11-17T01:00:00Z' })

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
      const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

      // ACT
      common.chai.request(server.app)
        .get(url).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });
});

describe('Test date query paramaters for GET list of interaction API for Ikea Family Number', () => {
  before(() => {
    sandbox.stub(service, 'getInteractionByIkeaFamilyNo')
      .withArgs('test', 'test')
      .resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getInteractionByIkeaFamilyNo.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when date query paramters is not in ISO8601 format ', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ from: 'December 17, 1995 03:24:00', to: 'December 17, 1995 03:24:00' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK for valid date query paramters is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ from: '2008-11-17T01:00:00Z', to: '2008-11-17T01:00:00Z' })

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
      const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

      // ACT
      common.chai.request(server.app)
        .get(url).query(filter)

        // ASSERT
        .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
    });
  });
});

describe('Test limit and page query paramaters for GET list of interaction API', () => {
  before(() => {
    sandbox.stub(service, 'getListCustomerInteraction')
      .withArgs('test', 'test').resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getListCustomerInteraction.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE & ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit query parameter is passed', () => {
    // ARRANGE

    // ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ limit: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });

  it('Should return status OK when valid page query parameter is passed', () => {
    // ARRANGE

    // ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL).query({ page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});

describe('Test limit and page query paramaters for GET list of interaction API for Global Customer Id', () => {
  before(() => {
    sandbox.stub(service, 'getInteractionByGlobalCustomerId')
      .withArgs('test', 'test').resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getInteractionByGlobalCustomerId.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

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
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

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
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.GLOBAL_CUSTOMER_URL}${common.constants.DUMMY_INTERACTION_ID}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '1', page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});

describe('Test limit and page query paramaters for GET list of interaction API for Ikea Family Number', () => {
  before(() => {
    sandbox.stub(service, 'getInteractionByIkeaFamilyNo')
      .withArgs('test', 'test').resolves(
        {},
      );
    sandbox.stub(logger);
  });

  after(() => {
    service.getInteractionByIkeaFamilyNo.restore(); // Unwraps the spy
    sandbox.restore();
  });

  it('Should return status BAD REQUEST when invalid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ limit: '-1' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status BAD REQUEST when invalid page query parameter is passed ', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ page: 'xyz' })

      // ASSERT
      .end((err, res) => res.status.should.equal(common.httpStatus.BAD_REQUEST));
  });

  it('Should return status OK when valid limit query parameter is passed', () => {
    // ARRANGE
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

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
    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}${common.constants.IKEA_FAMILY_URL}${common.constants.DUMMY_IKEA_FAMILY_NO}`;

    // ACT
    common.chai.request(server.app)
      .get(url).query({ page: '1' })

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.OK);
      });
  });
});

describe('Error Handler API Tests', () => {
  before(() => {
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });

  it('Should return status INTERNAL SERVER ERROR When ReadEventError exception occurred', (done) => {
    // ARRANGE
    sandbox.stub(service, 'getListCustomerInteraction').throws(new ReadEventError());
    //  ACT
    common.chai.request(server.app)
      .get(common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.INTERNAL_SERVER_ERROR);
        done();
      });
  });

  it('Should return status Not Found when NotFoundError exception occurred', (done) => {
    // ARRANGE
    sandbox.stub(service, 'getCustomerInteractionById').throws(new NotFoundError());

    const url = `${common.constants.INTERACTION_ENDPOINT_TEST_BASE_URL}/dummyId`;
    //  ACT
    common.chai.request(server.app)
      .get(url)

      // ASSERT
      .end((err, res) => {
        res.status.should.equal(common.httpStatus.NOT_FOUND);
        done();
      });
  });

  describe('Test countryCode paramaters for GET list of interaction API', () => {
    // ARRANGE
    const fixtures = ['/interactions/Aa/2.0.0', '/interactions/ab/2.0.0', '/interactions/AAA/2.0.0', '/interactions/A1/2.0.0'];

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
