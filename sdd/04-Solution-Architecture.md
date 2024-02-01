# Solution Architecture

## Architecture Decision

:[Architecture Decision](../docs/09-Architectural-Decisions/Architectural-Decisions.template.md)  

## Key Deviations & Rationale

A list of deviations from standards (if any), these could be deviations of components (i.e., using a non-standard tool) and/or deviations of patterns. Any deviations here need to be formally accepted by the SDF/ARB. Deviations need to be described in detail. Each deviation should be justified with a valid business case, potential risks, and a mitigation plan.

## Logical High Level Solution Overview

:[Architecture Decision](../docs/05-Building-Block-View/Overview.template.md)

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

:[Deployment View](../docs/07-Deployment-View/Deployment-View.template.md)

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