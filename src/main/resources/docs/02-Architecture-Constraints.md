# Architecture Constraints

## Programming Language
* C#
* Typescript
* Java

## Frameworks
* DotNet
* Docker
* Azure DevOps
* Azure Cloud
* Markdown
* NuGet
* xUnit
* Azure Functions

## Branching Model
    
* Git Flow

## Build Tool

* Dotnet
* NPM

## SCM

* Azure DevOps

## CI Server

* Azure DevOps

## DoD

* Package by Feature
* No TODO's or FIXME are present in code
* No Dead, commented or unused code are present
* No Warnings are present
* Use standard Code Formatter from IDEA
* Follow Programing Language Style Guide
* Use english for Programming language
* Use english for Documentation

## Guidelines

* Programing Language
  * https://google.github.io/styleguide/csharp-style.html
  * https://google.github.io/styleguide/javaguide.html
  * https://google.github.io/styleguide/tsguide.html
  * https://google.github.io/styleguide/shellguide.html
* Follow SOLID Patters
  * *S*- Single-Responsibility Principle
  * *O*- Open-Closed Principle
  * *L*- Liskow Substitution Principle
  * *I*- Interface Segregation Principle
  * *D*- Dependency Inversion
* Follow KISS Patterns
* Follow DRY Patterns

# Team Contract

## Branch Name Policy

```
feature/<ISSUE_ID>_<SHORT_DESCRIPTION>
```

## Commit Policy

```
git commit -m "<ISSUE_ID> <DESCRIPTION>"
```

## Git global setup

```
git config --global user.name "mtema"
git config --global user.email "mtema@munichre.com"
```

## Pull Request Policies

* Min approve: 1
* Default Reviewer: Mario
* Change MR status to unapproved after code change if MR has been reviewed/approved

## IDEA Settings

```
exclude every thing specific idea settings in SCM via .gitignore
```

## Naming Convention

* Repo Name
  * doc-gen
  * smart-box
  * message-relay
* Artifact Id
  * doc-gen-models
  * doc-gen-commons
* Context Path
  * /rest/api/doc-gen
  * /rest/api/smart-box
  * /rest/api/message-relay
* Domain Name
  * DocGen
  * SmartBox
  * MessageRelay
* Entity Name
  * DocGenEntity
  * SmartBoxEntity
  * MessageRelayEntity
* Business Process Flow
  * DocGen Process
  * SmartBox Process
  * MessageRelay Process

## 12 Factor App methodology

We based on the twelve-factor app that is methodology for building software-as-service apps that:

* Use declarative formats for setup automation, to minimize time and cost for new developer joining the project
* Have a clean contract with underlying operation system, offering maximum portability between execution environments
* Are suitable for development on modern cloud platforms, obviating the need for servers and systems administration
* Minimize divergence between development and production, enabling continues development for maximum agility
* Scale up without significant changes to tooling, architecture or development practices
