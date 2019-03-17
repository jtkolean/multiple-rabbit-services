# spring-multiple-rabbitmq
Lets connect to multiple rabbitmq services

#### How to setup services
1. Install docker *(e.g. brew cask install docker)*
2. Run `docker-compose up` to spin up those rabbits

#### Rabbit Management UI Details
- Red Rabbit
  - http://localhost:15672/#/queues
- Blue Rabbit
  - http://localhost:15673/#/queues
- Black Rabbit
  - http://localhost:15674/#/queues
