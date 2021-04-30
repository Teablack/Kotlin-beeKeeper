package com.example.beekeeper.model

class Hive {

    lateinit var apiaryID:String
    var hiveID: String? =null
    var hiveName:String ?= null
    var hiveType:String ?= null
    var queenbee :String ?= null
    var queenPersonality :String ?= null
    var frameCount :String ?= null
    var actualFrameCount :String ?= null
    var honeybees :String ?= null
    var nfcID:String ?= null


    constructor(
        apiaryID: String?, hiveID: String?, hiveName:String, hiveType:String, queenbee:String,
        queenPersonality:String,
        frameCount:String,
        actualFrameCount:String,
        honeybees:String, nfcID: String) {
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
        this.nfcID = nfcID
    }

    constructor()
}