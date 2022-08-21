package com.quiteinnocuous.bsdbuilder

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.lang.IllegalArgumentException

class CatalogueHandler : DefaultHandler() {

    enum class Tags {
        CATALOGUE,
        ENTRYLINK
    }

    lateinit var catalogue: Catalogue

    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        super.startElement(uri, localName, qName, attributes)
        val tag = try {
            Tags.valueOf(qName.uppercase())
        } catch (ignore: IllegalArgumentException) {
            // Not a tag we're interested in.
            return
        }

        when(tag) {
            Tags.CATALOGUE -> catalogue = Catalogue()
            Tags.ENTRYLINK -> catalogue.addEntryLink(EntryLink(
                attributes.getValue("id"),
                attributes.getValue("name")
            ))
        }
    }
}