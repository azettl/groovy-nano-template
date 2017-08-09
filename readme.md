# groovy-nano-template - Template Engine

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c136bd8ac5124d0598ad83ea138f2dc0)](https://www.codacy.com/app/azettl/groovy-nano-template?utm_source=github.com&utm_medium=referral&utm_content=azettl/groovy-nano-template&utm_campaign=badger)

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