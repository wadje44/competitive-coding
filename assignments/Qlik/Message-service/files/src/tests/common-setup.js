const chai = require('chai'); // eslint-disable-line import/no-extraneous-dependencies
const httpStatus = require('http-status-codes');

const { expect } = chai;
const chaiHttp = require('chai-http');// eslint-disable-line import/no-extraneous-dependencies
const constants = require('./arrange/constants.json');

const areJsonArraysEqual = (expected, actual) => {
  if (expected.length !== actual.length) {
    return false;
  }

  for (let i = 0; i < expected.length; i += 1) {
    const expectedObject = expected[i];
    const actualObject = actual[i];
    const expectedProps = Object.getOwnPropertyNames(expectedObject);
    const actualProps = Object.getOwnPropertyNames(actualObject);

    if (expectedProps.length !== actualProps.length) {
      return false;
    }

    for (let j = 0; j < expectedProps.length; j += 1) {
      const propName = expectedProps[j];
      if (expectedObject[propName] !== actualObject[propName]) {
        return false;
      }
    }
  }
  return true;
};

module.exports = {
  httpStatus,
  chai,
  expect,
  chaiHttp,
  constants,
  areJsonArraysEqual,
};
