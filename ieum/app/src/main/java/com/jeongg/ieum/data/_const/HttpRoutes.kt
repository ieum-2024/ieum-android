package com.jeongg.ieum.data._const

enum class HttpRoutes(val path: String) {

    BASE_URL("192.168.219.110"),

    // content
    GET_CONTENT_LIST("content"),
    GET_CONTENT_DETAIL("content/detail"),
    CREATE_CONTENT("content"),

    // interest
    GET_INTEREST_ALL("interest/all"),
    GET_INTEREST_PUBLIC("interest/public"),
    GET_INTEREST_PRIVATE("interest/private"),
    CREATE_INTEREST("interest/create"),
    DELETE_INTEREST("interest/delete"),

    // user
    KAKAO_LOGIN("user/login/kakao"),
    REFRESH_TOKEN("user/reissue"),
    ADD_USER_INFO("user/info"),

}