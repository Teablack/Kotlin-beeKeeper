package com.example.beekeeper.model

public class Apiary {
    lateinit var userID2:String
    var apiaryID: String? =null
    lateinit var apiaryName:String
    lateinit var localization :String

    constructor(userID2: String, apiaryID: String?, apiaryName:String, localization:String) {
        this.userID2 = userID2
        if (apiaryID != null) {
            this.apiaryID = apiaryID
        }
        this.apiaryName = apiaryName
        this.localization = localization
    }

    constructor()
}