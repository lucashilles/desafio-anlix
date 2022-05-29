# Desafio Anlix

**Sobre o desafio**

Os arquivos texto localizados no diretório dados apresentam diversas características sobre pacientes terminais internados em um hospital, que  são fornecidos por áreas distintas e sempre serão fornecidos separadamente. Os arquivos texto fornecidos por uma mesma área estão nomeados com datas distintas, pois retratam características dos pacientes em diferentes dias. Precisamos que você crie um software que contenha uma base de dados consultável através de uma API REST capaz de:

* [ ] Consultar, para cada paciente, cada uma das características individualmente e cada uma delas sendo a mais recente disponível;
* [ ] Consultar em uma única chamada, todas as características de um paciente, com os valores mais recentes de cada uma;
* [ ] Consultar para uma determinada data (dia, mês e ano), todas as características existentes de todos os pacientes da base de dados;
* [ ] Consultar uma característica qualquer de um paciente para um intervalo de datas a ser especificado na chamada da API;
* [ ] Consultar o valor mais recente de uma característica de um paciente que esteja entre um intervalo de valores a ser especificado na chamada da API;
* [ ] Consultar pacientes que contenham um nome ou parte de um nome a ser especificado na chamada da API.

Além disso, precisamos que algumas informações estejam disponíveis em uma interface web. É importante ressaltar que o diretor do hospital necessita exibir essa interface para todos os investidores da instituição e também para o corpo de médicos. Os requisitos são os seguintes:

* [X] Buscar um paciente por nome e exibir o valor mais recente de cada uma de suas características;
* [X] Ser possível exportar as características de um ou mais pacientes de todas as datas disponíveis para um arquivo CSV;
* [X] Exibir um gráfico temporal para um determinado paciente e uma determinada característica a ser inserida através da interface.

**Observações**

* Os arquivos fornecidos representam apenas uma pequena amostra de dezenas de milhares de pacientes que este hospital atendeu nos últimos 50 anos. O software desenvolvido deve ser capaz de lidar com todos esses dados.
* O gerente de projetos Celso Carrasco de nossa empresa de software precisa de um prazo máximo de entrega de 14 dias corridos. Desenvolva os itens mais pertinentes em sua avaliação caso não haja tempo suficiente para concluir todos os itens.
* Instruções no arquivo README.md de como executar seu projeto são importantes. Conteinerização será muito bem vinda.
* Faça commits em inglês mostrando a evolução do projeto.



# desafio-anlix Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/desafio-anlix-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
