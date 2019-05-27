package com.meetapp.ecoapp.network

import com.meetapp.ecoapp.ui.main.WikiModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface WikiService {

    @GET("api.php")
    fun loadDefinitions(@Query("action") action: String,
                      @Query("format") format: String,
                      @Query("list") list: String,
                      @Query("srsearch") srsearch: String): Observable<WikiModel.Result> }