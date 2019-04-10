package pc.dd.example

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        passwordEditText.onTextChanged { t -> Log.e(javaClass.name, t) }
        passwordEditText.onValidate = { validate ->  Log.e(javaClass.name, validate.toString())}
    }
}
