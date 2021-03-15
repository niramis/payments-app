# Payments-app
## Launching
To run this application in production mode add to the VM options in IntelliJ
```bash
-Dspring.profiles.active=production
```
It will load `PaymentOperationsCsvAdapter` bean to store data in CSV file. Otherwise, `PaymentOperationsOutAdapter` is used

