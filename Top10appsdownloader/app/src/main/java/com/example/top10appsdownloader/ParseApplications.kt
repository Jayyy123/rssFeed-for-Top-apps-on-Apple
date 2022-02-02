package com.example.top10appsdownloader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    val appplications  = ArrayList<MainActivity.FeedEntry>()
    fun parse(xmlData:String):Boolean{
        var status = true
        var inEntry = false
        var textValue = ""

        try{

            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var eventType = xpp.eventType
            var currentRecord = MainActivity.FeedEntry()
            while (eventType != XmlPullParser.END_DOCUMENT){
                val tagName = xpp.name?.toLowerCase()

                when(eventType){
                    XmlPullParser.START_TAG -> {
                        if(tagName == "entry"){
                            inEntry = true
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text
                    XmlPullParser.END_TAG -> {
                        if(inEntry){
                            when(tagName){
                                "entry" -> {
                                    appplications.add(currentRecord)
                                    inEntry = false
                                    currentRecord = MainActivity.FeedEntry()
                                }
                                "name" -> currentRecord.name = textValue
                                "artist" -> currentRecord.artist = textValue
                                "releasedate" -> currentRecord.releaseDate = textValue
                                "summary" -> currentRecord.summary = textValue
                                "image" -> currentRecord.imageUrl = textValue
                            }
                        }
                    }
                }
                eventType = xpp.next()
            }
            for(app in appplications){

            }

        }catch (e:Exception){
            e.printStackTrace()
            status = false
        }
        return status
    }
}