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
        val catalogue = dom.getElementsByTagName("catalogue").item(0)
        val name = catalogue.attributes?.let {
            // Skip non-libraries
            if (it.getNamedItem("library").nodeValue?.toString().toBoolean()) {
                return@forEach
            }
            it.getNamedItem("name").nodeValue
        }
        val entryLinks = dom.getElementsByTagName("entryLinks").item(0)
        println("$id => $name : ${entryLinks.childNodes.length}")
        for (i in 0 until entryLinks.childNodes.length) {
            val entryLink = entryLinks.childNodes.item(i)
            if (entryLink.nodeName == "#text") {
                continue
            }
            val entryLinkName = entryLink.attributes.getNamedItem("name").nodeValue
            println("  $entryLinkName")
        }
    }
}