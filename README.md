# realtime-chat-app

This repository is for learning purpose on how to create real time chat application.

Features:
1. User can signup/Login
2. User can see list of available rooms in system
3. User can create room if he has ROLE_ADMIN
4. User can join/see the from the available chat rooms
5. User can send and recieve text message in opened chat room
6. All history of chat room message will loaded
7. User can see who has joined or left the chat room

Technical Implementation pointers:
1. Real time chat using websocket with Stomp protocol & Sock JS on Angular UI
2. JWT token with Spring Security for authentication and Authorization 
3. OpenAPI documentation using spring fox
4. Log file with rollover strategy of
5. RabbitMQ topics for chat-rooms for durablity and message broadcasting

Assumptions:
1. Two types of user: Admin, User
2. User can only create and read chat rooms
3. Admin have all the priviledges of user alongside delete 
1. Only text message upto 255 chars is supported in the chat
2. Only room chats are allowed (one to one chat is not implemented)
3. Anyone can join room available in the system

Frontend code is present in chatserver-frontend directory
Run below commands to start frontend
1. $ npm install
2. $ ng serve

Backend code is present in chatserver directory
run mvn clean and start backend app as spring boot application.

Above application requires MySql and Rabbit MQ instance, which you can up by running below Docker commands:

docker run --name local-mysql -v /dockerdb/mysql:/var/lib/mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0


docker container run -it --name rabbitmq-stomp -p 15672:15672 -p 5672:5672 -p 61613:61613 -d pcloud/rabbitmq-stomp


