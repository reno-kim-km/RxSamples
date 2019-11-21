package com.reno.rxsamples

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    private var count1 = 0
    private var count2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable1 = createButtonClickObservable(button1)
        val observable2 = createButtonClickObservable(button2)

        disposable = Observable.combineLatest(
            observable1,
            observable2,
            BiFunction<Int, Int, String> { button1, button2 ->
                "$button1 x $button2 = ${button1 * button2}"
            })
            .subscribe {
                textView.text = it
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!disposable.isDisposed)
            disposable.dispose()
    }

    private fun createButtonClickObservable(
        button: Button
    ): Observable<Int> {
        return Observable.create<Int> { emitter ->
            button.setOnClickListener {
                val count = if (button.id == R.id.button1) {
                    count1 += 1
                    count1
                } else {
                    count2 += 1
                    count2
                }

                emitter.onNext(count)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.next -> {
                startActivity(Intent(this, IterableActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
