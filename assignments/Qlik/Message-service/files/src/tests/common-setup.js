const chai = require('chai'); // eslint-disable-line import/no-extraneous-dependencies
const httpStatus = require('http-status-codes');
const chaiHttp = require('chai-http');// eslint-disable-line import/no-extraneous-dependencies

const chaiAsPromised = require('chai-as-promised');// eslint-disable-line import/no-extraneous-dependencies
chai.use(chaiAsPromised);

const { expect } = chai;

module.exports = {
    httpStatus,
    chai,
    expect,
    chaiHttp,
};
