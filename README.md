# Camunda - Dummy journey

## Project
This is a study project to practice the development of [Camunda](https://docs.camunda.org/manual/latest/) based applications.
To be more interesting, this project simulates a credit card applicant journey in which the user inputs documents to sign up for a credit card.

![](.docs/credit_card_bpm.jpeg)

This project contains three modules:
- Engine
- CreditCardProposal
- ValidateDocuments

Each application has a context and are developed to be reusable in order to simulate real-world scenario
in which external tasks are architected to be plugged in different BPMNs with ease.

### Engine
This application only runs [Camunda](https://docs.camunda.org/manual/latest/) engine.

### CreditCardProposal
This application is an [external task](https://docs.camunda.org/manual/latest/user-guide/process-engine/external-tasks/) that validates credit and process approved and rejected credit card proposals.

### ValidateDocuments
This application is an [external task](https://docs.camunda.org/manual/latest/user-guide/process-engine/external-tasks/) that validates the documents provided.
