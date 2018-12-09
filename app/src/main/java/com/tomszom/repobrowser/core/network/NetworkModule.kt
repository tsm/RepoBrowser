package com.tomszom.repobrowser.core.network

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.tomszom.repobrowser.R
import com.tomszom.repobrowser.core.di.scope.PerApplication
import com.tomszom.repobrowser.type.CustomType
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.security.InvalidParameterException
import javax.inject.Named

const val GITHUB_GRAPHQL_API = "https://api.github.com/graphql"

const val NAMED_GITHUB_API_URL = "NAMED_GITHUB_API_URL"
const val NAMED_GITHUB_TOKEN = "NAMED_GITHUB_TOKEN"

@Module
class NetworkModule {

    @Provides
    @Named(NAMED_GITHUB_API_URL)
    @PerApplication
    fun provideGitHubApiUrl(): String {
        return GITHUB_GRAPHQL_API
    }

    @Provides
    @Named(NAMED_GITHUB_TOKEN)
    @PerApplication
    fun provideGitHubToken(context: Context): String {
        return context.getString(R.string.github_oauth_token)
    }

    @Provides
    @PerApplication
    fun provideOkHttpClient(@Named(NAMED_GITHUB_TOKEN) token: String): OkHttpClient {
        if (token.isBlank()) {
            throw InvalidParameterException("provided TOKEN must not be blank!")
        }
        return OkHttpClient.Builder()
            .authenticator { _, response ->
                response.request().newBuilder()
                    .header("Authorization", "Token $token")
                    .build()
            }
            .build()
    }

    @Provides
    @PerApplication
    fun provideApolloClient(@Named(NAMED_GITHUB_API_URL) url: String, okHttpClient: OkHttpClient): ApolloClient {

        return ApolloClient.builder()
            .serverUrl(url)
            .okHttpClient(okHttpClient)
            .addCustomTypeAdapter(CustomType.URI, UriCustomTypeAdapter())
            .build()
    }
}