# Introduction and Goals

Describe the project on high level overview
 
## Requirements Overview

TBD

## Quality Goals

![Categories of Quality
Requirements](./docs/images/01_2_iso-25010-topics-EN.drawio.png)

| ID | Priority | Quality Goal           | Description                                                                                                  |
|----|----------|------------------------|--------------------------------------------------------------------------------------------------------------|
| Q1 | 1        | Functional Suitability | System provides functions that meet stated or implied needs                                                  |
| Q2 | 2        | Security               | Protection of system items from accidental or malicious access, use, modification, destruction or disclosure |
| Q3 | 3        | Performance Efficiency | System provides appropriate performance, relative to the amount of resources used                            |
| Q4 | 4        | Reliability            | System can maintain a specific level of performance when used under specific condition                       |
| Q5 | 5        | Maintainability        | System can be modified, corrected, adapted or improved due to changes in environment or requirements         |

## Stakeholders

| Name    | Role                                    | Company   | Contact                                                | Expectations                                                                                          |
|---------|-----------------------------------------|-----------|--------------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| <Name>  | <role>                                  | <company> | <contact>                                              | Establishes a clear timeline; sets a budget that not only factors in costs                            |


| Release   | Date   | Changeset   | Release By | Description |
|-----------|--------|-------------|------------|-------------|
| <Release> | <Date> | <Changeset> | <contact>  | TBD         |


---

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
git config --global user.name "microtema"
git config --global user.email "microtema@web.de"
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


---

# System Scope and Context

**Contents**

System scope and context - as the name suggests - delimits your system
(i.e. your scope) from all its communication partners (neighboring
systems and users, i.e. the context of your system). It thereby
specifies the external interfaces.

![Categories of Quality Requirements](./docs/images/05_building_blocks-EN.png)

## Business Context

Specification of **all** communication partners (users, IT-systems, ...)
with explanations of domain specific inputs and outputs or interfaces.
Optionally you can add domain specific formats or communication
protocols.

| Communication Partner | Description                              | Inputs | Outputs      |
|-----------------------|------------------------------------------|--------|--------------|
| Admin UI              | Registritation Admin UI                  | JSON   | JSON/200,500 |
| Admin API             | Administrate Clients and Templates       | JSON   | JSON/200,500 |
| Customer API          | Internal System hosted in Azure Portal   | JSON   | JSON/200,500 |
| Contract API          | Internal System hosted in Azure Portal   | JSON   | JSON/200,500 |
| ID Provider           | Internal System hosted in Azure Portal   | JSON   | JSON/200,500 |

## Technical Context

Technical interfaces (channels and transmission media) linking your
system to its environment. In addition a mapping of domain specific
input/output to the channels, i.e. an explanation which I/O uses which
channel.

| API                   | Description                                                       | Inputs | Outputs      |
|-----------------------|-------------------------------------------------------------------|--------|--------------|
| Table Storage Account | Table Storage Account                                             | JSON   | JSON/200,500 |
| Blob Storage Account  | Blob Storage Account                                              | JSON   | JSON/200,500 |
| REST API              | CRUD's operations                                                 | JSON   | JSON/200,500 |
| Message Buss          | Publish Event to Message Bus                                      | JSON   | JSON/200,500 |
| Application Insights  | Detect and Diagnose Exceptions and Application Performance Issues | JSON   | JSON/200,500 |

## API

| Stage | URL              |
|-------|------------------|
| DEV   | https://dev.com  |
| INT   | https://int.com  |
| PROD  | https://prod.com |

### POST

/api/user

#### @Headers

```
{
    "Content-Type": "application/json"
    "Authorization": "<app-id>"
}
```

#### @Body

```
{
    "Content-Disposition": form-data; name="file"
}
```

#### @Response

* 200: JobId:string
* 500: Server Error Message:JSON

## Systems

### DEV

| Stage                  | URL/Connection String |
|------------------------|-----------------------|
| API                    | https://dev.com       |
| Storage Account/Tables | ...                   |
| Storage Account/Blob   | ...                   |
| Message Buss           | ...                   |

### INT

| Stage                  | URL/Connection String |
|------------------------|-----------------------|
| API                    | https://int.com       |
| Storage Account/Tables | ...                   |
| Storage Account/Blob   | ...                   |
| Message Buss           | ...                   |

### PROD

| Stage                  | URL/Connection String |
|------------------------|-----------------------|
| API                    | https://prod.com      |
| Storage Account/Tables | ...                   |
| Storage Account/Blob   | ...                   |
| Message Buss           | ...                   |


---

# Solution Strategy

**Contents**

A short summary and explanation of the fundamental decisions and
solution strategies, that shape system architecture. It includes

-   technology decisions

-   decisions about the top-level decomposition of the system, e.g.
    usage of an architectural pattern or design pattern

