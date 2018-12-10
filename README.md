# RepoBrowser
The Android app written in Kotlin that uses [GraphQL Github API](https://developer.github.com/v4/) to obtain list of owner (user or organisation) repositories. It is rather a POC than a daily use application.

![Screenshots](/img/repobrowser_screens.png?raw=true "RepoBrowser screenshots")


## Build

To use API you need to provide [generated here](https://github.com/settings/tokens/) github OAuth token in the GITHUB_OAUTH_TOKEN environment variable.

You can do it on Mac/Linux:
```
export GITHUB_OAUTH_TOKEN="XXXXXXXXXXXXXXXXXXXXX"

```

This value can also be changed in `NetworkModule.kt` file

## Libraries used

* [apollo-android](https://github.com/apollographql/apollo-android) - GraphQL Client for Android
* [OkHttp](https://github.com/square/okhttp) - Http client
* [RxJava2](https://github.com/ReactiveX/RxJava) - Used as a base for Use Cases mechanism
* [Dagger 2](https://github.com/google/dagger) - Dependency Injection framework
* [Glide](https://github.com/bumptech/glide) - Image loader
* [anko](https://github.com/Kotlin/anko) - Kotlin sugars in Android
* [Mockito](https://site.mockito.org/) - mocking for testing purposes
* and few more including AndroidSupport libraries

## Inspired by

* [Clean Architecture series](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/) by Fernando Cejas
* [MVP with Dagger](https://proandroiddev.com/mvp-with-dagger-2-11-847d52c27c5a) by Mladen Rakonjac
* [Testing MVP with Kotlin and RxJava](https://android.jlelse.eu/complete-example-of-testing-mvp-architecture-with-kotlin-and-rxjava-part-3-df4cf3838581_) by Tamás Kozmér

Many thanks to authors of above articles!



