mavenbuild:
		mvn clean install

cleanup:
		docker container prune -f && docker image prune -f

dockerbuild: 
		docker build -t user-app .
build:	mavenbuild dockerbuild cleanup
