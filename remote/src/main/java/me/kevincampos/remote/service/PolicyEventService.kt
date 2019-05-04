package me.kevincampos.remote.service

import kotlinx.coroutines.Deferred
import me.kevincampos.remote.model.PolicyEventResponse
import retrofit2.Response
import retrofit2.http.GET

interface PolicyEventService {

    @GET("5ccf4e91300000770752c4db")
    fun fetchPolicyEventsAsync(): Deferred<Response<List<PolicyEventResponse>>>

}