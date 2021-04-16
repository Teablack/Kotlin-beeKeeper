package com.example.beekeeper.model

class User {
    var userID: Int? = 0
    var userName: String? = null
    var password: String? = null

    constructor() {}

    constructor(
        userID: Int?, userName: String,
        password: String
    ) {
        this.userID = userID
        this.userName = userName
        this.password = password
    }
}
