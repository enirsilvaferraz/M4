package com.system.m4.kotlin.tags

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import java.util.*

/**
 * Created by enirs on 30/08/2017.
 * Activity from Tag
 */
class TagListActivity : DialogFragment(), TagContract.View {

    companion object {
        fun instance(x: Any): TagListActivity {
            val dialog : TagListActivity = TagListActivity()
            return dialog
        }
    }

    override fun showError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadData(list: ArrayList<DataTag>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addData(model: DataTag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateData(model: DataTag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeData(model: DataTag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}