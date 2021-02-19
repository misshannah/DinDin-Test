package com.hannah.application.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.hannah.application.R
import com.squareup.picasso.Picasso

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class BasicRow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleView: TextView
    private val subtitleView: TextView
    private val imageView: ImageView
    private val button: Button

    init {
        inflate(context, R.layout.basic_row, this)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        imageView = findViewById(R.id.user_list_profile_image)
        button = findViewById(R.id.button)
        orientation = VERTICAL
    }

    @TextProp
    fun setTitle(title: CharSequence) {
        //titleView.text = title
        titleView.text = "Pizza"
    }

    @TextProp
    fun setSubtitle(subtitle: CharSequence?) {
        subtitleView.visibility = if (subtitle.isNullOrBlank()) View.GONE else View.VISIBLE
        subtitleView.text = "Vegeterian"
    }

    @CallbackProp
    fun setImage(url: CharSequence?) {
        Picasso.get().load(url.toString()).into(imageView)
    }


    @CallbackProp
    fun setClickListener(clickListener: OnClickListener?) {
        setOnClickListener(clickListener)
        button.setOnClickListener { button.setBackgroundColor(Color.GREEN)}

        }
}