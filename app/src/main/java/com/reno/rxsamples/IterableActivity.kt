package com.reno.rxsamples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_iterable.*

class IterableActivity : AppCompatActivity() {

    private val arrayList = arrayListOf(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iterable)

        tvIterable.text = arrayList.toString()

        disposable = Observable.fromIterable(arrayList)
            .subscribeOn(Schedulers.io())
            .map(::add10)
            .filter(::isThreeTimes)
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::showResult)
    }

    private fun add10(params: Int): Int {
        return params + 10
    }

    private fun isThreeTimes(params: Int): Boolean {
        return params % 3 == 0
    }

    private fun showResult(params: List<Int>) {
        tvTest.text = "result: $params"
    }

    private fun log(message: Int) {
        Log.d("iterable1", message.toString())
    }

    private fun log2(message: Int) {
        Log.d("iterable2", message.toString())
    }
}