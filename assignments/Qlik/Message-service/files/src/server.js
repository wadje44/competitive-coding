const { app, logger } = require('.');

const port = process.env.PORT || 3003;

app.listen(port, () => {
  logger.info(`Server is listening on PORT [${port}]`);
});
