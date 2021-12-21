const Conversion = require('../../lib/conversion');
const customerInformation = require('../arrange/customer-information.json');
const ReadEventError = require('../../exception/ReadEventError');

const { datastoreToJson, buildEgressEvent } = Conversion();
const { expect } = require('../common-setup');

/* eslint-disable import/no-unresolved */
const rawCompressedData10031893 = require('../../../fixtures/raw-compressed-data-10031893.json');
const derivedCompressedData10031893 = require('../../../fixtures/derived-compressed-data-10031893.json');
const rawDatastoreData10031893 = require('../../../fixtures/raw-datastore-data-10031893.json');
const derivedDatastoreData10031893 = require('../../../fixtures/derived-datastore-data-10031893.json');
/* eslint-enable import/no-unresolved */

describe('datastoreToJson', () => {
  it('basic', async () => {
    const input = {
      Key: 'EgYKBG5hbWU',
      Properties: [
        {
          Name: 'bankId',
          Value: 'ABC',
          NoIndex: false,
        },
        {
          Name: 'bankLine4',
          Value: '111111111',
          NoIndex: false,
        },
        {
          Name: 'updDateTime',
          Value: '2006-06-02T13:40:16.000Z',
          NoIndex: false,
        },
        {
          Name: 'sacNumber',
          Value: 10050983,
          NoIndex: false,
        },
        {
          Name: 'createDateTime',
          Value: '2006-06-02T13:39:23.000Z',
          NoIndex: false,
        },
      ],
    };
    const expected = {
      bankId: 'ABC',
      bankLine4: '111111111',
      updDateTime: '2006-06-02T13:40:16.000Z',
      sacNumber: 10050983,
      createDateTime: '2006-06-02T13:39:23.000Z',
    };
    expect(expected).to.eql(datastoreToJson(input));
  });

  it('basic compressed', async () => {
    const expected = {
      sacLines: [
        {
          contentEncoding: 'gzip',
          data: 'compressed-sacLines',
        },
      ],
      sacLineSaLog: [
        {
          contentEncoding: 'gzip',
          data: 'compressed-sacLineSaLog',
        },
      ],
      sacSaLog: [],
      sacWorkflowLog: [
        {
          contentEncoding: 'gzip',
          data: 'compressed-sacWorkflowLog',
        },
      ],
    };

    const input = {
      sacLines: [
        {
          Key: 'EgYKBG5hbWU',
          Properties: [
            {
              Name: 'data',
              Value: 'compressed-sacLines',
              NoIndex: false,
            },
            {
              Name: 'contentEncoding',
              Value: 'gzip',
              NoIndex: false,
            },
          ],
        },
      ],
      sacLineSaLog: [{
        Key: 'EgYKBG5hbWU',
        Properties: [
          {
            Name: 'data',
            Value: 'compressed-sacLineSaLog',
            NoIndex: false,
          },
          {
            Name: 'contentEncoding',
            Value: 'gzip',
            NoIndex: false,
          },
        ],
      }],
      sacSaLog: [],
      sacWorkflowLog: [{
        Key: 'EgYKBG5hbWU',
        Properties: [
          {
            Name: 'data',
            Value: 'compressed-sacWorkflowLog',
            NoIndex: false,
          },
          {
            Name: 'contentEncoding',
            Value: 'gzip',
            NoIndex: false,
          },
        ],
      }],
    };
    expect(expected).to.eql(datastoreToJson(input));
  });

  it('successfully converts raw event', async () => {
    expect(rawCompressedData10031893)
      .to.eql(datastoreToJson(rawDatastoreData10031893));
  });

  it('successfully converts compressed fileds event', async () => {
    expect(derivedCompressedData10031893)
      .to.eql(datastoreToJson(derivedDatastoreData10031893));
  });

  it('returns null for empty data', async () => {
    expect(null).to.eql(datastoreToJson(null));
  });
});

describe('Test buildEgressEvent function for CustomerInformation Egress Event', () => {
  it('Should return customer information object with required properties when whole entity is passed', () => {
    // ACT && ASSERT
    const actual = buildEgressEvent(derivedDatastoreData10031893, 'CustomerInformation');
    expect(actual).to.eql(customerInformation);
  });

  it('Should throw an error when egress type not passed', () => {
    // ARRANGE
    const expectedError = new ReadEventError().message;

    // ACT && ASSERT
    expect(() => buildEgressEvent(derivedDatastoreData10031893)).to.throw(expectedError);
  });

  it('Should throw an error when entity not passed', () => {
    // ARRANGE
    const expectedError = new ReadEventError().message;

    // ACT && ASSERT
    expect(() => buildEgressEvent('CustomerInformation')).to.throw(expectedError);
  });
});
