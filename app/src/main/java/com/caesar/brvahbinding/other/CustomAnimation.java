package com.caesar.brvahbinding.other;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import com.chad.library.adapter.base.animation.BaseAnimation;

import org.jetbrains.annotations.NotNull;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CustomAnimation implements BaseAnimation {

//    @Override
//    public Animator[] getAnimators(View view) {
//        return new Animator[]{
//                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
//                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
//        };
//    }

    @NotNull
    @Override
    public Animator[] animators(@NotNull View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        };
    }
}
