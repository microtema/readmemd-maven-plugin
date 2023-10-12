# README.md Generator
Reducing Boilerplate Code with arch42ascii-maven-plugin maven plugin
> More Time for Feature and functionality
  Through a simple set of arch42ascii-maven-plugin templates and saving 60% of development time 

## Key Features
* Auto generation by maven compile phase
* Generate README.md based on ./docs/* templates

## How to use

### Configuration (maven)
```
<plugin>
    <groupId>de.microtema</groupId>
    <artifactId>readmemd-maven-plugin</artifactId>
    <version>2.0.0</version>
    <configuration>
        <docDir>./docs</docDir>
        <outputFile>README.md</outputFile>
    </configuration>
    <executions>
        <execution>
            <id>readme-generator</id>
            <phase>validate</phase>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Input

* /docs
  * /01_introduction_and_goals.adoc
  * /03_system_scope_and_context.adoc
  * /05_building_block_view.adoc
  * /06_runtime_view.adoc
  * /12_glossary.adoc

> NOTE: This is an example file.

## Output 
* ./README.md

> NOTE: This is an example file.

```
= Purchase Order Service

Quality Gate badges

:imagesdir: ./docs

:numbered:

include::docs/01_introduction_and_goals.adoc[]

== Constraints

Inherited from bootstrap

include::docs/03_system_scope_and_context.adoc[]

== Solution Strategy

Inherited from bootstrap

include::docs/05_building_block_view.adoc[]

include::docs/06_runtime_view.adoc[]

== Deployment View

Inherited from bootstrap

== Crosscutting Concepts

Inherited from bootstrap

== Architectural Decisions

Inherited from bootstrap

== Quality Requirements

Inherited from bootstrap

== Risks and Technical Debt

Inherited from bootstrap

include::docs/12_glossary.adoc[]
```
    
## Technology Stack

* Java 1.8
    * Streams 
    * Lambdas
* Third Party Libraries
    * Commons-BeanUtils (Apache License)
    * Commons-IO (Apache License)
    * Commons-Lang3 (Apache License)
    * Junit (EPL 1.0 License)
* Code-Analyses
    * Sonar
    * Jacoco
    
## Test Coverage threshold
> 95%
    
## License

MIT (unless noted otherwise)

## Quality Gate

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=mtema_arch42ascii-maven-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=mtema_jenkinsfile-maven-plugin) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mtema_arch42ascii-maven-plugin&metric=coverage)](https://sonarcloud.io/dashboard?id=mtema_arch42ascii-maven-plugin) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=mtema_arch42ascii-maven-plugin&metric=sqale_index)](https://sonarcloud.io/dashboard?id=mtema_arch42ascii-maven-plugin)
