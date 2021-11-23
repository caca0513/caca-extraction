# caca-extraction

A pet project for me to learn
1. Java
2. Spring Framework
3. Docker/Kubernetes
4. ...

the location based extraction

Done:
1. Anchor base direction indication, e.g. on left of {abc}, on above of {xyz}
2. Partial look up, e.g. Find {cde}, when the original Visual is [abcdefg], it will be split into [ab] [cde] and [fg], and then Find will match the [cde]
3. IoA threshold. used by hunter when determining whether a Visual should be treated as a valid result.
4. When multiple findings(leads) are found for one instruction, helper hunters will be summoned and each following one lead and advance forward, the original hunter(master) will retire. RIP~

Pending:
1. finding multiple records / sub transactions
2. visualize interim states of instruction execution
3. Given a group of scanned document of the same format, and labelled data (annotation), Auto generate instructions for hunter to find the target field/values 
4. Auto group document by its format