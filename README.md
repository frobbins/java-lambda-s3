# The Icecream Parlor Project

## About
This project is designed to create a serverless workflow for placing ice cream parlor orders.

##Environment
Java IDE (Preferrably IntelliJ) and AWS Console.

##Necessary Libraries/Resources
Maven

AWS

##How to Run
You will need: source code, AWS account, S3 bucket, Lambda functions, Step Function/State Machine,
and a JSON file.

The source code will need to be packaged with Maven.

Upload the JAR generated with Maven to all Lambdas on AWS.

Create the Step Function using the stepfunctions.json resource file and put the Lambda ARNs in the
respective places in the Step Function code.

Upload the JSON file to the S3 bucket. This will trigger the first lambda function which will execute
the existing State Machine in AWS. The workflow from the Step Function will call all the other lambdas
in order, building the dessert order. Once complete, the result is distributed via text when 'served'.

Please note, your AWS assets must be in an eligible region for SNS messaging. See this resource
for details.

https://docs.aws.amazon.com/sns/latest/dg/sns-supported-regions-countries.html