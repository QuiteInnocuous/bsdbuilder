package com.quiteinnocuous.bsdbuilder

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.quiteinnocuous.bsdbuilder.data.Catalogue
import com.quiteinnocuous.bsdbuilder.data.GameSystem
import com.quiteinnocuous.bsdbuilder.data.SelectionEntry
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

fun main(args: Array<String>) {
    val mapper = XmlMapper.builder().addModule(JacksonXmlModule())
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()
        .registerKotlinModule()

    val pathString = args[0]

    val idToGameSystem = Files.list(Paths.get(pathString)).filter {
        it.fileName.toString().endsWith(".gst")
    }.map {
        mapper.readValue(it.toFile(), GameSystem::class.java)
    }.collect(Collectors.toMap({ it.id }, { it }))

    Files.list(Paths.get(pathString)).filter {
        it.fileName.toString().let { fileName ->
            !fileName.startsWith("ZZ") && fileName.endsWith(".cat") && !fileName.contains("[LEGENDS]")
        }
    }.map {
        mapper.readValue(it.toFile(), Catalogue::class.java)
    }.filter {
        !it.library
    }.forEach {
        catalogue ->
        println(catalogue.name)
        println(catalogue.entryLinks.size)
        println(catalogue.catalogueLinks.size)
        val idToSelectionEntry: Map<String, SelectionEntry> = catalogue.catalogueLinks.map {
            catalogueLink ->
            mapper.readValue(
                Paths.get(pathString, "${catalogueLink.name}.cat").toFile(),
                Catalogue::class.java
            )
        }.let {
            linkedCatalogues ->
            linkedCatalogues + listOf(catalogue)
        }.map {
            it.sharedSelectionEntries
        }.let {
            it + listOf(idToGameSystem[catalogue.gameSystemId]!!.sharedSelectionEntries)
        }.reduce {
            a, b ->
            a + b
        }.associateBy { it.id }
        catalogue.entryLinks.forEach {
            entryLink ->
            val selectionEntry = idToSelectionEntry[entryLink.targetId]
                ?: throw IllegalStateException("Could not find ${entryLink.targetId}: ${entryLink.name}")
            selectionEntry.profiles ?: throw IllegalStateException("Could not find profiles for: ${entryLink.name}")


        }
    }
}
