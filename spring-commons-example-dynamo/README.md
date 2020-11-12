#Spring commons example Dynamo

## Create a local Dynamo database


Create docker-compose.yml file with following content

```
version: '3.7'
services:
 dynamodb-local:
   image: amazon/dynamodb-local
   container_name: dynamodb-local
   ports:
     - "8000:8000"
```

Execute 
```
docker-compose up -d
```
In this point you have a local dynamo database running in docker


#### Install AWS client

https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2.html

#### Configure aws client

Execute
```
aws configure
```
It's recommended to use the following values
```
AWS Access Key ID [None]: DUMMYIDEXAMPLE                                      
AWS Secret Access Key [None]: DUMMYEXAMPLEKEY
Default region name [eu-west-1]: 
Default output format [None]: 
```

Execute
```
aws dynamodb list-tables --endpoint-url http://localhost:8000
```

You are going to receive this response
```
{
    "TableNames": []
}
```
You are ready to use local dynamo db

## Endpoints for testing

Retrieve all fooObjects
```
GET 
/fooObjects
```
Create a fooObject
```
POST 
/fooObjects
```
Retrieve tables information from dynamo
```
GET
/dynamo-tables
```