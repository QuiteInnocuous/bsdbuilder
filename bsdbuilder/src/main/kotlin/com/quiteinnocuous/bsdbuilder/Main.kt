package com.quiteinnocuous.bsdbuilder

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.quiteinnocuous.bsdbuilder.data.Catalogue
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
//        setDefaultUseWrapper(false)
    val mapper = XmlMapper.builder().addModule(JacksonXmlModule())
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
        .registerKotlinModule()

    Files.list(Paths.get(args[0])).filter {
        it.fileName.toString().let { fileName ->
            !fileName.startsWith("ZZ") && fileName.endsWith(".cat")
        }
    }.map {
        mapper.readValue(it.toFile(), Catalogue::class.java)
    }.filter {
        it.library
    }.forEach {
        println(it.name)
    }
}
