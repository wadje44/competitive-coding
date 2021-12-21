const sinon = require('sinon');
const customerInteractionDao = require('../../dao/customer-interaction');
const customerInteraction = require('../arrange/customer-interaction.json');
const fullsac = require('../arrange/event-payload.json');
const rest = require('../../lib/rest-layer');
const common = require('../common-setup');
const logger = require('../../config/logger');
const db = require('../../config/db-config');
const ReadEventError = require('../../exception/ReadEventError');

const { assert } = common.chai;
const sandbox = sinon.createSandbox();

describe('Test getListCustomerInteraction function', () => {
  beforeEach(() => {
    process.env.REST_LAYER_URI = 'http://rest-layer:8060';
    process.env.projectId = 'csdf-local-dev';
    sandbox.stub(logger);
  });

  afterEach(() => {
    delete process.env.REST_LAYER_URI;
    delete process.env.projectId;
    sandbox.restore();
  });

  const fixtures = [{
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: {},
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: { limit: 500, page: 1 },
    },
  },
  {
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: { limit: 1, page: 1 },
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: { limit: 1, page: 1 },
    },
  },
  {
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: { region: 'ZZ' },
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: { limit: 500, page: 1, filter: '{"region":"ZZ"}' },
    },
  },
  {
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: { region: 'ZZ', from: '2018-02-20T10:35:32Z', to: '2018-02-22T10:35:32Z' },
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: {
        limit: 500,
        page: 1,
        filter: '{"sacHead.sacCreateDateTime":{"$gte":"2018-02-20T10:35:32Z"},"sacHead.sacCreateDateTime":{"$lte":"2018-02-22T10:35:32Z"},"region":"ZZ"}',
      },
    },
  },
  {
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: { sacNumber: '11111' },
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: { limit: 500, page: 1, filter: '{"headers.sacNumber":"11111"}' },
    },
  },
  {
    input: {
      namespace: { countryCode: 'ZZ' },
      queryParam: {
        region: 'ZZ', sacNumber: '11111', from: '2018-02-20T10:35:32Z', to: '2018-02-22T10:35:32Z',
      },
    },
    output: {
      namespace: 'derived-ZZ',
      id: null,
      queryParam: {
        limit: 500,
        page: 1,
        filter: '{"sacHead.sacCreateDateTime":{"$gte":"2018-02-20T10:35:32Z"},"sacHead.sacCreateDateTime":{"$lte":"2018-02-22T10:35:32Z"},"region":"ZZ","headers.sacNumber":"11111"}',
      },
    },
  },
  ];

  fixtures.forEach((data) => {
    it('Return all customer interactions', async () => {
      // ACT && ASSERT
      sandbox.stub(rest, 'apiCall').returns([fullsac]);
      const actual = await customerInteractionDao
        .getListCustomerInteraction(data.input.namespace, data.input.queryParam);
      common.expect(actual).to.eql([customerInteraction]);
      common.expect(rest.apiCall).to.have.been.calledWith(data.output.namespace, data.output.id,
        data.output.queryParam);
    });
  });
});

describe('Test getCustomerInteractionById function', () => {
  beforeEach(() => {
    process.env.REST_LAYER_URI = 'http://rest-layer:8080';
    process.env.projectId = 'csdf-local-dev';
    sandbox.stub(logger);
  });

  afterEach(() => {
    delete process.env.projectId;
    sandbox.restore();
  });

  it('Return customer interaction by id', async () => {
    // ACT && ASSERT
    sandbox.stub(rest, 'apiCall').returns(fullsac);

    const actual = await customerInteractionDao.getCustomerInteractionById({ countryCode: 'ZZ', id: 'abc' }, {});
    common.expect(actual).to.eql(customerInteraction);
  });
});

describe('Test addQueryFilter function', () => {
  before(() => {
    sandbox.stub(logger);
  });
  after(() => {
    sandbox.restore();
  });
  it('Should return query when valid query and region is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const region = 'US-MI';
    const query = 'query';
    sinon.stub(dummyBaseQuery, 'filter').returns(
      query,
    );

    // ACT && ASSERT
    const result = customerInteractionDao.addQueryFilter(dummyBaseQuery, region);
    assert(result, query);
  });

  it('Should return query when valid query and limit is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const limit = 10;
    const expectedQuery = 'test-limit';
    sandbox.stub(dummyBaseQuery, 'limit').returns(
      expectedQuery,
    );

    // ACT && ASSERT
    const result = customerInteractionDao.addQueryFilter(dummyBaseQuery, null, null, null, limit);
    assert(result, expectedQuery);
  });

  it('Should return query when valid query and offset is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const offset = 5;
    const expectedQuery = 'test-offset';
    sandbox.stub(dummyBaseQuery, 'offset').returns(
      expectedQuery,
    );

    // ACT && ASSERT
    const result = customerInteractionDao
      .addQueryFilter(dummyBaseQuery, null, null, null, null, offset);
    assert(result, expectedQuery);
  });

  it('Should return query with date filtering when limit and offset are not used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const region = 'US-MI';
    const expectedFrom = '2000-01-20T00:00:00Z';
    const actualFrom = '2000-01-20T00:00:00Z';
    const spy = sandbox.spy(dummyBaseQuery, 'filter');

    // ACT && ASSERT
    customerInteractionDao
      .addQueryFilter(dummyBaseQuery, region, actualFrom, null, null, null);
    assert(spy.calledWithExactly('head.sacCreateDateTime', '>=', expectedFrom), 'Filter called with wrong arguments');
  });

  it('Should return error when invalid baseQuery is used', () => {
    // ACT && ASSERT
    common.expect(() => customerInteractionDao
      .addQueryFilter(null, null)).to.throw(new ReadEventError().message);
  });
});
