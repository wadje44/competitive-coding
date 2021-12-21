const nock = require('nock');
const rest = require('../../lib/rest-layer');
const { expect } = require('../common-setup');

describe('apiCalls', () => {
  beforeEach(() => {
    process.env.REST_LAYER_URI = 'http://localhost:8080';
    process.env.DO_DOWNSTREAM_AUTH = false;
  });

  afterEach(() => {
    delete process.env.REST_LAYER_URI;
    nock.cleanAll();
  });

  it('successfully calls GET api when limit and page passed', async () => {
    const expected = { data: 'test' };
    nock(process.env.REST_LAYER_URI)
      .get('/api/derived')
      .query({ namespace: 'derived-ZZ', limit: 1, page: 1 })
      .reply(200, expected);
    const result = await rest.apiCall('derived-ZZ', null, { limit: 1, page: 1 });
    expect(expected).to.eql(result);
  });

  it('successfully calls GET api when limit and page not passed', async () => {
    const expected = { data: 'test' };
    nock(process.env.REST_LAYER_URI)
      .get('/api/derived')
      .query({ namespace: 'derived-ZZ' })
      .reply(200, expected);
    const result = await rest.apiCall('derived-ZZ', null, null);
    expect(expected).to.eql(result);
  });

  it('successfully calls GET api by id', async () => {
    const expected = { data: 'test' };
    nock(process.env.REST_LAYER_URI)
      .get('/api/derived/abc')
      .query({ namespace: 'derived-ZZ' })
      .reply(200, expected);
    const result = await rest.apiCall('derived-ZZ', 'abc', null);
    expect(expected).to.eql(result);
  });

  it('should throw rest layer error for 500 status code ', async () => {
    const expected = { code: 500, message: 'Internal service error!' };
    nock(process.env.REST_LAYER_URI)
      .get('/api/derived/MTAwMzE4OTMtWlo=')
      .query({ namespace: 'derived-ZZ' })
      .reply(500, expected);
    await expect(rest.apiCall('derived-ZZ', 'MTAwMzE4OTMtWlo=', null)).to.be.rejectedWith('Error calling REST Layer!');
  });

  it('should throw not found error for 404 status code ', async () => {
    const expected = { code: 404, message: 'Not found' };
    nock(process.env.REST_LAYER_URI)
      .get('/api/derived/MTAwMzE4OTMtWlo=')
      .query({ namespace: 'derived-ZZ' })
      .reply(500, expected);
    await expect(rest.apiCall('derived-ZZ', 'MTAwMzE4OTMtWlo=', null)).to.be.rejectedWith('Event not found');
  });
});

describe('apiCalls', () => {
  const fixtures = [{
    input: {},
    output: { limit: 500, page: 1 },
  },
  {
    input: { limit: 1, page: 1 },
    output: { limit: 1, page: 1 },
  },
  {
    input: { region: 'ZZ' },
    output: { limit: 500, page: 1, filter: '{"region":"ZZ"}' },
  },
  {
    input: { region: 'ZZ', from: '2018-02-20T10:35:32Z', to: '2018-02-22T10:35:32Z' },
    output: {
      limit: 500,
      page: 1,
      filter: '{"sacHead.sacCreateDateTime":{"$gte":"2018-02-20T10:35:32Z"},"sacHead.sacCreateDateTime":{"$lte":"2018-02-22T10:35:32Z"},"region":"ZZ"}',
    },
  },
  {
    input: { sacNumber: '11111' },
    output: { limit: 500, page: 1, filter: '{"headers.sacNumber":"11111"}' },
  },
  {
    input: {
      region: 'ZZ', sacNumber: '11111', from: '2018-02-20T10:35:32Z', to: '2018-02-22T10:35:32Z',
    },
    output: {
      limit: 500,
      page: 1,
      filter: '{"sacHead.sacCreateDateTime":{"$gte":"2018-02-20T10:35:32Z"},"sacHead.sacCreateDateTime":{"$lte":"2018-02-22T10:35:32Z"},"region":"ZZ","headers.sacNumber":"11111"}',
    },
  },
  ];

  fixtures.forEach((data) => {
    it('add filter', async () => {
      // ACT && ASSERT
      const actual = await rest.addFilter(data.input);
      expect(actual).to.eql(data.output);
    });
  });
});
