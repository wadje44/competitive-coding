const objectMapper = require('object-mapper');

const { dataMapConfigs } = require('../../lib/mapping');
const data = require('../arrange/event-payload.json');
const customerInformation = require('../arrange/customer-information.json');
const scheduleAction = require('../arrange/schedule-action.json');
const customerInteraction = require('../arrange/customer-interaction.json');
const financialOverview = require('../arrange/financial-overview.json');
const transactionOverview = require('../arrange/transaction-overview.json');
const usSalesforce = require('../arrange/us-salesforce.json');
const { expect } = require('../common-setup');

describe('customer events', () => {
  it('Should return customer information event', () => {
    delete customerInformation.eventType;
    delete customerInformation.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[0].map);
    expect(result).to.eql(customerInformation);
  });

  it('Should return customer interaction event', () => {
    delete customerInteraction.eventType;
    delete customerInteraction.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[1].map);
    expect(result).to.eql(customerInteraction);
  });

  it('Should return financial-overview event', () => {
    delete financialOverview.eventType;
    delete financialOverview.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[2].map);
    expect(result).to.eql(financialOverview);
  });

  it('Should return schedule action event', () => {
    delete scheduleAction.eventType;
    delete scheduleAction.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[3].map);
    expect(result).to.eql(scheduleAction);
  });

  it('Should return transaction-overview event', () => {
    delete transactionOverview.eventType;
    delete transactionOverview.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[4].map);
    expect(result).to.eql(transactionOverview);
  });

  it('Should return us-salesforce event', () => {
    delete usSalesforce.eventType;
    delete usSalesforce.payloadVersion;
    const result = objectMapper({ data }, dataMapConfigs[5].map);
    expect(result).to.eql(usSalesforce);
  });
});
