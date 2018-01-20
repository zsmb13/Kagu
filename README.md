# Kagu

_A basic Kotlin JS web framework_

Kagu is a Kotlin framework for developing client-side, single-page web applications. It uses the JavaScript target of Kotlin.

# Usage

## Components

Kagu is a component based framework. A component contains a template written in HTML, a controller implemented in Kotlin, and optional CSS styling.

Your own components have to subclass the `Component` class, and since these classes themselves should not hold state, they can be easily declared as `object`s.

```kotlin
object UserComponent : Component(
        selector = "user-component",
        templateUrl = "components/user/user.html",
        controller = ::UserController
)
```

#### Static components

Components can be declared statically in HTML using their selector tags. They can also be nested, so their template HTML files can also contain selector tags for components.

```kotlin
<div>
    <user-component></user-component>
</div>
```

#### Dynamic components

Each declared path (see later at Initialization) is associated with a component. An instance of this component will be injected in the `<app-root></app-root>` tag of the main template (see an example [here](/applicationModule/src/main/kotlin/co/zsmb/webmain/index.html)). Anything outside this tag will be a part of every page of the application. 

## Module types

Projects using Kagu have to use Gradle for builds. Two kinds of modules can be built on top of the framework:

- Component modules: these contain reusable components, and can't be ran as an application. They directly depend on the framework. See an example [here](/componentModule).
- Application modules: these contain an actual runnable application. They depend on the framework directly, and they can also depend on component modules. You can see an example [here](/applicationModule).

## Templates

Component templates are simple HTML files, with the addition of the component selector tags (as described above in the Static components section) and the `data-kt-id` and `data-kt-href` attributes made available. Each component should have a single `<div>` in the root of its template.

Here's an example of a component template:

```html
<div>
    <button data-kt-id="myButton">
        This is my button
    </button>
    <a data-kt-href="/home">Back</a>
</div>
```

The `data-kt-id` attribute lets you access an element from the controller, and the `data-kt-href` attribute on `<a>` tags enables you to create links to other pages within the application which will be parsed and appropriately handled by the framework.

## Controllers

Each controller must extend the `Controller` base class, and may override the lifecycle methods the base class defines. These are the following:

- *onCreate*: called when the controller instance is created, this is the place to perform one-time initialization.
- *onAdded*: called every time the component that the controller belongs to is activated, i.e. every time its DOM elements are added to the page. This is a good place to, for example, refresh any data your component displays.
- *onRemoved*: called when the component is being deactivated, its DOM elements removed from the page. It's recommended for your controller to cancel any work it's been performing, unsubscribe from any messages it would receive through the `MessageBroker`, etc. Otherwise, your component will keep working in the background, which might be unexpected for the user.

Controllers can also reference elements in their component's template, and inject various services to perform their duties. These are done via the `lookup()` and `inject()` methods. For a description of how services work, see the Services chapter below.

An example for a controller that would be associated with the template above:

```kotlin
class ButtonController : Controller() {
    private val btn by lookup<HTMLButtonElement>("myButton")
    private val logger by inject<Logger>()
    
    private var clicks = 0
    
    override fun onCreate() {
        btn.onClick {
            logger.d(this, "Button clicked ${++clicks} times")
        }
    }
}
```

## Project structure

The modules used with the framework are regular Gradle modules, however, some guidance about how to structure your code is necessary since HTML, CSS, and even JavaScript files can be mixed with your Kotlin code. There are also special helper files that you need to apply to your Gradle build to use the framework. If you get stuck based on the descriptions here, make sure you look at the Examples section for how to structure your various modules.

#### Component modules

File structure example:

```
/src
    /main
        /kotlin
            /com.example.lib
                /button
                    button.css
                    button.html
                    button.kt
build.gradle
```

Minimal `build.gradle` file:

```groovy
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'kotlin2js'
apply plugin: 'maven-publish'

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin_version"
    compile "co.zsmb:koinjs:$koinjs_version"
    compile "co.zsmb:kagu:$kagu_version"
}

ext.rootPackage = "co.zsmb.examplelib"
ext.moduleName = "examplelib"

apply from: 'https://raw.githubusercontent.com/zsmb13/Kagu/master/componentModule/helper.gradle'

publishing {
    publications {
        maven(MavenPublication) {
            groupId "co.zsmb"
            artifactId "examplelib"
            version "0.0.1"

            artifact jar
            artifact jarSources
        }
    }
}
```

