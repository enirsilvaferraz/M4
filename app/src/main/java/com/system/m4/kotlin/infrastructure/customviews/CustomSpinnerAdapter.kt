package com.system.m4.kotlin.infrastructure.customviews

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.system.m4.kotlin.tags.TagModel

/**
 * Created by eferraz on 05/09/17.
 * Custom Spinner Adapter
 */
class CustomSpinnerAdapter(context: Context?, val resource: Int, val objects: ArrayList<TagModel>) : ArrayAdapter<TagModel>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getView(position, convertView, parent)
        return getCustomView(view, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = super.getDropDownView(position, convertView, parent)
        return getCustomView(view, position)
    }

    private fun getCustomView(view: View, position: Int): View {
        val textView = view.findViewById(android.R.id.text1) as TextView
        textView.text = objects.get(position).name
        return view
    }
}