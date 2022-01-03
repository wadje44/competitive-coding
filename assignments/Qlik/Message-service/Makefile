DOCKER_IMAGE = message-service

.PHONY: build
build:
	DOCKER_BUILDKIT=1 docker build -t $(DOCKER_IMAGE) .

.PHONY: lint
lint: build
	docker run --rm \
		-v $(realpath ./files/src/tests):/src/tests \
		${DOCKER_IMAGE} lint

.PHONY: lint-fix
lint-fix: build
	docker run --rm \
		-v $(realpath ./files/src/tests):/src/test \
		-v $(realpath ./files/src/dao):/src/dao \
		-v $(realpath ./files/src/routes):/src/routes \
		-v $(realpath ./files/src/service):/src/service \
		-v $(realpath ./files/src/util):/src/util \
		-v $(realpath ./files/src/config):/src/config \
		-v $(realpath ./files/src/constants):/src/constants \
		${DOCKER_IMAGE} lint:fix

.PHONY: test
test: build
	docker run --rm \
	    -v $(realpath ./files/src/tests):/src/tests \
		-v $(realpath ../)/box-schemas/files/tests/fixtures:/fixtures \
		${DOCKER_IMAGE} test -- palindrome
