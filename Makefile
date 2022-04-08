.PHONY: build

setup:
	./gradlew wrapper --gradle-version 6.8.3

clean:
	./gradlew clean

build:
	./gradlew clean build

install: clean
	./gradlew install

run-dist:
	./build/install/app/bin/app $(ARGS)

run:
	./gradlew run

test:
	./gradlew test

lint:
	./gradlew checkstyleMain checkstyleTest

check-updates:
	./gradlew dependencyUpdates

build-run: build run
