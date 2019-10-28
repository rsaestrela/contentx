db.createUser(
  {
    user: "tester",
    pwd: "password",
    roles: [
      {
        role: "readWrite",
        db: "contentx"
      } 
    ]
  }
)
