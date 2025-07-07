Простейшее REST приложение на Spring

Запросы:

GET "/get_login_and_status"
Возвращает JSON:
```
{
    "login": "Login1",
    "status": "ok"
}
```

POST "/post_login_password_date"
Принимает JSON:
```
{
    "login": "some_login",
    "password": "some_password"
}
```

Возвращает JSON, построенный на основе данных из JSON запроса:
```
{
    "login": "some_login",
    "password": "some_password",
    "date": "yyyy-MM-dd HH:mm:ss"
}
```

где
currentDate - текущее время в указанном формате.
