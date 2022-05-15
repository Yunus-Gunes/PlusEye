package tr.gov.btk.hms.pluseye.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.huawei.hms.mlsdk.common.LensEngine;

import java.util.HashSet;
import java.util.Set;

public class GraphicOverlay extends View {
    private final Object mLock = new Object();

    private int mPreviewWidth;

    private float mWidthScaleFactor = 1.0f;

    private int mPreviewHeight;

    private float mHeightScaleFactor = 1.0f;

    private int mFacing = LensEngine.BACK_LENS;

    private Set<Graphic> mGraphics = new HashSet<>();


    public abstract static class Graphic {
        private GraphicOverlay mOverlay;

        public Graphic(GraphicOverlay overlay) {
            mOverlay = overlay;
        }


        public abstract void draw(Canvas canvas);

        //Adjusts a horizontal value of the supplied value from the preview scale to the view scale.
        public float scaleX(float horizontal) {
            return horizontal * mOverlay.mWidthScaleFactor;
        }

        public float scaleY(float vertical) {
            return vertical * mOverlay.mHeightScaleFactor;
        }

        public float translateX(float x) {
            if (mOverlay.mFacing == LensEngine.FRONT_LENS) {
                return mOverlay.getWidth() - scaleX(x);
            } else {
                return scaleX(x);
            }
        }
        public float translateY(float y) {
            return scaleY(y);
        }

    }

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //Removes all graphics from the overlay.
    public void clear() {
        synchronized (mLock) {
            mGraphics.clear();
        }
        postInvalidate();
    }

    //Adds a graphic to the overlay.
    public void add(Graphic graphic) {
        synchronized (mLock) {
            mGraphics.add(graphic);
        }
        postInvalidate();
    }

    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (mLock) {
            mPreviewWidth = previewWidth;
            mPreviewHeight = previewHeight;
            mFacing = facing;
        }
        postInvalidate();
    }

    //Draws the overlay with its associated graphic objects.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (mLock) {
            if ((mPreviewWidth != 0) && (mPreviewHeight != 0)) {
                mWidthScaleFactor = (float) canvas.getWidth() / (float) mPreviewWidth;
                mHeightScaleFactor = (float) canvas.getHeight() / (float) mPreviewHeight;
            }
            for (Graphic graphic : mGraphics) {
                graphic.draw(canvas);
            }
        }
    }
}
