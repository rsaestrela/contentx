docker run --name mongo -p 27017:27017 -d mongo --auth

sudo docker exec -it mongo mongo admin

use admin

db.createUser({ user: 'raul', pwd: '', roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] });

use contentx

db.createUser({ user: "raul", pwd: '', roles: [ { role: "readWrite", db: "contentx" } ] })