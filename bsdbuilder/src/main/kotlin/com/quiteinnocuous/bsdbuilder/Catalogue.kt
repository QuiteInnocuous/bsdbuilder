package com.quiteinnocuous.bsdbuilder

class Catalogue {

    val entryLinks: MutableList<EntryLink> = mutableListOf()

    fun addEntryLink(entryLink: EntryLink) = entryLinks.add(entryLink)
}