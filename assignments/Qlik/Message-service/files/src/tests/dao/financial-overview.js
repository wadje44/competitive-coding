const sinon = require('sinon');
const financialOverviewDao = require('../../dao/financial-overview');
const common = require('../common-setup');
const fullsac = require('../arrange/event-payload.json');
const financialOverview = require('../arrange/financial-overview.json');
const ReadEventError = require('../../exception/ReadEventError');
const db = require('../../config/db-config');
const logger = require('../../config/logger');
const rest = require('../../lib/rest-layer');

const { assert } = common.chai;
const sandbox = sinon.createSandbox();

describe('Test getListFinancialOverview function', () => {
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
    it('Return all financial overviews', async () => {
      // ACT && ASSERT
      sandbox.stub(rest, 'apiCall').returns([fullsac]);
      const actual = await financialOverviewDao
        .getListFinancialOverview(data.input.namespace, data.input.queryParam);
      common.expect(actual).to.eql([financialOverview]);
      common.expect(rest.apiCall).to.have.been.calledWith(data.output.namespace, data.output.id,
        data.output.queryParam);
    });
  });
});

describe('Test getFinancialOverviewById function', () => {
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

    const actual = await financialOverviewDao.getFinancialOverviewById({ countryCode: 'ZZ', id: 'abc' }, {});
    common.expect(actual).to.eql(financialOverview);
  });
});

describe('Test addQueryFilter function', () => {
  it('Should return query with date filtering when limit and offset are not used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const from = '2018-02-20T10:35:32Z';
    const to = '2020-07-06T13:23:57Z';

    const expectedFilters = [
      { name: 'head.sacCreateDateTime', op: '>=', val: from },
      { name: 'head.sacCreateDateTime', op: '<=', val: to },
    ];

    // ACT && ASSERT
    const actualQuery = financialOverviewDao.addQueryFilter(dummyBaseQuery, from, to);
    assert.isTrue(common.areJsonArraysEqual(expectedFilters, actualQuery.filters), 'filters are not added correctly');
  });

  it('Should return error when invalid baseQuery is used', () => {
    // ACT && ASSERT
    common.expect(() => financialOverviewDao
      .addQueryFilter(null, null, null)).to.throw(new ReadEventError().message);
  });

  it('Should return query when valid query and region is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const region = 'US-MI';

    const expectedFilters = [
      { name: 'region', op: '=', val: 'US-MI' },
    ];

    // ACT && ASSERT
    const actualQuery = financialOverviewDao.addQueryFilter(dummyBaseQuery, null, null, region);
    assert.isTrue(common.areJsonArraysEqual(expectedFilters, actualQuery.filters), 'filters are not added correctly');
  });

  it('Should return query when valid query and limit is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const limit = 10;

    // ACT && ASSERT
    const actualQuery = financialOverviewDao.addQueryFilter(
      dummyBaseQuery,
      null,
      null,
      null,
      limit,
    );
    assert.equal(10, actualQuery.limitVal, 'correct offset value is not set');
  });

  it('Should return query when valid query and offset is used', () => {
    // ARRANGE
    const dummyBaseQuery = db.createQuery('test');
    const offset = 5;

    // ACT && ASSERT
    const actualQuery = financialOverviewDao
      .addQueryFilter(dummyBaseQuery, null, null, null, null, offset);
    assert.equal(5, actualQuery.offsetVal, 'correct offset value is not set');
  });
});
