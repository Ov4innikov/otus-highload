# Проект otus-highload

## Локальный запуск прилоижения(social-network-backend):

1. Для запуска нужны JRE 21, mvn и docker.
2. Соберите проект, выполнив команду ```mvn clean package -f social-network-backend/pom.xml```.
3. Запустите локальный postgres командой ```docker run --name basic-postgres --rm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=4QeFTrGo2u -e PGDATA=/var/lib/postgresql/data/pgdata -v /tmp:/var/lib/postgresql/data -p 5432:5432 -it postgres:14.1-alpine```
4. Запустите приложение, выполнив команду ```java -jar social-network-backend/target/social-network-backend-0.0.1.jar```. 
