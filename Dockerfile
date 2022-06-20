FROM gradle:7.4-jdk-jammy

WORKDIR /soccer-app

COPY . .

RUN gradle clean build

CMD ["java", "-jar", "-Djava.rmi.server.hostname=localhost", "build/libs/soccer-online-manager-0.0.1.jar", "com.jcostamagna.socceronlinemanager.SoccerOnlineManagerApplication"]
