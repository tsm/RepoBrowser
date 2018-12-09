package com.tomszom.repobrowser.core.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.di.scope.PerApplication
import com.tomszom.repobrowser.type.CustomType
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named

const val GITHUB_API_URL = "GITHUB_API_URL"
const val GITHUB_TOKEN = "GITHUB_TOKEN"

@Module
class NetworkModule {

    @Provides
    @Named(GITHUB_API_URL)
    @PerApplication
    fun provideGitHubApiUrl(): String {
        return "https://api.github.com/graphql"
    }

    @Provides
    @Named(GITHUB_TOKEN)
    @PerApplication
    fun provideGitHubToken(context: Context): String {
        return context.getString(R.string.github_oauth_token)
    }

    @Provides
    @PerApplication
    fun provideOkHttpClient(@Named(GITHUB_TOKEN) token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator { route, response ->
                response.request().newBuilder()
                    .header("Authorization", "Token $token")
                    .build()
            }
            .build()
    }

    @Provides
    @PerApplication
    fun provideApolloClient(@Named(GITHUB_API_URL) url: String, okHttpClient: OkHttpClient): ApolloClient {

        return ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .addCustomTypeAdapter(CustomType.URI, UriCustomTypeAdapter())
            .build()
    }
}