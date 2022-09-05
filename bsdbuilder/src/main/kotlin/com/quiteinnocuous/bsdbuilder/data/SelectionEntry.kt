package com.quiteinnocuous.bsdbuilder.data

data class SelectionEntry(
    var id: String,
    var name: String,
    var profiles: List<Profile>?
)