package pc.dd.password_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.widget.AppCompatImageView
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import kotlin.properties.Delegates


/**
 * by Dmitry Dyachenko and leaditteam.com
 */
class PasswordEditText(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    /**
     * initialize views
     */
    private var editText: EditText? = null
    private var numberPresentIcon: AppCompatImageView? = null
    private var upperCasePresentIcon: AppCompatImageView? = null
    private var specialCharacterPresentIcon: AppCompatImageView? = null
    private var lengthPresentIcon: AppCompatImageView? = null
    private var progressCardView: ProgressCardView? = null
    private var bottomTips: LinearLayout? = null

    /**
     * init state variables
     */
    private var showBottomTips: Boolean = true
    private var cardProgressColor: Int? = null
    private var numberIconState: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old)
            changeIconState(numberPresentIcon!!, new)

    }
    private var upperCaseIconState: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old)
            changeIconState(upperCasePresentIcon!!, new)

    }
    private var lengthIconState: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old)
            changeIconState(lengthPresentIcon!!, new)

    }
    private var specialCharacterIconState: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old)
            changeIconState(specialCharacterPresentIcon!!, new)

    }

    var onValidate: ((Boolean) -> Unit)? = null
    private var validate: Boolean by Delegates.observable(false) { _, old, new ->
        if (new != old)
            onValidate?.invoke(new)
    }

    /**
     * inflate
     */
    init {
        initView()
        setAttr(context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText))

        bottomTips?.visibility = if (showBottomTips) View.VISIBLE else View.GONE
        progressCardView?.setProgressColorFromResources(cardProgressColor!!)

        editText?.onTextChanged { t ->
            val validateInt = validate(t)
            validate = validateInt == 4
            progressCardView?.progress = validateInt
        }
    }

    private fun initView() {
        inflate(context, R.layout.password_edit_text, this)

        editText = findViewById(R.id.sampleEditText)
        numberPresentIcon = findViewById(R.id.number_icon)
        upperCasePresentIcon = findViewById(R.id.cap_letter_icon)
        specialCharacterPresentIcon = findViewById(R.id.symbol_icon)
        lengthPresentIcon = findViewById(R.id.length_icon)
        progressCardView = findViewById(R.id.progressCardView)
        bottomTips = findViewById(R.id.bottom_tips)
    }

    private fun setAttr(attributes: TypedArray) {
        showBottomTips = attributes.getBoolean(R.styleable.PasswordEditText_showBottomTips, true)
        cardProgressColor = attributes.getColor(R.styleable.PasswordEditText_cardProgressColor, Color.GREEN)
        attributes.recycle()
    }

    /**
     * validate text
     */
    private fun validate(input: String): Int {
        val specialChars = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?"
        var currentCharacter: Char
        var result = 0
        var numberPresent = false
        var upperCasePresent = false
        var lengthPresent = false
        var specialCharacterPresent = false

        for (i in 0 until input.length) {
            currentCharacter = input[i]
            when {
                specialChars.contains(currentCharacter.toString()) -> specialCharacterPresent = true
                Character.isDigit(currentCharacter) -> numberPresent = true
                Character.isUpperCase(currentCharacter) -> upperCasePresent = true
                input.length >= 8 -> lengthPresent = true
            }
        }

        if (numberPresent)
            ++result

        if (upperCasePresent)
            ++result

        if (lengthPresent)
            ++result

        if (specialCharacterPresent)
            ++result

        numberIconState = numberPresent
        upperCaseIconState = upperCasePresent
        lengthIconState = lengthPresent
        specialCharacterIconState = specialCharacterPresent

        return result
    }

    private fun changeIconState(view: AppCompatImageView, state: Boolean) {
        val animFromDoneToClose: AnimatedVectorDrawableCompat? =
            AnimatedVectorDrawableCompat.create(context, R.drawable.animated_from_done_to_close)
        val animFromCloseToDone: AnimatedVectorDrawableCompat? =
            AnimatedVectorDrawableCompat.create(context, R.drawable.animated_from_close_to_done)
        val animation = if (state) animFromCloseToDone else animFromDoneToClose
        if (animation == view.drawable) return
        view.setImageDrawable(animation)
        animation?.start()
    }

    fun onTextChanged(onTextChanged: (String) -> Unit) {
        editText?.onTextChanged(onTextChanged)
    }

    private fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged.invoke(s.toString())
            }
        })
    }
}
