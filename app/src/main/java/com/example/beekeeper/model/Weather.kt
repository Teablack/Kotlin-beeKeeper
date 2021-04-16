package com.example.beekeeper.model

class Weather {
    lateinit var timezone:String
    lateinit var temp:String
    lateinit var pressure:String
    lateinit var wind_speed: String

    constructor(timezone: String,temp:String,pressure:String,wind_speed: String ) {
        this.timezone = timezone
        this.temp = temp
        this.pressure = pressure
        this.wind_speed = wind_speed

    }

    constructor()
}