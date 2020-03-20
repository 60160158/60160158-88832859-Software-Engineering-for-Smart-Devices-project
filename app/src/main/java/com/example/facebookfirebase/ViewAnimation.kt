package com.example.facebookfirebase

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.core.view.ViewCompat.animate
import android.R.attr.rotation
import android.view.View


class ViewAnimation {

    fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }


    fun showInY(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationY = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .translationY(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun showOutY(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 1f
        v.translationY = 0f
        v.animate()
            .setDuration(200)
            .translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    fun showInX(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationX = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .translationX(0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun showOutX(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 1f
        v.translationX = 0f
        v.animate()
            .setDuration(200)
            .translationX(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    fun init(v: View) {
        v.visibility = View.GONE
        v.translationY = v.height.toFloat()
        v.alpha = 0f
    }
}