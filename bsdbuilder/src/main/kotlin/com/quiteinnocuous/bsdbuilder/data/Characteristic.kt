package com.quiteinnocuous.bsdbuilder.data

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

data class Characteristic(
    var name: String,
    var typeId: String,
    //var value: String
)
