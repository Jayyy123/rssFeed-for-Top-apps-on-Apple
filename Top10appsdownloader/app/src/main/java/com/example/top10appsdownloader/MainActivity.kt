package com.example.top10appsdownloader

import android.accounts.NetworkErrorException
import android.content.Context
import android.nfc.Tag
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
//private lateinit var xmlListView: ListView
    class FeedEntry{

        var name = ""
        var artist = ""
        var releaseDate = ""
        var summary = ""
        var imageUrl = ""

        override fun toString(): String {
            return """
                name = $name
                artist = $artist
                releaseDate = $releaseDate
                summary = $summary
                imageUrl = $imageUrl
            """.trimIndent()
        }
    }
    private var TAG = "Main Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        xmlListView = findViewById(com.example.top10appsdownloader.R.id.xmlListView)

        Log.d(TAG, "OnCreate: Called")
        val downloadData = DownloadData(this,findViewById(R.id.xmlListView))
        val link = downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")

        Log.d(TAG, "OnCreate:done")

    }
    companion object{
        private class DownloadData(context: Context, listView: ListView): AsyncTask<String,Void,String>(){
            private var TAG = "DownloadData"
            var pContext: Context by Delegates.notNull()
            var pListView:ListView by Delegates.notNull()

            init {
                pContext = context
                pListView = listView
            }


            override fun onPostExecute(result: String) {
                super.onPostExecute(result)
                Log.d(TAG,"OnPostExecute: Called")
                val parseApplications = ParseApplications()
                parseApplications.parse(result)

//                val arrayAdapter = ArrayAdapter<FeedEntry>(pContext, R.layout.list_view, parseApplications.appplications)
                val adapter = Feed_record(pContext,R.layout.list_records,parseApplications.appplications)
                pListView.adapter = adapter
            }

            override fun doInBackground(vararg url: String?): String {
                Log.d(TAG,"DoInBackground: started")
                val rssFeed = downloadXml(url[0])
                Log.d(TAG,"DoInBackground starts with ${url[0]}")
                return rssFeed
            }

            private fun downloadXml(urlPath: String?): String {
                return URL(urlPath).readText()
//                val xmlResult = StringBuilder()
//
//                try {
//                    val url = URL(urlPath)
//                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                    val response = connection.responseCode
//
//                    Log.d(TAG,"The response was $response bytes")
//
////                    val inputStream = connection.inputStream
////                    val inputStreamReader = InputStreamReader(inputStream)
////                    val reader = BufferedReader(inputStreamReader)
////                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
////
////                    val charset = CharArray(500)
////                    var charsRead = 0
////
////                    while (charsRead >= 0 ) {
////                        charsRead = reader.read(charset)
////                        if (charsRead > 0) {
////                            xmlResult.append(String(charset, 0, charsRead))
////                        }
////                    }
////                   reader.close()
//
//                    connection.inputStream.buffered().reader().use { xmlResult.append(it.readText()) }
//
//                    return xmlResult.toString()
//                }catch (e:Exception){
//                    val errormessage: String
//                    when (e){
//
//                        is MalformedURLException -> { e.printStackTrace()
//                            errormessage = "Malformed Url! -> ${e.message}"
//                        }
//                        is IOException -> {e.printStackTrace()
//                            errormessage ="IO exception -> ${e.message}"
//                        }
//                        is SecurityException -> {e.printStackTrace()
//                            errormessage ="Security exception -> ${e.message}"
//                        }
//                        is NetworkErrorException -> {e.printStackTrace()
//                            errormessage ="There is a network error exception:" +
//                                    " -> ${e.message} "
//                        }
//
//                        else -> {e.printStackTrace()
//                            errormessage = "unknown error: ${e.message}"
//                        }
//
//                    }
//                }
//               return ""
            }

        }
    }



}