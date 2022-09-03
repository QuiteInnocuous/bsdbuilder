package com.quiteinnocuous.bsdbuilder.data

data class Catalogue(
    var id: String,
    var gameSystemId: String,
    // catalog
    var name: String,
    var library: Boolean,
    var entryLinks: List<EntryLink> = listOf(),
    var catalogueLinks: List<CatalogueLink> = listOf(),
    // library
    var sharedSelectionEntries: List<SelectionEntry> = listOf()

)