mvn clean install spring-boot:repackage

docker build -t lh-java-report:0.1 .

docker run -it --rm -e COOKIE=test1 -v ~/Downloads/lh_experiments/:/home/project lh-java-report:0.1