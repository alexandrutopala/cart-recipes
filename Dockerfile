FROM eclipse-temurin:21-jre-alpine

WORKDIR /srv

EXPOSE 8080

COPY target/cart-recipes.jar /srv/cart-recipes.jar

ENTRYPOINT ["java", "-jar", "/srv/cart-recipes.jar"]