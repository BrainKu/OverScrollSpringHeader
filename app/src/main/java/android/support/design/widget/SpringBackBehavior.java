package android.support.design.widget;

import android.animation.ValueAnimator;
import android.support.annotation.Px;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by zhengxinwei on 2017/8/1.
 */
public class SpringBackBehavior extends AppBarLayout.Behavior {

    public static final String TAG = SpringBackBehavior.class.getSimpleName();
    private ValueAnimator mSpringRecoverAnimator;
    private int mInitOffset = -300;
    private int mOffsetSpring;
    private static final int MAX_DURATION = 300;

    public void setInitOffset(@Px int initOffset) {
        mInitOffset = -initOffset;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        boolean started = super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes);
        if (started && mSpringRecoverAnimator != null && mSpringRecoverAnimator.isRunning()) {
            mSpringRecoverAnimator.cancel();
        }
        return started;
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target) {
        super.onStopNestedScroll(coordinatorLayout, abl, target);
        animateRecoverBySpring(coordinatorLayout, abl);
    }

    @Override
    void onFlingFinished(CoordinatorLayout parent, AppBarLayout layout) {
        super.onFlingFinished(parent, layout);
        animateRecoverBySpring(parent, layout);
    }

    private void animateRecoverBySpring(final CoordinatorLayout coordinatorLayout, final AppBarLayout abl) {
        mOffsetSpring = getTopAndBottomOffset();
        if (mOffsetSpring <= mInitOffset)
            return;
        int duration = (int) (MAX_DURATION * Math.abs((mOffsetSpring - mInitOffset) / (float)mInitOffset));
        if (mSpringRecoverAnimator == null) {
            mSpringRecoverAnimator = new ValueAnimator();
            mSpringRecoverAnimator.setInterpolator(new DecelerateInterpolator());
            mSpringRecoverAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    updateSpringHeaderHeight(coordinatorLayout, abl, (int) animation.getAnimatedValue());
                }
            });
        } else {
            if (mSpringRecoverAnimator.isRunning()) {
                mSpringRecoverAnimator.cancel();
            }
        }
        mSpringRecoverAnimator.setDuration(duration);
        mSpringRecoverAnimator.setIntValues(mOffsetSpring, mInitOffset);
        mSpringRecoverAnimator.start();
    }

    private void updateSpringHeaderHeight(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int newOffset) {
        mOffsetSpring = newOffset;
        setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, newOffset);
    }
}
