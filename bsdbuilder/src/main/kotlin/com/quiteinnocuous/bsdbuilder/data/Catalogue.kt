package com.quiteinnocuous.bsdbuilder.data

data class Catalogue(
    var id: String,
    var name: String,
    var library: Boolean,
    var entryLinks: List<EntryLink>?
)