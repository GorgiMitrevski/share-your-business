package com.example.proektdva;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Ovaa KLASA ja najdov gotova i samo ja implementirav, za SWIPE detector na ListView, posto so obicnata funkcija sto ja imam
// vo drugite ekrani ne raboti na ListView swipe detector

public class SwipeListViewTouchListener implements View.OnTouchListener {
    ////////////////
    private int mSlop;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private long mAnimationTime;

    private ListView mListView;
    private OnSwipeCallback mCallback;
    private int mViewWidth = 1;
    private boolean dismissLeft = true;
    private boolean dismissRight = true;

    private List< PendingSwipeData > mPendingSwipes = new ArrayList< PendingSwipeData >();
    private int mDismissAnimationRefCount = 0;
    private float mDownX;
    private boolean mSwiping;
    private VelocityTracker mVelocityTracker;
    private int mDownPosition;
    private View mDownView;
    private boolean mPaused;


    public interface OnSwipeCallback {
        void onSwipeLeft(ListView listView, int[] reverseSortedPositions);
        void onSwipeRight(ListView listView, int[] reverseSortedPositions);
    }

    public SwipeListViewTouchListener(ListView listView, OnSwipeCallback callback) {
        ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
        mSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mAnimationTime = listView.getContext().getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        mListView = listView;
        mCallback = callback;
    }

    public SwipeListViewTouchListener(ListView listView, OnSwipeCallback callback, boolean dismissLeft, boolean dismissRight) {
        this(listView, callback);
        this.dismissLeft = dismissLeft;
        this.dismissRight = dismissRight;
    }

    public void setEnabled(boolean enabled) {
        mPaused = !enabled;
    }

    public AbsListView.OnScrollListener makeScrollListener() {
        return new AbsListView.OnScrollListener() {
            @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
        }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {}
        };
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (mViewWidth < 2) {
            mViewWidth = mListView.getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            {
                if (mPaused) {
                    return false;
                }

                Rect rect = new Rect();
                int childCount = mListView.getChildCount();
                int[] listViewCoords = new int[2];
                mListView.getLocationOnScreen(listViewCoords);
                int x = (int) motionEvent.getRawX() - listViewCoords[0];
                int y = (int) motionEvent.getRawY() - listViewCoords[1];
                View child;
                for (int i = 0; i < childCount; i++) {
                    child = mListView.getChildAt(i);
                    child.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        mDownView = child;
                        break;
                    }
                }
                if (mDownView != null) {
                    mDownX = motionEvent.getRawX();
                    mDownPosition = mListView.getPositionForView(mDownView);
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                view.onTouchEvent(motionEvent);
                return true;
            }

            case MotionEvent.ACTION_UP:
            {
                if (mVelocityTracker == null) {
                    break;
                }
                float deltaX = motionEvent.getRawX() - mDownX;
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(500);
                float velocityX = Math.abs(mVelocityTracker.getXVelocity());
                float velocityY = Math.abs(mVelocityTracker.getYVelocity());
                boolean swipe = false;
                boolean swipeRight = false;

                if (Math.abs(deltaX) > mViewWidth / 2) {
                    swipe = true;
                    swipeRight = deltaX > 0;
                } else if (mMinFlingVelocity <= velocityX && velocityX <= mMaxFlingVelocity && velocityY < velocityX) {
                    swipe = true;
                    swipeRight = mVelocityTracker.getXVelocity() > 0;
                }
                if (swipe) {
                    final View downView = mDownView;
                    final int downPosition = mDownPosition;
                    final boolean toTheRight = swipeRight;
                    ++mDismissAnimationRefCount;
                    mDownView.animate()
                            .translationX(swipeRight ? mViewWidth : -mViewWidth)
                            .alpha(0)
                            .setDuration(mAnimationTime)
                            .setListener(new AnimatorListenerAdapter() {@
                                    Override
                            public void onAnimationEnd(Animator animation) {
                                performSwipeAction(downView, downPosition, toTheRight, toTheRight ? dismissRight : dismissLeft);
                            }
                            });
                } else {
                    // cancel
                    mDownView.animate()
                            .translationX(0)
                            .alpha(1)
                            .setDuration(mAnimationTime)
                            .setListener(null);
                }
                mVelocityTracker = null;
                mDownX = 0;
                mDownView = null;
                mDownPosition = ListView.INVALID_POSITION;
                mSwiping = false;
                break;
            }
            case MotionEvent.ACTION_MOVE:
            {
                if (mVelocityTracker == null || mPaused) {
                    break;
                }
                mVelocityTracker.addMovement(motionEvent);
                float deltaX = motionEvent.getRawX() - mDownX;
                if (Math.abs(deltaX) > mSlop) {
                    mSwiping = true;
                    mListView.requestDisallowInterceptTouchEvent(true);
                    MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    mListView.onTouchEvent(cancelEvent);
                }
                if (mSwiping) {
                    mDownView.setTranslationX(deltaX);
                    mDownView.setAlpha(Math.max(0f, Math.min(1f,
                            1f - 2f * Math.abs(deltaX) / mViewWidth)));
                    return true;
                }
                break;
            }
        }
        return false;
    }

    class PendingSwipeData implements Comparable < PendingSwipeData > {
        public int position;
        public View view;
        public PendingSwipeData(int position, View view) {
            this.position = position;
            this.view = view;
        }
        @Override
        public int compareTo(PendingSwipeData other) {
            return other.position - position;
        }
    }

    private void performSwipeAction(final View swipeView, final int swipePosition, boolean toTheRight, boolean dismiss) {
        final ViewGroup.LayoutParams lp = swipeView.getLayoutParams();
        final int originalHeight = swipeView.getHeight();
        final boolean swipeRight = toTheRight;

        ValueAnimator animator;
        if (dismiss) {
            animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);
        }
        else {
            animator = ValueAnimator.ofInt(originalHeight, originalHeight - 1).setDuration(mAnimationTime);
        }
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
        public void onAnimationEnd(Animator animation) {
            --mDismissAnimationRefCount;
            if (mDismissAnimationRefCount == 0) {
                Collections.sort(mPendingSwipes);
                int[] swipePositions = new int[mPendingSwipes.size()];
                for (int i = mPendingSwipes.size() - 1; i >= 0; i--) {
                    swipePositions[i] = mPendingSwipes.get(i).position;
                }
                if (swipeRight)
                    mCallback.onSwipeRight(mListView, swipePositions);
                else
                    mCallback.onSwipeLeft(mListView, swipePositions);
                ViewGroup.LayoutParams lp;
                for (PendingSwipeData pendingDismiss: mPendingSwipes) {
                    pendingDismiss.view.setAlpha(1f);
                    pendingDismiss.view.setTranslationX(0);
                    lp = pendingDismiss.view.getLayoutParams();
                    lp.height = originalHeight;
                    pendingDismiss.view.setLayoutParams(lp);
                }
                mPendingSwipes.clear();
            }
        }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            lp.height = (Integer) valueAnimator.getAnimatedValue();
            swipeView.setLayoutParams(lp);
        }
        });
        mPendingSwipes.add(new PendingSwipeData(swipePosition, swipeView));
        animator.start();
    }}
