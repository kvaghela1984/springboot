mavenbuild:
		mvn clean install

cleanup:
		docker container prune -f && docker image prune -f

dockerbuild: 
		docker build -t jwt-example .
build:	mavenbuild dockerbuild cleanup
