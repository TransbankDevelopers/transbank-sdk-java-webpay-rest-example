FROM maven:3-jdk-8
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y git
RUN mkdir /app
WORKDIR /app
RUN git clone https://github.com/TransbankDevelopers/transbank-sdk-java-webpay-rest-example.git
WORKDIR /app/transbank-sdk-java-webpay-rest-example
RUN git checkout feat/transbank-live-example
RUN mvn clean package
ENTRYPOINT ["mvn", "spring-boot:run"]