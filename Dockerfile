FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/one-to-fifty.jar /one-to-fifty/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/one-to-fifty/app.jar"]