-   decisions on how to achieve key quality goals

-   relevant organizational decisions, e.g. selecting a development
    process or delegating certain tasks to third parties.

**Motivation**

These decisions form the cornerstones for your architecture. They are
the foundation for many other detailed decisions or implementation
rules.

**Form**

Keep the explanations of such key decisions short.

Motivate what was decided and why it was decided that way, based upon
problem statement, quality goals and key constraints. Refer to details
in the following sections.

The following prerequisites and assumptions are granted:

* OneCloud subscription in non-connected spoke is available
* Knautilus dedicated cluster with the necessary networking parameters and load-balancing is in place
* Budget for the needed Azure Services and other licensed are available
* Azure Services in the regions are available

---

# Building Block View

**Content**

The building block view shows the static decomposition of the system
into building blocks (modules, components, subsystems, classes,
interfaces, packages, libraries, frameworks, layers, partitions, tiers,
functions, macros, operations, data structures, ...) as well as their
dependencies (relationships, associations, ...)

This view is mandatory for every architecture documentation. In analogy
to a house this is the *floor plan*.

**Motivation**

Maintain an overview of your source code by making its structure
understandable through abstraction.

This allows you to communicate with your stakeholder on an abstract
level without disclosing implementation details.

**Form**

The building block view is a hierarchical collection of black boxes and
white boxes (see figure below) and their descriptions.

![Hierarchy of building blocks](./docs/images/05_building_blocks-EN.png)

**Level 1** is the white box description of the overall system together
with black box descriptions of all contained building blocks.

**Level 2** zooms into some building blocks of level 1. Thus it contains
the white box description of selected building blocks of level 1,
together with black box descriptions of their internal building blocks.

**Level 3** zooms into selected building blocks of level 2, and so on.

