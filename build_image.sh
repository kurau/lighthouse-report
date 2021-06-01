mvn clean install spring-boot:repackage

VERSION=0.6

docker build -t kurau/lh-java-report:${VERSION} .

docker run -it --rm -e COOKIE=test1 -e DEVICE=mobile -v ~/Downloads/lh_experiments2/:/home/project kurau/lh-java-report:${VERSION}