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

