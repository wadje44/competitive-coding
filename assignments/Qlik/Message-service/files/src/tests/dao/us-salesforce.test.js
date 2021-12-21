const sinon = require('sinon');
const assert = require('assert');

const usSalesforceDao = require('../../dao/us-salesforce');
const usSalesforce = require('../arrange/us-salesforce.json');
const fullsac = require('../arrange/event-payload.json');
const rest = require('../../lib/rest-layer');
const util = require('../../util/common-util');

const common = require('../common-setup');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();

describe('Test getUsSalesforceBySacNumber function', () => {
  describe('Basic success', () => {
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

    it('Return all salesforce object', async () => {
      // ARRANGE
      sandbox.stub(rest, 'apiCall').returns(
        fullsac,
      );

      sandbox.stub(util, 'generateKeyIdentifier').returns(
        'MTIzNDU2Nzg5LVpa',
      );

      const input = {
        reqPathParam: {
          sacNumber: '123456789',
          countryCode: 'ZZ',
        },
      };

      const expected = {
        namespace: 'derived-ZZ',
        id: 'MTIzNDU2Nzg5LVpa',
        queryParam: null,
      };
      // ACT
      const actual = await usSalesforceDao
        .getUsSalesforceBySacNumber(input.reqPathParam);

      // ASSERT
      common.expect(actual).to.eql(usSalesforce);
      common.expect(util.generateKeyIdentifier).to.have.been.calledWith('123456789', 'ZZ');
      common.expect(rest.apiCall).to.have.been.calledWith(expected.namespace, expected.id,
        expected.queryParam);
    });
  });

  describe('Fails', () => {
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

    it('Fails generateKeyIdentifier ', async () => {
      // ARRANGE
      sandbox.stub(rest, 'apiCall').returns(
        fullsac,
      );

      sandbox.stub(util, 'generateKeyIdentifier')
        .throwsException(new Error('Failed in generateKeyIdentifier'));

      const input = {
        reqPathParam: {
          sacNumber: '123456789',
          countryCode: 'ZZ',
        },
      };

      // ACT & ASSERT
      await assert.rejects(
        usSalesforceDao.getUsSalesforceBySacNumber(input.reqPathParam),
        new Error('Failed in generateKeyIdentifier'),
      );
      common.expect(rest.apiCall).to.have.callCount(0);
    });

    it('Fails apiCall ', async () => {
      // ARRANGE
      sandbox.stub(rest, 'apiCall')
        .throwsException(new Error('Failed in apiCall'));

      sandbox.stub(util, 'generateKeyIdentifier').returns(
        'MTIzNDU2Nzg5LVpa',
      );

      const input = {
        reqPathParam: {
          sacNumber: '123456789',
          countryCode: 'ZZ',
        },
      };

      // ACT & ASSERT
      await assert.rejects(
        usSalesforceDao.getUsSalesforceBySacNumber(input.reqPathParam),
        new Error('Failed in apiCall'),
      );
      common.expect(util.generateKeyIdentifier).to.have.callCount(1);
      common.expect(util.generateKeyIdentifier).to.have.been.calledWith('123456789', 'ZZ');
    });
  });
});
