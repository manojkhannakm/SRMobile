package com.webarch.srmobile.views.quickaccess;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.webarch.srmobile.R;
import com.webarch.srmobile.entities.User;
import com.webarch.srmobile.utils.animation.AnimationListenerAdapter;
import com.webarch.srmobile.utils.animation.AnimationObject;
import com.webarch.srmobile.utils.animation.AnimationUtils;
import com.webarch.srmobile.utils.animation.ColorAnimation;
import com.webarch.srmobile.views.Text;
import com.webarch.srmobile.views.colorbuttons.BaseColorButton;
import com.webarch.srmobile.views.colorbuttons.ColorButton;

/**
 * @author Manoj Khanna
 */

public class UserButton extends RelativeLayout implements ColorButton, ColorAnimation.OnColorChangeListener {

    private static final int STATE_SIGNED_OUT = 0;
    private static final int STATE_SIGNING_IN = 1;
    private static final int STATE_SIGNED_IN = 2;
    private BaseColorButton baseColorButton;
    private ImageView unknownUserPictureImageView, userPictureImageView;
    private Text signInText, signingInText, userNameText, signOutText;
    private OnClickListener onClickListener;
    private AnimationObject crossFadeAnimationObject;
    private int state;

    public UserButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(getContext(), R.layout.quick_access_pane_user_button, this);

        baseColorButton = new BaseColorButton(this, Color.TRANSPARENT, getResources().getColor(R.color.dark));
        unknownUserPictureImageView = (ImageView) findViewById(R.id.quick_access_pane_user_button_unknown_user_picture);
        userPictureImageView = (ImageView) findViewById(R.id.quick_access_pane_user_button_user_picture);
        signInText = (Text) findViewById(R.id.quick_access_pane_user_button_sign_in);
        signingInText = (Text) findViewById(R.id.quick_access_pane_user_button_signing_in);
        userNameText = (Text) findViewById(R.id.quick_access_pane_user_button_user_name);
        signOutText = (Text) findViewById(R.id.quick_access_pane_user_button_sign_out);

        setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onClickListener.onUserClicked();
            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return baseColorButton.onTouchEvent(event);
    }

    @Override
    public void onColorChanged(int color) {
        setBackgroundColor(color);
    }

    public void signingIn() {
        if (state == STATE_SIGNING_IN) {
            return;
        }
        state = STATE_SIGNING_IN;

        setClickable(false);

        if (crossFadeAnimationObject != null) {
            crossFadeAnimationObject.end();
        }
        crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{signingInText},
                new View[]{signInText}, 0, null);
    }

    public void signIn() {
        if (state == STATE_SIGNED_IN) {
            return;
        }
        state = STATE_SIGNED_IN;

        User user = User.getUser();
        userPictureImageView.setImageBitmap(user.getPictureBitmap());
        userNameText.setText(user.getName());

        if (crossFadeAnimationObject != null) {
            crossFadeAnimationObject.end();
        }
        crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{userPictureImageView, userNameText, signOutText},
                new View[]{signingInText, unknownUserPictureImageView, signInText}, 0,
                new AnimationListenerAdapter() {

                    @Override
                    public void onAnimationEnd() {
                        setClickable(true);
                    }

                }
        );
    }

    public void signOut() {
        if (state == STATE_SIGNED_OUT) {
            return;
        }
        state = STATE_SIGNED_OUT;

        if (crossFadeAnimationObject != null) {
            crossFadeAnimationObject.end();
        }
        crossFadeAnimationObject = AnimationUtils.crossFade(new View[]{unknownUserPictureImageView, signInText},
                new View[]{signingInText, userPictureImageView, userNameText, signOutText}, 0,
                new AnimationListenerAdapter() {

                    @Override
                    public void onAnimationEnd() {
                        setClickable(true);

                        userPictureImageView.setImageDrawable(null);
                        userNameText.setText(null);
                    }

                }
        );
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {

        public void onUserClicked();

    }

}
