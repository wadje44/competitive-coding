const nock = require('nock');
const { getToken, buildHeadersWithAuthToken } = require('../../lib/getAuthToken');
const { expect } = require('../common-setup');

describe('getAuthToken', () => {
  afterEach(() => {
    nock.cleanAll();
    delete process.env.DO_DOWNSTREAM_AUTH;
  });

  describe('#getToken', () => {
    it('returns valid token with successful service account request', async () => {
      const receivingUrl = 'http://anything';
      const expectedToken = 'a-dummy-token';
      nock('http://metadata/computeMetadata/v1/instance/service-accounts/default/')
        .get('/identity')
        .query(
          {
            audience: receivingUrl,
          },
        )
        .reply(
          200,
          expectedToken,
        );
      const actualToken = await getToken(receivingUrl);
      await expect(actualToken).to.equal(expectedToken);
    });
  });

  describe('#buildHeadersWithAuthToken', () => {
    it('builds headers with bearer token if DO_DOWNSTREAM_AUTH is set', async () => {
      const receivingUrl = 'http://anything';
      const expectedToken = 'a-dummy-token';
      const expectedHeader = {
        authorization: `Bearer ${expectedToken}`,
      };
      process.env.DO_DOWNSTREAM_AUTH = true;
      nock('http://metadata/computeMetadata/v1/instance/service-accounts/default/')
        .get('/identity')
        .query(
          {
            audience: receivingUrl,
          },
        )
        .reply(
          200,
          expectedToken,
        );
      const actualHeaders = await buildHeadersWithAuthToken(receivingUrl);
      expect(actualHeaders).to.eql(expectedHeader);
    });
    it('builds an empty headers when DO_DOWNSTREAM_AUTH is NOT set', async () => {
      const receivingUrl = 'http://anything';
      const expectedHeader = {
      };
      const actualHeaders = await buildHeadersWithAuthToken(receivingUrl);
      expect(actualHeaders).to.eql(expectedHeader);
    });
    it('builds an empty headers when DO_DOWNSTREAM_AUTH is set to false', async () => {
      const receivingUrl = 'http://anything';
      const expectedHeader = {};
      process.env.DO_DOWNSTREAM_AUTH = false;
      const actualHeaders = await buildHeadersWithAuthToken(receivingUrl);
      expect(actualHeaders).to.eql(expectedHeader);
    });
  });
});
