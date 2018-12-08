package com.tomszom.repobrowser.core.network

import com.apollographql.apollo.ApolloClient
import com.tomszom.repobrowser.type.CustomType
import okhttp3.OkHttpClient

object NetworkClientProvider {

    private const val serverUrl = "https://api.github.com/graphql"

    fun provideApolloClient(token: String): ApolloClient {

        return ApolloClient.builder()
            .serverUrl(serverUrl)
            .okHttpClient(provideOkHttpClient(token))
            .addCustomTypeAdapter(CustomType.URI, UriCustomTypeAdapter())
            .build()
    }

    private fun provideOkHttpClient(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator { route, response ->
                response.request().newBuilder()
                    .header("Authorization", "Token $token")
                    .build()
            }
            .build()
    }

}