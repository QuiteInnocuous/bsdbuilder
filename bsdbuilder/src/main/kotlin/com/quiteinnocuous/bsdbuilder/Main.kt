package com.quiteinnocuous.bsdbuilder

import com.squareup.kotlinpoet.FunSpec
import javax.xml.parsers.DocumentBuilderFactory

fun main(args: Array<String>) {

    val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    // Arbitrary classloader
    val dom = with(FunSpec::class.java.classLoader.getResourceAsStream(args[0])) {
        documentBuilder.parse(this)
    }

    println(dom.getElementsByTagName("entryLink").length)
}