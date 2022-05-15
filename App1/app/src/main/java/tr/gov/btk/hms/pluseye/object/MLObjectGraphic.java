package tr.gov.btk.hms.pluseye.object;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

import com.huawei.hms.mlsdk.objects.MLObject;
import tr.gov.btk.hms.pluseye.camera.GraphicOverlay;

public class MLObjectGraphic extends GraphicOverlay.Graphic {
    private static final float TEXT_SIZE = 54.0f;
    private static final float STROKE_WIDTH = 4.0f;
    private final MLObject object;
    private final Paint boxPaint;
    private final Paint textPaint;

    MLObjectGraphic(GraphicOverlay overlay, MLObject object) {
        super(overlay);

        this.object = object;

        this.boxPaint = new Paint();
        this.boxPaint.setColor(Color.WHITE);
        this.boxPaint.setStyle(Style.STROKE);
        this.boxPaint.setStrokeWidth(MLObjectGraphic.STROKE_WIDTH);

        this.textPaint = new Paint();
        this.textPaint.setColor(Color.WHITE);
        this.textPaint.setTextSize(MLObjectGraphic.TEXT_SIZE);
    }

    @Override
    public void draw(Canvas canvas) {
        // draw the object border.
        RectF rect = new RectF(this.object.getBorder());
        rect.left = this.translateX(rect.left);
        rect.top = this.translateY(rect.top);
        rect.right = this.translateX(rect.right);
        rect.bottom = this.translateY(rect.bottom);
        canvas.drawRect(rect, this.boxPaint);
        // draw other object info.
        canvas.drawText(MLObjectGraphic.getCategoryName(this.object.getTypeIdentity()), rect.left, rect.bottom, this.textPaint);
    }

    private static String getCategoryName(int category) {
        switch (category) {
            case MLObject.TYPE_OTHER:
                return "Unknown";
            case MLObject.TYPE_FURNITURE:
                return "Home good";
            case MLObject.TYPE_GOODS:
                return "Fashion good";
            case MLObject.TYPE_PLACE:
                return "Place";
            case MLObject.TYPE_PLANT:
                return "Plant";
            case MLObject.TYPE_FOOD:
                return "Food";
            case MLObject.TYPE_FACE:
                return "Face";
            default:
        }
        return "";
    }
}
