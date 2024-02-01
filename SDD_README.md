# Executive Summary

## Project Overview

Project Overview

## Solution value

Solution Values

### In scope and out of scope

#### In scope:

* TBD

#### Out of scope:

* TBD

## Assumptions

Solution Strategy

## Risk & Issues

Risks and Technical Debt

## References

* [Vendor Pack](http://SharePoint.url)
* [Process Control Assessment](http://SharePoint.url)
* [Operating Manual](http://SharePoint.url)
* [Test Strategy Work Product](http://SharePoint.url)
* [Business Requirements Document](http://SharePoint.url)
* [Authorization Concept Document](http://SharePoint.url)

# Overview

## Check list

| Item     | Value     |
|----------|-----------|
| <Item_1> | <Value_1> |
| <Item_n> | <Value_n> |

**Note**

> Going forward your CIA (Confidentiality, Integrity, Availability) rating is an outcome of the pre-cart process of RACE. It can also be found in the CMDB. Please ensure your solution design clearly meets the guardrails based on the data classification of the data that you are working with.

## RACE Process Control Statements

Once the PRE-CART has been completed for your application, the process will generate control statements based on your application's CIA rating. Please download and provide a link to them under the reference section. Otherwise, indicate why the assessment is not available at the time of your submission (i.e., you are submitting an SDD for a POC or for a project that has been through the legacy Compliance Gate Process).

## Business Architecture

Reference the business architecture from your cluster's Target roadmap. Find your roadmap by reaching out to your respective EA (Find Your EA here ). Show how this application fits into the bigger picture of the Munich Re application portfolio.

## Solution Impact on the business capabilities

The diagram below captures potential areas where the solution can have an impact. The view represents major areas like policy, claims, finance etc., If the target roadmaps are not updated, please work with respective EA (Find Your EA here ) to align on the missing capabilities and add the technology stack to the roadmap.

Please identify the solution impact and business capabilities using the diagram below. Circle all categories that apply using the diagram below.

![sdd business capabilities](images/sdd_business-capabilities.png)


# Current and Target State Architecture

This section focuses on whether the project is aligned with EA published target architecture and roadmaps. Please make sure you align with your cluster EA on current and target architecture of the project scope. Provide a current and target architecture high level diagrams from TarDev document and explain how this project is aligned with the target architecture.

Note: If the SDD scope is to upgrade/modify an existing system, conducting a POC, the requirement above can be ignored. This section is targeted for larger projects/initiatives where we are introducing new capabilities or changing technology direction.
Define the current state of the business process and systems. Provide diagrams for the same. If there is a manual process that fulfils the requirements, please elaborate on the process at a high level.

The next section (Section#4) focuses on the details of the build components, security, technology stack and application integrations as part of the solution design for the scope of the project.

## Current State Architecture

![sdd current state architecture](images/sdd_current-state-architecture-example.png)

## Target State Architecture

![sdd target state architecture](images/sdd_current-state-architecture-example.png)

# Solution Architecture

## Architecture Decision

Architecture Decisions  

## Key Deviations & Rationale

A list of deviations from standards (if any), these could be deviations of components (i.e., using a non-standard tool) and/or deviations of patterns. Any deviations here need to be formally accepted by the SDF/ARB. Deviations need to be described in detail. Each deviation should be justified with a valid business case, potential risks, and a mitigation plan.

## Logical High Level Solution Overview

Building Block Overview

## High Level Solution Architecture With Technology Stack

Please add a diagram that shows the logical solution overview including detailed information about the technologies and services used. Include authentication between components and if it is PAAS/SAAS/IAAS etc.

Describe each system involved in the solution at a high level. Define all application components along with integration stack and how they interact with protocols (e.g., API, ODBC or file transfers).

Each application integration must refer to a exiting pattern otherwise a new pattern needs to be defined if required.

Clearly identify type of component used like SaaS, PaaS or IaaS for each component

## List of Enterprise Patterns Used

Provide a complete list of all the technologies and patterns used in the solution. This should reference Enterprise patterns. This section should include the rationale behind selecting specific patterns and potential risks and challenges associated with them.

Note: If your project is leveraging nonstandard software (including Open-Source Tools), the completion of the SDD is not sufficient. Please submit a completed Vendor pack such that the technology can be reviewed and be approved/disapproved to be introduced to the Munich Re standard technology stack.

Organize the patterns you have used by the domains provided in the pattern explorer based on the applications and integrations identified in this solution. If any of the patterns do not exist involve EA to tentatively agree on the pattern as future standard, so that EA can work on publishing a standard pattern in parallel to the project. A request can be submitted via the Patterns page linked above.

Refer to the link below for all available resp. approved cloud services:
Cloud Service Readiness Catalog (sharepoint.com )

**Note**

> Avoid making changes to patterns. However, if a deviation is necessary, please specify the changes made along with the base pattern used.

| ID    | Pattern Name                                              |
|-------|-----------------------------------------------------------|
| ID100 | To Expose API to external users                           |
| ID200 | Managed File Transfer pulling files from external sources |

## Infrastructure Component / Deployment View

### Infrastructure Component

### Deployment View

Deployment View

## System Interfaces

Please provide more detail on the external interfaces or connections the solution connects to. For example: external services, API's, feeds, data sources, Applications, etc.

## Monitoring and incident management

* DataDog
* Process Reporting
* App insights

## DevOps and Automation

* CI/CD Pipelines
* Infra-as-Code
* Doc-as-Code
* Jmeter
* TeamScale

# Data

## Data Flow

Please provide a brief overview (summary) and insert a picture or diagram (it can be copied from Visio, PPT, etc.) including in and outbound communication of the components

## High level Database Objects

Define the database and their type, data management tools, location of the databases like PaaS, on-prem etc. Define business objects stored in the databases like claims, policies, customer info etc.

## Database storage

Provide a brief overview (summary) of which application component(s) store the data by filling out the table below:

**Note**

> If you are using BDAP ADLS indicate which storage area is requested: project, business, application, pipeline etc.

| ID | Stores Data | Permanent | Description    |
|----|-------------|-----------|----------------|
| 01 | Y/N         | Y/N       | Stored for ... |

## Encryption

Define standards followed to encrypt or tokenize the data at rest for databases, files, and intermediate storage.

At rest auf dem Storage / DB

# Network

## Network Overview

Please give a brief overview (summary) and insert a picture or diagram (Visio, PPT, etc.) including in- and outbound communication of the components.

Include details of prod and non-prod systems, subscription details, vNET details and hub & spoke network architecture details

## Systems Communication

Hops, Protocol and Ports

What kind of communication is accepted by the components?
Provide an overview of ALL existing communications, details each communication in details; connection between the services e.g.

* Browser and Application Service
* Browser and Reporting Service
* Application Service and Databases
* Reporting Service and Databases.
* Application Service and Storages.
* Application Service and E-Mail Server

* Please complete the table below:

Note: If details are not available at the time of design, please make sure to add them later and indicate why they are not available at this point of time.

| App/Component (Initiator) | App/Component (Receiver) | Protocol  | Port | Description          |
|---------------------------|--------------------------|-----------|------|----------------------|
| <App_1>                   | <App_n>                  | TCP       | 80   | HTTP request for ... |

# Security

See shared documentation

## Credentials and Certificate management

* KeyVaults

> Define how the project will store various types of certs, credentials etc. Indicate the use of CyberArc, KeyVault, Citrix etc.

## User management and roles

* Authorization Concept

> Define what approaches this solution is going to follow. For example: whether roles are defined in AD/AAD or CIAM etc.

## Identity & Access Management

Indicate whether the components require project specific technical accounts or managed identities?

* Where are these identities managed?
* Handling of secrets?
* Brief description of Multifactor Authentication in use (if any)

### Accounts

| ID  | Account     | Privileged | Managed By | Secrets Stored IN |
|-----|-------------|------------|------------|-------------------|
| <1> | <Account_1> | Y/N        | IAM/PAM    | PAM/KeePass       |

### Groups

| ID  | Group     | Privileged | Managed By | Usage |
|-----|-----------|------------|------------|-------|
| <1> | <Group_1> | Y/N        | IAM/PAM    |       |

### Azure Application Registrations

| ID  | Application     | Admin Consent  | Usage |
|-----|-----------------|----------------|-------|
| <1> | <Application_1> | Y/N            |       |

## Privileged Account Management

Please describe the process of handling high privileged accounts and groups. For example, admins, built in accounts or service accounts. Treatment of secrets, Access, and control of these secrets (e.g., sealing, reports, firefighting, etc.).

**Note** 

> If special privileges are needed this section should be used to capture those decisions. Otherwise, please provide a link to the Authorization Concept Document

## Security Logging, Monitoring & Critical Events

Please provide a brief overview about the overall logging strategy, dedicated logging, and audit requirements. For example, to meet regulatory requirements. In addition, please complete the table below for a better overview:

**Note**

> If you have this information captured in the Operating Manual, please provide a link to that instead.

| ID  | Default Log | App Log | Log Forward | Audit Trail | Description                                                                     |
|-----|-------------|---------|-------------|-------------|---------------------------------------------------------------------------------|
| <1> | Y/N         | Y/N     | Y/N         | Y/N         | Logs to AZURE OMS and forwarded to Azure Security Center. Audit Trail activated |



# Nonfunctional Requirements

Please document nonfunctional requirement from the requirement document, response times, high availability and criticality of the application must be documented in this section. Quality Assurance must follow these NFR’s as basis for their test cases.

If the application is deployed on to existing standard Munich Re platform, you can ignore relevant sections. If a new instance of an existing platform is created, please fill out all the details.

**Note**

> If you are using standard Munich Re systems/processes like back up and DR you do not complete the sections below. Simply, specify that you are following the standard process instead.

## High Availability

## Back Up and Restore

## Volumes and performance requirements

## Disaster Recovery (DR)

# Quality Assurance Strategy

## Functional Testing

## Integration Testing

## Performance Testing

## User Acceptance Testing (UAT)

## Vulnerability and Penetration Testing

# Abbreviations

# Glossary

| Term      | Definition    | 
|-----------|---------------| 
| DX        | Data Exchange | 
| Microtema | Micro tema    |

# Document History

| Version  | Date           | Author               | Changes              |
|----------|----------------|----------------------|----------------------|
| v1.0     | 31.01.2023     | mtema@munichre.com   | Create Documentation |

