package com.example.top10appsdownloader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.zip.Inflater


class ViewHondler(v:View){
    val header: TextView = v.findViewById(R.id.header)
    val owner: TextView = v.findViewById(R.id.owner)
    val summaryy: TextView = v.findViewById(R.id.summaryy)
}
class Feed_record( context:Context, private var resource:Int,private var applications: List<MainActivity.FeedEntry>):
    ArrayAdapter<MainActivity.FeedEntry>(context,resource) {
        private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHondler: ViewHondler
        if (convertView == null){
            view = inflater.inflate(resource,parent,false)
            viewHondler = ViewHondler(view)
            view.tag = viewHondler
        }else{
            view = convertView
            viewHondler = view.tag as ViewHondler
        }

//        val header: TextView = view.findViewById(R.id.header)
//        val owner: TextView = view.findViewById(R.id.owner)
//        val summaryy: TextView = view.findViewById(R.id.summaryy)

        val currentapp = applications[position]

        viewHondler.header.text = currentapp.name
        viewHondler.owner.text = currentapp.artist
        viewHondler.summaryy.text = currentapp.summary

        return view

    }
}