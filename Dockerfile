# Estágio 1: Build da aplicação com Maven e Java 21
# Usamos uma imagem oficial e validada que combina Maven com a distribuição Temurin do OpenJDK 21.
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos de configuração do Maven para aproveitar o cache de dependências
COPY pom.xml .
COPY .mvn .mvn

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia o resto do código-fonte da sua aplicação
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes para um build mais rápido
RUN mvn clean install -DskipTests


# Estágio 2: Execução da aplicação
# Usamos uma imagem leve, apenas com o Java 21 Runtime, para manter o container pequeno e seguro
FROM eclipse-temurin:21-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o arquivo .jar gerado no estágio anterior para o novo container
# CORREÇÃO: O nome do arquivo agora está com 'H' maiúsculo para corresponder ao build.
COPY --from=build /app/target/Habits-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que a aplicação vai usar dentro do container
EXPOSE 10000

# Comando para iniciar a aplicação quando o container rodar
# As variáveis de ambiente do Render serão passadas aqui
ENTRYPOINT ["java", "-Dfrontend.url=${FRONTEND_URL}", "-jar", "app.jar"]