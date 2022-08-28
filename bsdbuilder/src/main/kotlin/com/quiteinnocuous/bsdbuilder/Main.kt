package com.quiteinnocuous.bsdbuilder

import org.w3c.dom.Node
import org.w3c.dom.NodeList
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
        it.getElementsByTagName("catalogue")[0].attributes.getNamedItem("id").nodeValue
    }

    idsToDoms.forEach { (id, dom) ->
        val catalogue = dom.getElementsByTagName("catalogue")[0]
        val name = catalogue.attributes?.let {
            // Skip non-libraries
            if (it.getNamedItem("library").nodeValue?.toString().toBoolean()) {
                return@forEach
            }
            it.getNamedItem("name").nodeValue
        }
        val entryLinks = dom.getElementsByTagName("entryLinks")[0]
        println("$id => $name : ${entryLinks.childNodes.length}")
        val catalogueDoms = (dom.getElementsByTagName("catalogueLinks").takeIf {
            it.length == 1
        }?.let {
            nodeList ->
            nodeList[0].childNodes.asSequence().skipText().map {
                val catalogueLinkId = it.attributes.getNamedItem("targetId").nodeValue
                idsToDoms[catalogueLinkId]!!
            }.toList()
        } ?: listOf()) + listOf(dom)

        entryLinks.childNodes.asSequence().skipText().forEach {
            val entryLinkName = it.attributes.getNamedItem("name").nodeValue
            val targetId = it.attributes.getNamedItem("targetId").nodeValue ?: throw IllegalStateException("Could not find targetId for $entryLinkName")

//            val target = idsToDoms.values.fold(null as Element?) {
//                acc, nextDom ->
//                acc ?: nextDom.getElementById(targetId)
//            }
            entryLinks.childNodes.asSequence().skipText().forEach {
                odd ->
                println(odd.attributes.getNamedItem("name").nodeValue)
            }
            val target = dom.getElementById(targetId)
            println(targetId)
            target!!
            println("  $entryLinkName $targetId")
        }
    }
}

fun NodeList.asSequence(): Sequence<Node> = generateSequence(Pair(0, item(0))) {
    (i, _) ->
    if (i < length - 1) { Pair(i+1, item(i+1)) } else { null }
}.map { it.second }

fun NodeList.forEach(action: (Node) -> Unit) {
    for (i in 0 until length) {
        action(item(i))
    }
}

operator fun NodeList.get(i: Int): Node = item(i)

fun Sequence<Node>.skipText() = filter { it.nodeName != "#text" }