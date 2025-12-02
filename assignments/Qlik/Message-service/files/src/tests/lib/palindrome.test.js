const sinon = require('sinon');
const palidromeLib = require('../../lib/palindrome');
const logger = require('../../config/logger');
const db = require('../../config/db-config');
const common = require('../common-setup');
const DatabaseQueryError = require('../../exception/DatabaseQueryError');

const { assert } = common.chai;
const sandbox = sinon.createSandbox();


describe('Test addQueryFilter function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });
    afterEach(() => {
        sandbox.restore();
    });
    it('Should return query when valid query and filters are used', () => {
        // ARRANGE
        const dummyBaseQuery = db.createQuery('test');
        const limit = 10;
        const offset = 10;
        const message = 'message';
        const query = 'query';
        // sinon.stub(dummyBaseQuery, 'filter').returns(
        //     query,
        // );

        // ACT && ASSERT
        const result = palidromeLib.addQueryFilter(dummyBaseQuery, limit, offset, message);
        assert(result, query);
    });

    it('Should return query when valid query and no filter is used', () => {
        // ARRANGE
        const dummyBaseQuery = db.createQuery('test');
        const expectedQuery = 'test-no-filter';

        // ACT && ASSERT
        const result = palidromeLib.addQueryFilter(dummyBaseQuery);
        assert(result, expectedQuery);
    });

    it('Should return query when valid query and limit is used', () => {
        // ARRANGE
        const dummyBaseQuery = db.createQuery('test');
        const limit = 5;
        const expectedQuery = 'test-offset';
        sandbox.stub(dummyBaseQuery, 'limit').returns(
            expectedQuery,
        );

        // ACT && ASSERT
        const result = palidromeLib
            .addQueryFilter(dummyBaseQuery, limit);
        assert(result, expectedQuery);
    });

    it('Should return query when valid query and offset is used', () => {
        // ARRANGE
        const dummyBaseQuery = db.createQuery('test');
        const offset = 5;
        const expectedQuery = 'test-offset';
        sandbox.stub(dummyBaseQuery, 'offset').returns(
            expectedQuery,
        );

        // ACT && ASSERT
        const result = palidromeLib
            .addQueryFilter(dummyBaseQuery, null, offset);
        assert(result, expectedQuery);
    });

    it('Should return query when limit and only message filter is used', () => {
        // ARRANGE
        const dummyBaseQuery = db.createQuery('test');
        const message = 'message-filter-value';
        const spy = sandbox.spy(dummyBaseQuery, 'filter');

        // ACT && ASSERT
        palidromeLib
            .addQueryFilter(dummyBaseQuery, null, null, message);
        assert(spy.calledWithExactly('message', '=', message), 'Filter called with wrong arguments');
    });

    it('Should return error when invalid baseQuery is used', () => {
        // ACT && ASSERT
        common.expect(() => palidromeLib
            .addQueryFilter(null, null)).to.throw(new DatabaseQueryError().message);
    });
});

describe('Test runQuery function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });
    afterEach(() => {
        sandbox.restore();
    });
    it('Should return result when valid query passed', () => {
        // ARRANGE
        const query = 'query';
        const queryResult = ['queryResult'];
        sandbox.stub(db, 'runQuery').returns(
            [queryResult],
        );

        // ACT && ASSERT
        const result = palidromeLib.runQuery(query);
        assert(result, queryResult);
    });

    it('Should throw error for invalid query', async () => {
        // ARRANGE
        const query = null;

        // ACT && ASSERT
        await common.expect(palidromeLib
            .runQuery(query)).to
            .be.rejectedWith(new DatabaseQueryError().message);
    });

    it('Should throw error for query execution fail', async () => {
        // ARRANGE
        const query = 'valid query';
        sandbox.stub(db, 'runQuery')
            .throwsException(new Error('Failed in query execution!'));

        // ACT && ASSERT
        await common.expect(palidromeLib
            .runQuery(query)).to.be.rejectedWith(new DatabaseQueryError().message);
    });
});


describe('Test addMessage function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });
    afterEach(() => {
        sandbox.restore();
    });
    it('Should return result when valid query passed', () => {
        // ARRANGE
        const saveResponse = 'Record saved!';
        sandbox.stub(db, 'save').returns(saveResponse);

        // ACT && ASSERT
        const result = palidromeLib.addMessage('message');
        assert(result, saveResponse);
    });
});

describe('Test getMessages function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });

    afterEach(() => {
        sandbox.restore();
    });

    it('Should return result when valid query passed', async () => {
        // ARRANGE
        const messages = [{}];
        messages[0][db.KEY] = { id: 123 };
        sandbox.stub(db, 'createQuery').returns('baseQuery');
        sandbox.stub(palidromeLib, 'addQueryFilter').returns('query');
        sandbox.stub(db, 'runQuery').returns([messages]);

        // ACT && ASSERT
        const result = await palidromeLib.getMessages(0, 0);
        assert(result, [{ id: 123 }]);
    });
});

describe('Test getMessageById function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });
    afterEach(() => {
        sandbox.restore();
    });
    it('Should return result when valid id is passed', async () => {
        // ARRANGE
        sandbox.stub(db, 'runQuery').returns(['result']);

        // ACT && ASSERT
        const result = await palidromeLib.getMessageById('query');
        assert(result, 'result');
    });
});

describe('Test deleteMessageById function', () => {
    beforeEach(() => {
        sandbox.stub(logger);
    });
    afterEach(() => {
        sandbox.restore();
    });
    it('Should return result when deleted by Id', async () => {
        // ARRANGE
        sandbox.stub(db, 'delete').returns('delete');

        // ACT && ASSERT
        const result = await palidromeLib.deleteMessageById('messageId');
        assert(result, 'delete');
    });
});