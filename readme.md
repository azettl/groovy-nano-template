# groovy-nano-template - Template Engine

The groovy-nano-template class replaces placeholders in a string with values from an array.

## Installation

```cmd
```

## Usage

```groovy
Template demo = new Template()
demo.setTemplate('<p>\n' +
                             '{user.greeting()} {user.first_name} {user.last name}!\n' +
                             ' Your account is <strong>{user.account.status}</strong> \n' +
                             '{user.nonexistingnode}\n' +
                             '</p>')
demo.setData([
 "user":[
     "login":"demo",
     "first_name":"Anon",
     "last name":"Ymous",
     "account":[
         "status":"active",
         "expires_at":"2016-12-31"
     ],
     "greeting":{ 'Hello' },
     "function":{ param -> 'Test' + param }
 ]
])

println(demo.render())
```