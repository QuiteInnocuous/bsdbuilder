package com.quiteinnocuous.bsdbuilder

import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Paths
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.streams.asSequence

fun main(args: Array<String>) {

    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    val idsToDoms = Files.list(Paths.get(args[0])).filter {
        it.fileName.toString().let { fileName ->
            !fileName.startsWith("ZZ") && fileName.endsWith(".cat")
        }
    }.map {
        with(
            FileInputStream(it.toString()),
            documentBuilder::parse
        )
    }.asSequence().associateBy {
        it.getElementsByTagName("catalogue").item(0).attributes.getNamedItem("id").nodeValue
    }

    idsToDoms.forEach { (id, dom) ->
        val name = dom.getElementsByTagName("catalogue").item(0).attributes.getNamedItem("name").nodeValue
        println("$id => $name")
    }
}