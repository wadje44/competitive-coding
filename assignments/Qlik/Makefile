DOCKER_BUILDKIT = 1

.EXPORT_ALL_VARIABLES:

.PHONY: up
up:
	docker-compose build
	docker-compose up -d

.PHONY: down
down:
	docker-compose down --remove-orphans
