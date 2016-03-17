package com.webarch.srmobile.utils.animation;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class AnimationObject {

    private View view;
    private AnimationListenerAdapter animationListenerAdapter;
    private Animator animator;
    private ArrayList<AnimationObject> animationObjectList;

    public AnimationObject(View view, AnimationListenerAdapter animationListenerAdapter) {
        this.view = view;
        this.animationListenerAdapter = animationListenerAdapter;
    }

    public AnimationObject(Animator animator) {
        this.animator = animator;
    }

    public AnimationObject(ArrayList<AnimationObject> animationObjectList) {
        this.animationObjectList = animationObjectList;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void end() {
        if (view != null) {
            Animation animation = view.getAnimation();
            if (animation != null) {
                animation.setAnimationListener(null);

                if (!animation.hasEnded()) {
                    view.clearAnimation();

                    animationListenerAdapter.onAnimationEnd();
                }
            }
        } else if (animator != null) {
            animator.end();
        } else if (animationObjectList != null) {
            for (AnimationObject animationObject : animationObjectList) {
                animationObject.end();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void cancel() {
        if (view != null) {
            Animation animation = view.getAnimation();
            if (animation != null) {
                animation.setAnimationListener(null);
            }

            view.clearAnimation();
        } else if (animator != null) {
            animator.cancel();
        } else if (animationObjectList != null) {
            for (AnimationObject animationObject : animationObjectList) {
                animationObject.cancel();
            }
        }
    }

}
