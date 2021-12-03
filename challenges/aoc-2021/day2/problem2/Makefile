.PHONY: build
build: 
	docker build -t aoc2021-day2-problem-2 .

.PHONY: run
run: build
	docker run --rm -i \
	-v $(realpath ./test-cases):/src/test-cases \
	aoc2021-day2-problem-2
