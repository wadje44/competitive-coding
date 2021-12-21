const sinon = require('sinon');
const transactionOverviewDao = require('../../dao/transaction-overview');
const transactionOverview = require('../arrange/transaction-overview.json');
const fullsac = require('../arrange/event-payload.json');
const rest = require('../../lib/rest-layer');
const common = require('../common-setup');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();

describe('Test getListTransactionOverview function', () => {
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
    it('Return all transaction overview', async () => {
      // ACT && ASSERT
      sandbox.stub(rest, 'apiCall').returns(
        [fullsac],
      );
      const actual = await transactionOverviewDao
        .getListTransactionOverview(data.input.namespace, data.input.queryParam);
      common.expect(actual).to.eql([transactionOverview]);
      common.expect(rest.apiCall).to.have.been.calledWith(data.output.namespace, data.output.id,
        data.output.queryParam);
    });
  });
});

describe('Test getTransacionOverviewId function', () => {
  beforeEach(() => {
    process.env.REST_LAYER_URI = 'http://rest-layer:8080';
    process.env.projectId = 'csdf-local-dev';
    sandbox.stub(logger);
  });

  afterEach(() => {
    delete process.env.projectId;
    sandbox.restore();
  });

  it('Return all transaction overview by id', async () => {
    // ACT && ASSERT
    sandbox.stub(rest, 'apiCall').returns(fullsac);

    const actual = await transactionOverviewDao.getTransacionOverviewId({ countryCode: 'ZZ', id: 'abc' });
    common.expect(actual).to.eql(transactionOverview);
  });
});
