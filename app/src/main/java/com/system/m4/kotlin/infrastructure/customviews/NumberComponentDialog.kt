package com.system.m4.kotlin.infrastructure.customviews

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.system.m4.R


class NumberComponentDialog : DialogFragment() {

    private var mNumber1: String? = null;
    private var mNumber2: String? = null;
    private var mOperator: String? = null;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater?.inflate(R.layout.dialog_number_component, container, false)

        val onClick: (View) -> Unit = { v -> pressKey((v as Button).text.toString()) }

        view?.findViewById<Button>(R.id.btn_0)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_1)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_2)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_3)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_4)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_5)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_6)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_7)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_8)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_9)?.setOnClickListener(onClick)

        view?.findViewById<Button>(R.id.btn_sum)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_minus)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_multiply)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_divide)?.setOnClickListener(onClick)
        view?.findViewById<Button>(R.id.btn_equals)?.setOnClickListener(onClick)

        view?.findViewById<Button>(R.id.btn_back)?.setOnClickListener({ backSpace() })

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun backSpace() {
        if (mNumber1 != null && mOperator == null) {
            if (mNumber1!!.length > 0) {
                mNumber1 = mNumber1!!.substring(0, mNumber1!!.length - 1)
            }
        } else if (mNumber2 != null) {
            if (mNumber2!!.length > 0) {
                mNumber2 = mNumber2!!.substring(0, mNumber2!!.length - 1)
            }
        }
    }

    private fun pressKey(key: String) {

        when (key) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> setValue(key)
            "+", "-", "*", "/" -> setOperator(key)
            "=" -> calcule()
            "." -> concatDot()
        }
    }

    private fun concatDot() {
        if (mNumber1 == null) {
            mNumber1 = "0."
        } else if (mOperator == null) {
            if (!mNumber1!!.contains(".")) {
                mNumber1 += "."
            }
        } else {
            if (!mNumber2!!.contains(".")) {
                mNumber2 += "."
            }
        }
    }

    private fun setOperator(key: String) {
        mOperator = key
    }

    fun setValue(value: String) {
        if (mNumber1 == null) {
            mNumber1 = value
        } else if (mOperator == null) {
            mNumber1 += value
        } else {
            mNumber2 = value
        }
    }

    fun calcule() {

        if (mNumber1 != null && mNumber2 != null && mOperator != null) {

            val val1 = mNumber1!!.toDouble()
            val val2 = mNumber2!!.toDouble()

            val result = when (mOperator) {
                "+" -> val1 + val2
                "-" -> val1 - val2
                "*" -> val1 * val2
                "/" -> val1 / val2
                else -> throw RuntimeException("Operator not found!")
            }

            mNumber1 = result.toString()
            mNumber2 = null
            mOperator = null
        }
    }
}