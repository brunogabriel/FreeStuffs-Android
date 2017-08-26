package br.com.freestuffs.shared.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by bruno on 24/08/2017.
 */

// Binding
private fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
fun <T: View> Fragment.bind(@IdRes idResource: Int): Lazy<T> = unsafeLazy { view?.findViewById<T>(idResource) as T}
fun <T: View> Activity.bind(@IdRes idResource: Int): Lazy<T> = unsafeLazy { findViewById<T>(idResource) as T}
fun <T: View> RecyclerView.ViewHolder.bind(@IdRes idResource: Int): Lazy<T> = unsafeLazy { itemView?.findViewById<T>(idResource) as T}

// Layout Inflater
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

// Load images
fun ImageView.load(path: String) {
    Glide.with(context).load(path).into(this)
}