# Introduction and Goals

---

# Architecture Constraints

## Content

Any requirement that constrains software architects in their freedom of design and implementation decisions or decision about the development process. These constraints sometimes go beyond individual systems and are valid for whole organizations and companies.

## Motivation

Architects should know exactly where they are free in their design decisions and where they must adhere to constraints. Constraints must always be dealt with; they may be negotiable, though.

## Form

Simple tables of constraints with explanations. If needed you can subdivide them into technical constraints, organizational and political constraints and conventions (e.g. programming or versioning guidelines, documentation or naming conventions)

---

# Context and Scope

## Content

System scope and context - as the name suggests - delimits your system (i.e. your scope) from all its communication partners (neighboring systems and users, i.e. the context of your system). It thereby specifies the external interfaces.

If necessary, differentiate the business context (domain specific inputs and outputs) from the technical context (channels, protocols, hardware).

## Motivation

The domain interfaces and technical interfaces to communication partners are among your system’s most critical aspects. Make sure that you completely understand them.

## Form

Various context diagrams
Lists of communication partners and their interfaces.

## Business context

### Content
Specification of all communication partners (users, IT-systems, …) with explanations of domain specific inputs and outputs or interfaces. Optionally you can add domain specific formats or communication protocols.

### Motivation
All stakeholders should understand which data are exchanged with the environment of the system.

### Form
All kinds of diagrams that show the system as a black box and specify the domain interfaces to communiations partners.

Alternatively (or additionally) you can use a table. The title of the table is the name of your system, the three columns contain the name of the communication partner, the inputs, and the outputs.


## Technical context

### Contents

Technical interfaces (channels and transmission media) linking your system to its environment. In addition a mapping of domain specific input/output to the channels, i.e. an explanation with I/O uses which channel.

### Motivation

Many stakeholders make architectural decision based on the technical interfaces between the system and its context. Especially infrastructure or hardware designers decide these technical interfaces.

### Form

E.g. UML deployment diagram describing channels to neighboring systems, together with a mapping table showing the relationships between channels and input/output.

---

# Solution Strategy

## Solution Strategy 01

## Solution Strategy 02



---

# Building Block View

Building Block Overview



---

# Deployment View

## Deployment View



---

# Crosscutting Concepts

## Azure Functions

![Azure Functions](docs/images/08_concept_Azure-Functions-Cover.png)

## Durable Functions

![Durable Functions](docs/images/08_concept_durable_functions.png)

## Serverless Architecture

### Cost-Efficiency

![Serverless Architecture](docs/images/08_concept_serverless_architecture.png)





---

# Architectural Decisions

Architecture Decisions



---

# Quality Requirements

Quality Scenarios



---

# Risk and Technical Debt

## Development Risks

## Deployment Risks

## Operations Risks

---

# Glossary

| Term      | Definition    | 
|-----------|---------------| 
| DX        | Data Exchange | 
| Microtema | Micro tema    |


---

