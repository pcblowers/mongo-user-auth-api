db.users.insertMany([
  {
    "first_name": "Petrick",
    "last_name": "Blowers",
    "email": "pblowers@entegral.com",
    "username": "pblowers",
    "password": "admin123",
    "roles":["external_identifier_view", "external_identifier_update"],
    "create_date": new ISODate()
  },
  {
      "first_name": "John",
      "last_name": "Doe",
      "email": "jdoe@entegral.com",
      "username": "jdoe",
      "password": "admin1234",
      "roles":["external_identifier_view"],
      "create_date": new ISODate()
    }
]);