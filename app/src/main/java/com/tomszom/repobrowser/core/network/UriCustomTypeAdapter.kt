package com.tomszom.repobrowser.core.network

import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue

class UriCustomTypeAdapter : CustomTypeAdapter<String> {
    override fun decode(value: CustomTypeValue<*>): String {
        return value.value.toString()
    }

    override fun encode(value: String): CustomTypeValue<*> {
        return CustomTypeValue.GraphQLString(value)
    }
}