See [Building Block View](https://docs.arc42.org/section-5/) in the
arc42 documentation.

## Whitebox Overall System {#_whitebox_overall_system}

Here you describe the decomposition of the overall system using the
following white box template. It contains

- an overview diagram

- a motivation for the decomposition

- black box descriptions of the contained building blocks. For these
  we offer you alternatives:

    - use *one* table for a short and pragmatic overview of all
      contained building blocks and their interfaces

    - use a list of black box descriptions of the building blocks
      according to the black box template (see below). Depending on
      your choice of tool this list could be sub-chapters (in text
      files), sub-pages (in a Wiki) or nested elements (in a modeling
      tool).

- (optional:) important interfaces, that are not explained in the
  black box templates of a building block, but are very important for
  understanding the white box. Since there are so many ways to specify
  interfaces why do not provide a specific template for them. In the
  worst case you have to specify and describe syntax, semantics,
  protocols, error handling, restrictions, versions, qualities,
  necessary compatibilities and many things more. In the best case you
  will get away with examples or simple signatures.

***\<Overview Diagram>***

Motivation

:   <text explanation>

Contained Building Blocks

:   <Description of contained building block (black boxes)>

Important Interfaces

:   <Description of important interfaces>

Insert your explanations of black boxes from level 1:

If you use tabular form you will only describe your black boxes with
name and responsibility according to the following schema:

| **Name**       | **Responsibility** |
|----------------|--------------------|
| <black box 1>* | <Text>             |
| <black box 2>* | <Text>             |

If you use a list of black box descriptions then you fill in a separate
black box template for every important building block . Its headline is
the name of the black box.

### \<Name black box 1>

Here you describe \<black box 1> according the the following black box
template:

- Purpose/Responsibility

- Interface(s), when they are not extracted as separate paragraphs.
  This interfaces may include qualities and performance
  characteristics.

- (Optional) Quality-/Performance characteristics of the black box,
  e.g.availability, run time behavior, ....

- (Optional) directory/file location

- (Optional) Fulfilled requirements (if you need traceability to
  requirements).

- (Optional) Open issues/problems/risks

*\<Purpose/Responsibility>*

*\<Interface(s)>*

*\<(Optional) Quality/Performance Characteristics>*

*\<(Optional) Directory/File Location>*

*\<(Optional) Fulfilled Requirements>*

*\<(optional) Open Issues/Problems/Risks>*

### \<Name black box 2>

*\<black box template>*

### \<Name black box n>

*\<black box template>*

### \<Name interface 1>

...

### \<Name interface m>

## Level 2 {#_level_2}

Here you can specify the inner structure of (some) building blocks from
level 1 as white boxes.

You have to decide which building blocks of your system are important
enough to justify such a detailed description. Please prefer relevance
over completeness. Specify important, surprising, risky, complex or
volatile building blocks. Leave out normal, simple, boring or
standardized parts of your system

### White Box *\<building block 1>*

...describes the internal structure of *building block 1*.

*\<white box template>*

### White Box *\<building block 2>*

*\<white box template>*

...

### White Box *\<building block m>*

*\<white box template>*

## Level 3

Here you can specify the inner structure of (some) building blocks from
level 2 as white boxes.

When you need more detailed levels of your architecture please copy this
part of arc42 for additional levels.

### White Box

Specifies the internal structure of *building block x.1*.

*\<white box template>*

### White Box

*\<white box template>*

### White Box

*\<white box template>*

---

# Runtime View

![Runtime View](./docs/images/06_Runtime_View.png)

## Layers/Participants

* Storage Account Lane
* Doc Gen
* Doc Gen Admin

## Service Runtime View

* Storage Account Lane
  * Table Storage
  * Blob Storage
* Doc Gen
  * Get Client Id
  * Is Authorized Client?
  * Are files Valid?
  * Create Job
  * Add Or Update Job
  * Upload File
  * Publish Message
  * Call OnJobCreated Callback
  * Convert Job
  * Process Completed
* Doc Gen Admin
  * Get Client Id

## Successful Runtime View

Once the client is registered and files are valid, 
the job is created and published to the Message-Bus,
files are persisted to the Blob Storage and process is successfully completed.

## Error Runtime View

During the process operation, several types of dimensional errors occur due to the inadequate control of process parameters. 
In this case the process will fail and terminated.

* Errors and Exceptions Scenarios
  * Error may occur by validating the Client-Id
  * Error may occur by validating the files

### Error Runtime Operation and Administration

* Launch
  * The process will be launched as docker container and waiting for requests
* Start-up
  * The process will be start-up via REST API from clients and waiting for the response
* Stop
  * The process will be stopped once the request is done and trace all transaction on reporting service (not ready yet)

## State machines
    
There will be no state holding at all, the process will be completely stateless

---

# Deployment View

**Content**

The deployment view describes:

1.  technical infrastructure used to execute your system, with
    infrastructure elements like geographical locations, environments,
    computers, processors, channels and net topologies as well as other
    infrastructure elements and

2.  mapping of (software) building blocks to that infrastructure
    elements.

Often systems are executed in different environments, e.g. development
environment, test environment, production environment. In such cases you
should document all relevant environments.

Especially document a deployment view if your software is executed as
distributed system with more than one computer, processor, server or
container or when you design and construct your own hardware processors
and chips.

From a software perspective it is sufficient to capture only those
elements of an infrastructure that are needed to show a deployment of
your building blocks. Hardware architects can go beyond that and
describe an infrastructure to any level of detail they need to capture.

**Motivation**

Software does not run without hardware. This underlying infrastructure
can and will influence a system and/or some cross-cutting concepts.
Therefore, there is a need to know the infrastructure.

Maybe a highest level deployment diagram is already contained in section
3.2. as technical context with your own infrastructure as ONE black box.
In this section one can zoom into this black box using additional
deployment diagrams:

-   UML offers deployment diagrams to express that view. Use it,
    probably with nested diagrams, when your infrastructure is more
    complex.

-   When your (hardware) stakeholders prefer other kinds of diagrams
    rather than a deployment diagram, let them use any kind that is able
    to show nodes and channels of the infrastructure.

## Infrastructure Level 1

Describe (usually in a combination of diagrams, tables, and text):

-   distribution of a system to multiple locations, environments,
    computers, processors, .., as well as physical connections between
    them

-   important justifications or motivations for this deployment
    structure

-   quality and/or performance features of this infrastructure

-   mapping of software artifacts to elements of this infrastructure

For multiple environments or alternative deployments please copy and
adapt this section of arc42 for all relevant environments.

***\<Overview Diagram>***

Motivation

:   <explanation in text form>

Quality and/or Performance Features

:   <explanation in text form>

Mapping of Building Blocks to Infrastructure

:   <description of the mapping>

## Infrastructure Level 2

Here you can include the internal structure of (some) infrastructure
elements from level 1.

Please copy the structure from level 1 for each selected element.

### <Infrastructure Element 1>

<diagram + explanation>

### <Infrastructure Element 2>

<diagram + explanation>

...

### <Infrastructure Element n>

<diagram + explanation>

---

# Crosscutting Concepts

**Content**

This section describes overall, principal regulations and solution ideas
that are relevant in multiple parts (= cross-cutting) of your system.
Such concepts are often related to multiple building blocks. They can
include many different topics, such as

-   models, especially domain models

-   architecture or design patterns

-   rules for using specific technology

-   principal, often technical decisions of an overarching (=
    cross-cutting) nature

-   implementation rules

**Motivation**

Concepts form the basis for *conceptual integrity* (consistency,
homogeneity) of the architecture. Thus, they are an important
contribution to achieve inner qualities of your system.

Some of these concepts cannot be assigned to individual building blocks,
e.g. security or safety.

**Form**

The form can be varied:

-   concept papers with any kind of structure

-   cross-cutting model excerpts or scenarios using notations of the
    architecture views

-   sample implementations, especially for technical concepts

-   reference to typical usage of standard frameworks (e.g. using
    Hibernate for object/relational mapping)

**Structure**

A potential (but not mandatory) structure for this section could be:

-   Domain concepts

-   User Experience concepts (UX)

-   Safety and security concepts

-   Architecture and design patterns

-   \"Under-the-hood\"

-   development concepts

-   operational concepts

Note: it might be difficult to assign individual concepts to one
specific topic on this list.

![Possible topics for crosscutting
concepts](./docs/images/08-Crosscutting-Concepts-Structure-EN.png)

## <Concept 1>

explanation

## <Concept 2>

explanation

...

## <Concept n>

explanation

---

**Contents**

Important, expensive, large scale or risky architecture decisions
including rationales. With \"decisions\" we mean selecting one
alternative based on given criteria.

Please use your judgement to decide whether an architectural decision
should be documented here in this central section or whether you better
document it locally (e.g. within the white box template of one building
block).

Avoid redundancy. Refer to other sections, where you already captured the
most important decisions of your architecture.

**Motivation**

Stakeholders of your system should be able to comprehend and retrace
your decisions.


| ID            | AD-001                                                                                                   |
|---------------|----------------------------------------------------------------------------------------------------------|
| Problem       | Due to security and server management overhead concerns, no additional resources can be used in Project  |
| Decision	     | Using the Serverless architecture, auto scaling and security issues is part of the Azure provides        |
| Alternatives  |                                                                                                          |


| ID           | AD-002                                                                                                    |
|--------------|-----------------------------------------------------------------------------------------------------------|
| Problem      | Due to orchestration overhead concerns, no reliable can be granted in Project                             |
| Decision	    | Using the Azure Durable-Function, orchestration and long running task is provided by Azure out of the box |
| Alternatives | Camunda BPMN                                                                                              |


| ID           | AD-003                                                                                                |
|--------------|-------------------------------------------------------------------------------------------------------|
| Problem      | Due to distributed logs concerns, no quick support can be provided in Project                         |
| Decision	    | Using the Process Reporting tool, reporting and auditing can reduce the root cause analysis up to 60% |
| Alternatives | Read logs manually                                                                                    |                                                                                   |

---

# Quality Requirements

## Quality Tree

![Quality Tree](./docs/images/10_Quality_Tree.png)

## Quality Scenarios


| Test Scenario    | Definition                                                                                                                                                         | Link                                      |
|------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| Unit Test        | Verify that individual, isolated parts work as expected. Catch typos and type errors as you write the code                                                         | [Quality Gate](https://quality.cloud.com) |
| Integration Test | Verify that several units work together in harmony                                                                                                                 | [Quality Gate](https://quality.cloud.com) |
| Regression Test  | Identify any change that reintroduces a bug                                                                                                                        | [Quality Gate](https://quality.cloud.com) |
| System Test      | Verify that several systems work together as expected                                                                                                              | [Test Reports](https://quality.cloud.com) | 
| Smoke Test       | Determines whether a new build delivered by the Development team is bug-free or not                                                                                | [Test Reports](https://quality.cloud.com) |
| E2E Test         | Trigger the downstream CI/CD Pipeline for browser automated tests                                                                                                  | [Test Reports](https://quality.cloud.com) |
| Performance Test | Evaluate the speed, responsive and stability of a service and network under a workload (200 threads within 2 minutes), to identify performance-related bottlenecks | [Test Reports](https://quality.cloud.com) |



---

# Risks and Technical Debt

**Contents**

A list of identified technical risks or technical debts, ordered by
priority

1. Release Pipelines
2. E2E Tests (Email Relay API)

**Motivation**

"Risk management is project management for grown-ups" (Tim Lister,
Atlantic Systems Guild.)

This should be your motto for systematic detection and evaluation of
risks and technical debts in the architecture, which will be needed by
management stakeholders (e.g. project managers, product owners) as part
of the overall risk analysis and measurement planning.

**Form**

List of risks and/or technical debts, probably including suggested
measures to minimize, mitigate or avoid risks or reduce technical debts.



---

# Glossary

**Contents**

The most important domain and technical terms that your stakeholders use
when discussing the system.

You can also see the glossary as source for translations if you work in
multi-language teams.

**Motivation**

You should clearly define your terms, so that all stakeholders

-   have an identical understanding of these terms

-   do not use synonyms and homonyms

A table with columns \<Term> and \<Definition>.

Potentially more columns in case you need translations.


| Term         | Definition                          |
|--------------|-------------------------------------|
| <Term-1>     | <definition-1>                      |
| <Term-2>     | <definition-2>                      |

---

