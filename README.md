# RepoBrowser
The Android app written in Kotlin that uses GraphQL Github API to obtain data

![Screenshots](/img/repobrowser_screens.png?raw=true "RepoBrowser screenshots")


## Build

For the correct operation of the application you need to provide [generated here](https://github.com/settings/tokens/) github OAuth token in the GITHUB_OAUTH_TOKEN environment variable.

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



