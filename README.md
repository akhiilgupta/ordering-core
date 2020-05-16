To up the service go to the service directory and run following commands [maven should be installed]:

1. mvn clean install
2. java -jar target/ordering-core-0.0.1-SNAPSHOT.jar

Service will run on the server: http://localhost:8080

User following curls to test the endpoints:

1. To update the status of the delivery: <br />
curl --location --request DELETE 'http://localhost:8080/v1/orders/11312964'

2. To check the status of the delivery: <br />
curl --location --request GET 'http://localhost:8080/v1/orders/11312964'

3. To create the delivery for a given order id: <br />
curl --location --request POST 'http://localhost:8080/v1/orders/'
--header 'Content-Type: application/json'
--data-raw '{ "number_of_items": 3, "address": "address", "phone": "7699092812", "item_id": "123", "name": "test", "email": "test@gmail.com" }'

