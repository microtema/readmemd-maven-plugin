
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
| Alternatives | Read logs manually                                                                                    |