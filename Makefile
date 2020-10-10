.DEFAULT_GOAL := help

.PHONY: help
help: ## show this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

clean: ## remove build folders
	@rm -rf .gradle
	@rm -rf build
	@rm -rf out
	@rm -rf node_modules
	@rm -f *.bat

stage: ## package (task executed by heroky)
	@./gradlew stage

dc-stop: ## stop docker containters
	@docker-compose stop -t 0

dc-build: dc-stop ## build docker containers
	@docker-compose build

dc-up: dc-build ## start docker containers
	@docker-compose up -d

classes: ## compile
	@./gradlew classes
