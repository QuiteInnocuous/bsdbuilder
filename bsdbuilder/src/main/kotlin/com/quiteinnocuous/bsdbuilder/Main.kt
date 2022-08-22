package com.quiteinnocuous.bsdbuilder

import com.squareup.kotlinpoet.FunSpec
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.*
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {

    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    // Arbitrary classloader
    Files.list(Paths.get(args[0])).filter {
        it.toString().endsWith(".cat")
    }.map {
        with(
            FileInputStream(it.toString()),
            documentBuilder::parse
        )
    }.forEach {
        println(it.childNodes.length)
    }
}