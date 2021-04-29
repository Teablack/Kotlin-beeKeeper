package com.example.beekeeper.model

class Hive {

    lateinit var apiaryID:String
    var hiveID: String? =null
    lateinit var hiveName:String
    lateinit var hiveType:String
    lateinit var queenbee :String
    lateinit var queenPersonality :String
    lateinit var frameCount :String
    lateinit var actualFrameCount :String
    lateinit var honeybees :String


    constructor(
        apiaryID: String?, hiveID: String?, hiveName:String, hiveType:String, queenbee:String,
        queenPersonality:String,
        frameCount:String,
        actualFrameCount:String,
        honeybees:String) {
        if (apiaryID != null) {
            this.apiaryID = apiaryID
        }
        if (hiveID != null) {
            this.hiveID = hiveID
        }
        this.hiveName = hiveName
        this.hiveType = hiveType
        this.queenbee = queenbee

        this.queenPersonality = queenPersonality
        this.frameCount = frameCount
        this.actualFrameCount = actualFrameCount
        this.honeybees = honeybees
    }

    constructor()
}