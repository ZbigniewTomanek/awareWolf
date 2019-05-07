package com.meetapp.ecoapp.ui.definition

object Model {
    data class Result(val query: Query)
    data class Query(val search: List<Element>)
    data class Element(val title: String, val snippet: String)
}