const sinon = require('sinon');
const assert = require('assert');

const usSalesforceService = require('../../service/us-salesforce');
const usSalesforceDao = require('../../dao/us-salesforce');
const usSalesforce = require('../arrange/us-salesforce.json');

const common = require('../common-setup');
const logger = require('../../config/logger');

const sandbox = sinon.createSandbox();

describe('Test getUsSalesforceBySacNumber function', () => {
  describe('Basic success', () => {
    beforeEach(() => {
      sandbox.stub(logger);
    });

    afterEach(() => {
      sandbox.restore();
    });

    it('Return all salesforce object', async () => {
      // ARRANGE
      sandbox.stub(usSalesforceDao, 'getUsSalesforceBySacNumber').returns(
        usSalesforce,
      );

      const reqPathParam = {
        sacNumber: '123456789',
        countryCode: 'ZZ',
      };

      // ACT
      const actual = await usSalesforceService
        .getUsSalesforceBySacNumber(reqPathParam);

      // ASSERT
      common.expect(actual).to.eql(usSalesforce);
      common.expect(usSalesforceDao.getUsSalesforceBySacNumber)
        .to.have.been.calledWith(reqPathParam);
    });
  });

  describe('Fails', () => {
    beforeEach(() => {
      sandbox.stub(logger);
    });

    afterEach(() => {
      sandbox.restore();
    });

    it('Fails generateKeyIdentifier ', async () => {
      // ARRANGE

      sandbox.stub(usSalesforceDao, 'getUsSalesforceBySacNumber')
        .throwsException(new Error('Failed in Dao in getUsSalesforceBySacNumber'));

      const reqPathParam = {
        sacNumber: '123456789',
        countryCode: 'ZZ',
      };

      // ACT & ASSERT
      await assert.rejects(
        usSalesforceService.getUsSalesforceBySacNumber(reqPathParam),
        new Error('Failed in Dao in getUsSalesforceBySacNumber'),
      );
      common.expect(usSalesforceDao.getUsSalesforceBySacNumber)
        .to.have.been.calledWith(reqPathParam);
    });
  });
});
