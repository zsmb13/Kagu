# Kagu

_A basic Kotlin JS web framework_

### Parameters in `gradle.properties`

- `localRepository`
    
    the path to the `mavenLocal` repository, with no trailing slash, e.g. `C:/Users/<WindowsUser>/.m2/repository`
- `devMode`
    
    boolean value; `true` makes the Gradle modules depend on each other directly for easy navigation and editing of all modules, while `false` makes projects reference dependencies that are in `mavenLocal()`, to simulate pulling in every module as a separate Gradle dependency

### Publishing Kagu to mavenLocal

If the `localRepository` is set as described above, running `gradlew framework:build` will both build and publish Kagu to the local maven repository. 
