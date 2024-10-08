package com.yazaki_groupcom.app

import com.yazaki_groupcom.app.test.PostsData
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface API {

    //放在网络上，https://github.com/chenzhizsqq/testJson/blob/main/db.json
    //app用的是，https://raw.githubusercontent.com/chenzhizsqq/testJson/main/db.json
    @GET("/chenzhizsqq/testJson/posts")
    suspend fun getResponseGson(): Response<List<PostsData>>

    @GET("/")
    suspend fun getResponse(): Response<ResponseBody>
}