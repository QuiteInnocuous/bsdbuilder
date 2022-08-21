package com.quiteinnocuous.bsdbuilder

import javax.xml.parsers.SAXParserFactory

fun main(args: Array<String>) {
    val factory = SAXParserFactory.newInstance()
    val saxParser = factory.newSAXParser()
    val catalogueHandler = CatalogueHandler()
    with(catalogueHandler.javaClass.classLoader.getResourceAsStream(args[0])) {
        saxParser.parse(this, catalogueHandler)
    }
    println(catalogueHandler.catalogue.entryLinks.size)
}