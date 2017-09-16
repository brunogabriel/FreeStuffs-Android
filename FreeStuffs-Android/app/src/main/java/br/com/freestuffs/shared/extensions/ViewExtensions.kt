package br.com.freestuffs.shared.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by bruno on 24/08/2017.
 */

// Layout Inflater
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

// Load images
fun ImageView.load(path: String) {
    Glide.with(context).load(path).into(this)
}