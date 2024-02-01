# System Scope and Context

**Contents**

System scope and context - as the name suggests - delimits your system
(i.e. your scope) from all its communication partners (neighboring
systems and users, i.e. the context of your system). It thereby
specifies the external interfaces.

![Categories of Quality Requirements](images/05_building_blocks-EN.png)

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
