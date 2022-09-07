package com.quiteinnocuous.bsdbuilder.data

data class Catalogue(

    // Attributes
    var id: String,
    var name: String,
    var revision: Int,
    var battleScribeVersion: String,
    var library: Boolean,
    var gameSystemId: String,
    var gameSystemRevision: Int,

    // Children
    var entryLinks: List<EntryLink> = listOf(),
    var catalogueLinks: List<CatalogueLink> = listOf(),
    var sharedSelectionEntries: List<SelectionEntry> = listOf()

)