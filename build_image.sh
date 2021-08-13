mvn clean install spring-boot:repackage

VERSION=0.11

docker build -t kurau/lh-java-report:${VERSION} .

docker run -it --rm -e COOKIE=test1 -e DEVICE=mobile -v /Users/kurau/git/lighthouse-docker/reports/first/:/home/project kurau/lh-java-report:${VERSION}