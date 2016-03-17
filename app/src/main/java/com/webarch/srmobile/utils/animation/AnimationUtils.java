package com.webarch.srmobile.utils.animation;

import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;

import java.util.ArrayList;

/**
 * @author Manoj Khanna
 */

public class AnimationUtils {

    public static AnimationObject alpha(View view, float fromAlpha, float toAlpha, long startOffset, long duration, Interpolator interpolator, final AnimationListenerAdapter animationListenerAdapter) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setStartOffset(startOffset);
        alphaAnimation.setDuration(duration);

        if (interpolator != null) {
            alphaAnimation.setInterpolator(interpolator);
        }

        if (toAlpha > 0) {
            alphaAnimation.setFillEnabled(true);
            alphaAnimation.setFillAfter(true);
        }

        if (animationListenerAdapter != null) {
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    animationListenerAdapter.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationListenerAdapter.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

            });
        }

        view.startAnimation(alphaAnimation);
        return new AnimationObject(view, animationListenerAdapter);
    }

    public static AnimationObject fadeIn(final View view, long startOffset, final AnimationListenerAdapter animationListenerAdapter) {
        return alpha(view, 0, 1, startOffset, 250, null, new AnimationListenerAdapter() {

            @Override
            public void onAnimationStart() {
                view.setVisibility(View.VISIBLE);

                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd() {
                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationEnd();
                }
            }

        });
    }

    public static AnimationObject fadeOut(final View view, long startOffset, final AnimationListenerAdapter animationListenerAdapter) {
        return alpha(view, 1, 0, startOffset, 250, null, new AnimationListenerAdapter() {

            @Override
            public void onAnimationStart() {
                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd() {
                view.setVisibility(View.INVISIBLE);

                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationEnd();
                }
            }

        });
    }

    public static AnimationObject crossFade(final View[] inViews, final View[] outViews, long startOffset, AnimationListenerAdapter animationListenerAdapter) {
        ArrayList<AnimationObject> animationObjectList = new ArrayList<>(inViews.length + outViews.length);

        for (View inView : inViews) {
            if (inView.getVisibility() != View.VISIBLE) {
                animationObjectList.add(fadeIn(inView, startOffset, animationListenerAdapter));
                animationListenerAdapter = null;
            }
        }

        for (View outView : outViews) {
            if (outView.getVisibility() == View.VISIBLE) {
                animationObjectList.add(fadeOut(outView, startOffset, animationListenerAdapter));
                animationListenerAdapter = null;
            }
        }

        if (animationObjectList.isEmpty() && animationListenerAdapter != null) {
            animationListenerAdapter.onAnimationStart();
            animationListenerAdapter.onAnimationEnd();
        }

        return new AnimationObject(animationObjectList);
    }

    public static AnimationObject scale(View view, float fromX, float toX, float fromY, float toY, float pivotX, float pivotY, float fromAlpha, float toAlpha, long startOffset, long duration, Interpolator interpolator, final AnimationListenerAdapter animationListenerAdapter) {
        AnimationSet scaleAnimationSet = new AnimationSet(false);

        ScaleAnimation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
        if (interpolator != null) {
            scaleAnimation.setInterpolator(interpolator);
        }
        scaleAnimationSet.addAnimation(scaleAnimation);

        if (fromAlpha != toAlpha) {
            scaleAnimationSet.addAnimation(new AlphaAnimation(fromAlpha, toAlpha));
        }

        scaleAnimationSet.setStartOffset(startOffset);
        scaleAnimationSet.setDuration(duration);

        if (toX > 0 && toY > 0) {
            scaleAnimationSet.setFillEnabled(true);
            scaleAnimationSet.setFillAfter(true);
        }

        if (animationListenerAdapter != null) {
            scaleAnimationSet.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    animationListenerAdapter.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationListenerAdapter.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

            });
        }

        view.startAnimation(scaleAnimationSet);
        return new AnimationObject(view, animationListenerAdapter);
    }

    public static AnimationObject zoomIn(final View view, boolean fadingEnabled, long startOffset, Interpolator interpolator, final AnimationListenerAdapter animationListenerAdapter) {
        return scale(view, 0, 1, 0, 1, 0.5f, 0.5f, 0, fadingEnabled ? 1 : 0, startOffset, 250, interpolator, new AnimationListenerAdapter() {

            @Override
            public void onAnimationStart() {
                view.setVisibility(View.VISIBLE);

                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd() {
                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationEnd();
                }
            }

        });
    }

    public static AnimationObject zoomOut(final View view, boolean fadingEnabled, long startOffset, Interpolator interpolator, final AnimationListenerAdapter animationListenerAdapter) {
        return scale(view, 1, 0, 1, 0, 0.5f, 0.5f, fadingEnabled ? 1 : 0, 0, startOffset, 250, interpolator, new AnimationListenerAdapter() {

            @Override
            public void onAnimationStart() {
                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd() {
                view.setVisibility(View.INVISIBLE);

                if (animationListenerAdapter != null) {
                    animationListenerAdapter.onAnimationEnd();
                }
            }

        });
    }

//    public static void translate() {
//
//    }
//
//    public static void moveIn() {
//
//    }
//
//    public static void moveOut() {
//
//    }
//
//    public static void rotate() {
//
//    }

    public static AnimationObject changeValue(View view, float fromValue, float toValue, long startOffset, Interpolator interpolator, ValueAnimation.OnValueChangeListener onValueChangeListener, final AnimationListenerAdapter animationListenerAdapter) {
        ValueAnimation valueAnimation = new ValueAnimation(fromValue, toValue, onValueChangeListener);
        valueAnimation.setStartOffset(startOffset);
        valueAnimation.setDuration(250);

        if (interpolator != null) {
            valueAnimation.setInterpolator(interpolator);
        }

        if (animationListenerAdapter != null) {
            valueAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    animationListenerAdapter.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationListenerAdapter.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

            });
        }

        view.startAnimation(valueAnimation);
        return new AnimationObject(view, animationListenerAdapter);
    }

    public static AnimationObject changeColor(View view, int fromColor, int toColor, long startOffset, Interpolator interpolator, ColorAnimation.OnColorChangeListener onColorChangedListener, final AnimationListenerAdapter animationListenerAdapter) {
        if (fromColor == Color.TRANSPARENT) {
            fromColor = Color.argb(0, Color.red(toColor), Color.green(toColor), Color.blue(toColor));
        }

        if (toColor == Color.TRANSPARENT) {
            toColor = Color.argb(0, Color.red(fromColor), Color.green(fromColor), Color.blue(fromColor));
        }

        ColorAnimation colorAnimation = new ColorAnimation(fromColor, toColor, onColorChangedListener);
        colorAnimation.setStartOffset(startOffset);
        colorAnimation.setDuration(250);

        if (interpolator != null) {
            colorAnimation.setInterpolator(interpolator);
        }

        if (animationListenerAdapter != null) {
            colorAnimation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    animationListenerAdapter.onAnimationStart();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    animationListenerAdapter.onAnimationEnd();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

            });
        }

        view.startAnimation(colorAnimation);
        return new AnimationObject(view, animationListenerAdapter);
    }

}
