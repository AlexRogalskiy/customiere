# README

![Build](https://github.com/AlexRogalskiy/customiere/workflows/Build/badge.svg?branch=master&event=push)
![Create Release](https://github.com/AlexRogalskiy/customiere/workflows/Release-draft/badge.svg?branch=master)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/AlexRogalskiy/customiere)
![GitHub closed issues](https://img.shields.io/github/issues-closed/AlexRogalskiy/customiere)
![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/AlexRogalskiy/customiere)
![Github All Contributors](https://img.shields.io/github/all-contributors/AlexRogalskiy/customiere)
![GitHub repo size](https://img.shields.io/github/repo-size/AlexRogalskiy/customiere)
![Lines of code](https://img.shields.io/tokei/lines/github/AlexRogalskiy/customiere)
![GitHub last commit](https://img.shields.io/github/last-commit/AlexRogalskiy/customiere)
![GitHub](https://img.shields.io/github/license/AlexRogalskiy/customiere)
![GitHub All Releases](https://img.shields.io/github/downloads/AlexRogalskiy/customiere/total)
![Docker Pulls](https://img.shields.io/docker/pulls/alexanderr/customiere)
![GitHub language count](https://img.shields.io/github/languages/count/AlexRogalskiy/customiere)
![GitHub search hit counter](https://img.shields.io/github/search/AlexRogalskiy/customiere/goto)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=AlexRogalskiy_customiere&metric=alert_status)](https://sonarcloud.io/dashboard?id=AlexRogalskiy_customiere)

## Summary

Web service intended to provide operations on electronic devices \(create, edit, fetch device's data, etc.\)

## Description

Application can be used for creating and editing electronic devices profiles

## Compile

### For JDK 8

```text
mvnw clean install spring-boot:repackage -Pnon_module_java,test-jar -DskipTests
```

to build image to Docker daemon:

```text
mvnw clean install -Pnon_module_java,test-jar,jib -DskipTests
```

### For JDK 11

```text
mvnw clean install spring-boot:repackage -Pmodule_java,test-jar,jib -DskipTests
```

building image to Docker daemon:

```text
mvnw clean package -Pmodule_java,test-jar,jib -DskipTests

```

buidling and deploying docker image to DockerHub:

```text
mvnw -s settings.xml clean package -Pmodule_java,test-jar,jib -DskipTests -Denv.DOCKERHUB_USERNAME=<username> -Denv.DOCKERHUB_PASSWORD=<password>
```

## Run with containerized PostgreSQL

Run PostgreSQL in docker-container via command:

```text
docker run --name db-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=devicedb -p 5432:5432 -d postgres
```

```text
cd ${project.build.directory}/repackage
java -jar com.sensiblemetrics.api.customiere-crm-clients-0.1.0-SNAPSHOT-exec.jar
```

where

```text
project.build.directory=modules/customiere-crm-clients/.build/bin/com.sensiblemetrics.api.customiere.crm.clients
```

or simply run:

```text
scripts/run.bat
```

## Deploy with Kubectl

#### 1. Start the Customiere CRM Clients service:

```text
kubectl run spring-boot-jib --image=$IMAGE --port=8080 --restart=Never
```

#### 2. Wait until pod is up and running:

```text
kubectl port-forward spring-boot-jib 8080
```

## Download Docker image \(RegistryHub\)

```text
docker pull alexanderr/customiere:0.1.0-SNAPSHOT
```

## Usage

Choose one of the modules and add to your `pom.xml`

```xml
<dependency>
    <groupId>com.sensiblemetrics.api</groupId>
    <artifactId>{module.artifactId}</artifactId>
    <version>{lib.version}</version>
</dependency>
```

Bundle with all Customiere dependencies:

- [**Customiere All**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-all)  
  `customiere-all` - Library to work with Customiere API.

Customiere module dependencies:

- [**Customiere Actuator**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-actuator)  
  `customiere-actuator` - Library to work with Actuator API.

- [**Customiere Commons**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-commons)  
  `customiere-commons` - Library with utilities for common use cases.

- [**Customiere Crm Clients**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-crm-clients)  
  `customiere-crm-clients` - Library to work with CRM Clients API.

- [**Customiere Executor**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-executor)  
  `customiere-executor` - Library to work with Executor API.

- [**Customiere Logger**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-logger)  
  `customiere-logger` - Library to work with Logger API.

- [**Customiere Metrics**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-metrics)  
  `customiere-metrics` - Library to work with Metrics API.

- [**Customiere Security**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-security)  
  `customiere-security` - Library to work with Security API.

- [**Customiere Validation**](https://github.com/AlexRogalskiy/customiere/tree/master/modules/customiere-validation)  
  `customiere-validation` - Library to work with Validation API.
  
## Version Store

[./pom.xml](https://github.com/AlexRogalskiy/customiere/blob/master/pom.xml)

## Miscellaneous

  You can find more useful libs and examples on [wiki](https://github.com/AlexRogalskiy/customiere/wiki)

## Authors

customiere is maintained by:
* [Alexander Rogalskiy](https://github.com/AlexRogalskiy) 

with community support please contact with us if you have some question or proposition.

## Team Tools

[![alt tag](http://pylonsproject.org/img/logo-jetbrains.png)](https://www.jetbrains.com/) 

SensibleMetrics Team would like inform that JetBrains is helping by provided IDE to develop the application. Thanks to its support program for an Open Source projects!

[![alt tag](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=org.schemaspy%3Aschemaspy)

SensibleMetrics customiere project is using SonarCloud for code quality. 
Thanks to SonarQube Team for free analysis solution for open source projects.

## License

SensibleMetrics customiere is distributed under LGPL version 3 or later, see COPYING.LESSER(LGPL) and COPYING(GPL).   
LGPLv3 is additional permissions on top of GPLv3.

![image](https://user-images.githubusercontent.com/19885116/48661948-6cf97e80-ea7a-11e8-97e7-b45332a13e49.png)
