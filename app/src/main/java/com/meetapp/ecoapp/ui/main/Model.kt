package com.meetapp.ecoapp.ui.main

object Model {
    data class Result(val query: Query)
    data class Query(val search: List<Element>)
    data class Element(val title: String, val snippet: String)
}

data class Codebeautify(var batchcomplete:String,
                        var `continue`:Continue,
                        var query:Query)
data class Query(  var searchinfo:Searchinfo,
                   internal var search: List<Model.Element> = arrayListOf()
)
class Searchinfo(var totalhits:Int = 0)
class Continue(var sroffset:Float = 0f,
               val `continue`:String)