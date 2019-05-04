package me.kevincampos.insurancepolicies.util

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

@BindingAdapter("visibleUnless")
fun bindVisibleUnless(view: View, mustGone: Boolean) {
    view.visibility = if (mustGone) {
        GONE
    } else {
        VISIBLE
    }
}

@BindingAdapter("goneUnless")
fun bindGoneUnless(view: View, mustVisible: Boolean) {
    view.visibility = if (mustVisible) {
        VISIBLE
    } else {
        GONE
    }
}

@BindingAdapter(
    "imageDrawable",
    "imageUrl",
    "imagePlaceholder",
    "circleCropImage",
    "crossFadeImage",
    "overrideImageWidth",
    "overrideImageHeight",
    "imageLoadListener",
    requireAll = false
)
fun bindImage(
    imageView: ImageView,
    imageDrawable: Drawable? = null,
    imageUrl: String? = null,
    placeholder: Int? = null,
    circleCrop: Boolean? = false,
    crossFade: Boolean? = false,
    overrideWidth: Int? = null,
    overrideHeight: Int? = null,
    listener: RequestListener<Drawable>?
) {
    var request = when {
        imageUrl != null -> GlideApp.with(imageView.context).load(imageUrl)
        imageDrawable != null -> GlideApp.with(imageView.context).load(imageDrawable)
        else -> GlideApp.with(imageView.context).load(placeholder)
    }
    if (placeholder != null) {
        request = request.placeholder(placeholder).error(placeholder)
    }
    if (circleCrop == true) {
        request = request.circleCrop()
    }
    if (crossFade == true) {
        request = request.transition(DrawableTransitionOptions.withCrossFade())
    }
    if (overrideWidth != null && overrideHeight != null) {
        request = request.override(overrideWidth, overrideHeight)
    }
    if (listener != null) {
        request = request.listener(listener)
    }
    request.into(imageView)
}