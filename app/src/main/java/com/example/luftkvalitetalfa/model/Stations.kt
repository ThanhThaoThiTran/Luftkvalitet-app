package com.example.luftkvalitetalfa.model

class Stations(val eoi: String, val name: String, val longitude: String, val grunnkrets: Grunnkrets,
               val kommune: Kommune, val delomrade: Delomrade, val height: Float, val latitude: String)

class Delomrade(val areacode: String, val name: String)

class Grunnkrets(val areacode: String, val name: String)

class Kommune(val areacode: String, val name: String)
