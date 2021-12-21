const zlib = require('zlib');
const Compress = require('../../lib/compress');
const { expect } = require('../common-setup');

/* eslint-disable import/no-unresolved */
const rawCompressedData10031893 = require('../../../fixtures/raw-compressed-data-10031893.json');
const rawDecompressedData10031893 = require('../../../fixtures/raw-decompressed-data-10031893.json');
const derivedCompressedData10031893 = require('../../../fixtures/derived-compressed-data-10031893.json');
const derivedDecompressedData10031893 = require('../../../fixtures/derived-decompressed-data-10031893.json');
/* eslint-enable import/no-unresolved */

const { decompress } = Compress();

describe('decompress', () => {
  const getInputExpected = () => {
    const expected = {
      sacLines: [{
        sacLine: 1,
      }, {
        sacLine: 2,
      }],
      sacLineSaLog: [{ sacLineSaLog: 1 }],
      sacSaLog: [],
      sacWorkflowLog: [{ sacWorkflowLog: 'test' }],
    };

    const input = {
      sacLines: [
        {
          contentEncoding: 'gzip',
          data: zlib.gzipSync(JSON.stringify(expected.sacLines)).toJSON().data,
        },
      ],
      sacLineSaLog: [
        {
          contentEncoding: 'gzip',
          data: zlib.gzipSync(JSON.stringify(expected.sacLineSaLog)).toJSON().data,
        },
      ],
      sacSaLog: [],
      sacWorkflowLog: [
        {
          contentEncoding: 'gzip',
          data: zlib.gzipSync(JSON.stringify(expected.sacWorkflowLog)).toJSON().data,
        },
      ],
    };
    return { input, expected };
  };

  it('basic raw format decompress check', () => {
    const setUp = getInputExpected();
    expect({ data: setUp.expected }).to.eql(decompress({ data: setUp.input }));
  });

  it('basic derived format decompress check', () => {
    const setUp = getInputExpected();
    expect(setUp.expected).to.eql(decompress(setUp.input));
  });

  it('actual raw format decompress check', () => {
    expect(rawDecompressedData10031893)
      .to.eql(decompress(rawCompressedData10031893));
  });

  it('actual derived format decompress check', () => {
    expect(derivedDecompressedData10031893)
      .to.eql(decompress(derivedCompressedData10031893));
  });
});