#### Application modules

File structure example:

```
/lib
    jquery-3.2.1.min.js
/src
    /main
        /kotlin
            /co.zsmb.exampleapp
                /components
                    /user
                        user.css
                        user.html
                        user.kt
                index.html
                main.kt
build.gradle
```

Minimal `build.gradle` file:

```groovy
buildscript {
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'kotlin2js'

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib-js:$kotlin_version"
    compile "co.zsmb:koinjs:$koinjs_version"
    compile "co.zsmb:kagu:$kagu_version"
}

ext.rootPackage = "co.zsmb.exampleapp"

apply from: "https://raw.githubusercontent.com/zsmb13/Kagu/master/applicationModule/helper.gradle"
```

## Initialization

## Services

Kagu uses [`KoinJS`](https://github.com/zsmb13/KoinJS) for dependency injeciton. Services in Kagu can be injected into `Controllers` using the `inject` method:

```kotlin
class MyController: Controller() {
    
    private val logger by inject<Logger>()
    
    override fun onCreate() {
        logger.d(this, "MyController onCreate")
    }
    
}
```

### Built-in services

Kagu provides several services out of the box to help perform common web development tasks instead of having to use the raw JavaScript API.

#### HttpService

This service allows you to perform AJAX calls - for now, only with GET, POST, PUT, and DELETE methods.
    
```kotlin
val httpService by inject<HttpService>()

httpService.post(url = "http://www.some.url",
        body = MyDataClass("hello", "world", 123),
        headers = listOf(
                "Content-Type" to "application/json",
                "Accept" to "application/json"
        ),
        onSuccess = { response ->
            logger.d(response)
        },
        onError = { error ->
            logger.d(error)
        })
```

Note that whatever you pass as the `body` parameter will be automatically stringified to JSON, you don't need to do this yourself.

#### Logger

The built in logger service allows you to write messages to the console, which you can automatically tag with the name of the class they're originating from by passing in an optional first parameter to the logging methods.

```kotlin
val logger by inject<Logger>()
        
logger.v(this, "verbose")       // [V/TestClass]: verbose 
logger.i(this, "info")          // [I/TestClass]: info
logger.d(this, "debug")         // [D/TestClass]: debug
logger.e(this, "error")         // [E/TestClass]: error
```

You can adjust which log levels are enabled (globally, since the `Logger` interface is backed by a singleton implementation) by setting the logger's `level` property. Each logging level shows messages that are logged using the same or more important log level. The default setting is `VERBOSE`.

```kotlin
logger.level = Logger.Level.VERBOSE   // shows all messages
logger.level = Logger.Level.INFO     
logger.level = Logger.Level.DEBUG
logger.level = Logger.Level.ERROR     // shows errors only
```

#### MessageBroker

`MessageBroker` is a simple publish-subscribe messaging service that enables cross-component communication. Clients can subscribe to channels, and publish messages to channels which every client subscribed to the given channel will receive.

You can send any object to these channels, the type is just `Any`, it's up to you to manage types.

```kotlin
val messageBroker by inject<MessageBroker>()
        
messageBroker.subscribe("channel1", {
    logger.d("Message received: $it")
})
messageBroker.publish("channel1", MessageObject())
```

To unsubscribe from the broker (which is usually recommended in your `Controller`'s `onRemoved` lifecycle method), you need to store your listener instance.

```kotlin
val callback: MessageCallback = {
    logger.d("Message received: $it")
}
messageBroker.subscribe("channel1", callback)
messageBroker.unsubscribe("channel1", callback)
```

#### Navigator

The `Navigator` service allows you to load a given address within the application.

```kotlin
val navigator by inject<Navigator>()

navigator.goto("/")
navigator.goto("/view/$id")
```

#### PathParams

`PathParams` allows you to read the parameters in your current path:

```kotlin
val pathParams by inject<PathParams>()

pathParams.getInt("id")                 // example path: /view/:id
pathParams.getString("name")            // example path: /profiles/:name
```

The above functions return nullable values, you can use `getIntUnsafe` and `getStringUnsafe` instead to get non-nullable values (or a runtime error, if the parameter is not found or can't be cast).

#### CookieStorage

`CookieStorage` allows you to manage your cookies.

For simple operations, you can just use it as a map, with operators:

```kotlin
val cookieStorage by inject<CookieStorage>()

cookieStorage["key"] = "value"
val value = cookieStorage["key"]
cookieStorage -= "key"
```

Or without the operators:

```kotlin
cookieStorage.set("key", "value")
val value = cookieStorage.get("key")
cookieStorage.remove("key")
```

You can also set cookies with an optional expiry date or custom path:

```kotlin
cookieStorage.set("key", "value", Date(1970, 1, 1), "/docs")
```

#### LocalStorage

You can access the browser's Local Storage API through the `LocalStorage` service. This is used the same way as `CookieStorage`, as a map:

```kotlin
val localStorage by inject<LocalStorage>()

localStorage["key"] = "value"
val value = localStorage["key"]
localStorage -= "key"
```

[What's the difference between cookies and localstorage?](https://stackoverflow.com/a/3220802/4465208). 

#### TemplateLoader

You can load server side HTML resources using the `TemplateLoader` service. This interface is backed by the same service that performs the internal template loading for the framework. It caches results and pools requests for the same resource so that every resource is only download once, making it efficient for populating list view rows, for example.

```kotlin
templateLoader.get("components/list/listitem.html") { listItem ->
    listItem.textContent = item.text
    listItem.onClick {
        navigator.goto("/view/${item.id}")
    }
    list.appendChild(listItem)
}
```

### Defining your own services

You can also define your own services that can build on the browser APIs directly or existing injectable dependencies, such as the built in services. For example, here's a service that uses two built in services to perform its task:

```kotlin
class HttpTestService(val logger: Logger, val httpService: HttpService) {

    fun performTest() {
        httpService.get("https://cors-test.appspot.com/test",
                onSuccess = { response ->
                    logger.d(this, "Http response: ${JSON.stringify(response)}")
                },
                onError = { error ->
                    logger.d(this, "Http error: $error")
                })
    }

}
```

To inject this service, you need to define a KoinJS module, like so:

```kotlin
object MyModule : Module() {

    override fun context() = declareContext {
        provide { HttpTestService(get(), get()) }
    }

}
```

You'll need to add this module when initializing your application for it to be used for injection:

```kotlin
application {
    modules {
        +MyModule
    }
}
```

From here, your own service can be injected like any other:

```kotlin
class MyController: Controller() {
    val httpTestService by inject<HttpTestService>()
}
```

If you wish to inject your service by an interface, cast it to the interface in your KoinJS module's provide method, and then use the interface for the `inject` call as well. 

# Event handler extensions

Kagu offers a couple of extension functions to make the creation of event listeners more idiomatic than directly using the browser APIs.

For example, you can replace this code:

```kotlin
btn.onclick = {
    logger.d("btn clicked")
}
```

... with the following:

```kotlin
btn.onClick { 
    logger.d("btn clicked")
}
```

For the full list of available extensions, see the [EventHandlers.kt](/framework/src/main/kotlin/co/zsmb/kagu/core/dom/EventHandlers.kt) file.

# Examples

### Within the main project

As described above, the main project for the framework includes the [framework module](/framework), as well as an example for a [component module](/componentModule) that depends on the framework and an [application module](/applicationModule) that depends on both.

### kagu-todos

[kagu-todos](https://github.com/zsmb13/kagu-todos) is a simple web application built on Kagu that shows example usages for many of its APIs, and implements CRUD operations on todo items. The frontend of this project only has a single Kagu application module. 

Its project also contains a backend that it can sync with, built on the Vert.x framework.

# License

    Copyright 2018 Marton Braun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# Contributing and development

#### Parameters in `gradle.properties`

- `devMode`: boolean value; `true` makes the Gradle modules depend on each other directly for easy navigation and editing of all modules, while `false` makes projects reference dependencies that are in `mavenLocal()`, to simulate pulling in every module as a separate Gradle dependency

#### Publishing Kagu to mavenLocal

Kagu can be published locally by running `gradlew framework:publishToMavenLocal`.

#### Publishing Kagu to Bintray

This also uses the contents of `gradle.properties`, and requires the following:

- `bintrayUser`: the Bintray username
- `bintrayKey`:  the Bintray account key

If these are filled out, the project can be published to Bintray with the `framework:bintrayUpload` task.
