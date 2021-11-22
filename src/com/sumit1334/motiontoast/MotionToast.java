package com.sumit1334.motiontoast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Component;

import com.sumit1334.motiontoast.util.Utility;

import java.io.File;

public class MotionToast extends AndroidNonvisibleComponent implements Component {
    private final Context context;
    private final String TAG = "Motion Toast";
    private final Utility utility;
    private ScaleAnimation scaleAnimation;
    private View fullToast;
    private TextView title;
    private ImageView imageView;
    private TextView body;
    private int gravity;
    private int bgColor;
    private int titleColor;
    private int bodyColor;
    private int duration;
    private String titleTypeface;
    private String bodyTypeface;

    public MotionToast(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
        this.utility = new Utility(container.$form(), this);
        this.createLayout();
        this.animate();
        Gravity("Bottom");
        this.bgColor = utility.parseColor("#DDFFDDFF");
        Duration("Long");
        TitleColor(Color.BLACK);
        BodyTextColor(COLOR_LTGRAY);
        TitleTypeface("None");
        BodyTypeface("None");
        Log.i(TAG, "MotionToast: Extension Initialised");
    }

    @SimpleProperty(description = "specifies the background color of the toast")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_DKGRAY)
    public void BackgroundColor(int bgColor) {
        this.bgColor = bgColor;
        this.setBackground(bgColor, 20);
    }

    @SimpleProperty
    public int BackgroundColor() {
        return bgColor;
    }

    @SimpleProperty(description = "specifies the body text color of the toast default layout")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_LTGRAY)
    public void BodyTextColor(int color) {
        bodyColor = color;
        body.setTextColor(color);
    }

    @SimpleProperty
    public int BodyTextColor() {
        return bodyColor;
    }

    @SimpleProperty(description = "Set the custom font typeface of the body text shown on default toast")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void BodyTypeface(String typeface) {
        if (!typeface.contains("."))
            return;
        else
            bodyTypeface = typeface;
        body.setTypeface(this.getFont(typeface));
    }

    @SimpleProperty
    public String BodyTypeface() {
        return bodyTypeface;
    }

    @SimpleProperty(description = "specifies the duration of the toasts")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES, defaultValue = "Long", editorArgs = {"Short", "Long"})
    public void Duration(String duration) {
        this.duration = duration.equals("Short") ? 0 : 1;
    }

    @SimpleProperty
    public String Duration() {
        return duration == 1 ? "Long" : "Short";
    }

    @SimpleProperty(description = "specifies the gravity of the toasts")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_CHOICES, defaultValue = "Bottom", editorArgs = {"Top", "Centre", "Bottom"})
    public void Gravity(String gravity) {
        if (gravity.equals("Top"))
            this.gravity = Gravity.TOP;
        else if (gravity.equals("Centre"))
            this.gravity = Gravity.CENTER;
        else
            this.gravity = Gravity.BOTTOM;
    }

    @SimpleProperty
    public String Gravity() {
        return gravity == Gravity.TOP ? "Top" : gravity == Gravity.CENTER ? "Centre" : "Bottom";
    }

    @SimpleProperty(description = "specifies the title color of the default toasts")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_COLOR, defaultValue = DEFAULT_VALUE_COLOR_BLACK)
    public void TitleColor(int bgColor) {
        titleColor = bgColor;
        title.setTextColor(bgColor);
    }

    @SimpleProperty
    public int TitleColor() {
        return titleColor;
    }

    @SimpleProperty(description = "Set the title typeface of the title of the default toast")
    @DesignerProperty(editorType = PropertyTypeConstants.PROPERTY_TYPE_ASSET)
    public void TitleTypeface(String typeface) {
        if (!typeface.contains("."))
            return;
        else
            titleTypeface = typeface;
        title.setTypeface(this.getFont(typeface));
    }

    @SimpleProperty
    public String TitleTypeface() {
        return titleTypeface;
    }

    @SimpleEvent(description = "This event raises when any error occurred")
    public void ErrorOccurred(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorOccurred", error);
    }

    @SimpleFunction(description = "Show a simple layout toast with given icon, title and body text")
    public void ShowToast(String icon, String title, String body) {
        this.updateView(icon, title, body);
    }

    @SimpleFunction(description = "Show any android view component as a toast long and short duration and gravity with x y offset \n" +
            "1 for long duration & 0 for short duration \n" +
            "48 for top gravity & 17 for centre gravity & 80 for bottom gravity")
    public void ShowLayoutAsToast(AndroidViewComponent component, int duration, int gravity, int x, int y) {
        View view = component.getView();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null)
            viewGroup.removeView(view);
        Toast toast = new Toast(context);
        toast.setGravity(gravity, x, y);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    private void animate() {
        scaleAnimation = new ScaleAnimation(1, 0.7f, 1, .7f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        imageView.startAnimation(scaleAnimation);
    }

    private void createLayout() {
//      Main View
        LinearLayout view = new LinearLayout(context);
        LinearLayout layout = new LinearLayout(context);
        view.addView(layout);
        layout.setBackgroundColor(0);
        layout.setVerticalGravity(17);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, (int) px2dp(80));
        params.setMargins(24, 24, 24, 24);
        layout.setLayoutParams(params);
//      Image View
        this.imageView = new ImageView(context);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams((int) px2dp(40), (int) px2dp(40));
        params1.setMarginStart(24);
        imageView.setLayoutParams(params1);
        layout.addView(imageView);
//      body container
        LinearLayout linearLayout = new LinearLayout(context);
        layout.addView(linearLayout);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(-1, -2);
        params2.setMarginStart(24);
        params2.setMarginEnd(8);
        linearLayout.setLayoutParams(params2);
        linearLayout.setGravity(Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setVerticalGravity(16);
//      Title
        this.title = new TextView(context);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(-2, -2);
        params3.setMarginStart(12);
        params3.topMargin = 12;
        title.setLayoutParams(params3);
        title.setTextSize(17);
        linearLayout.addView(title);
//      Body
        this.body = new TextView(context);
        LinearLayout.LayoutParams bodyP = new LinearLayout.LayoutParams(-2, -2);
        bodyP.topMargin = 4;
        bodyP.bottomMargin = 12;
        bodyP.setMarginStart(12);
        bodyP.setMarginEnd(12);
        body.setLayoutParams(bodyP);
        body.setMaxLines(2);
        linearLayout.addView(body);
        linearLayout.setHorizontalGravity(Gravity.LEFT);
        fullToast = view;
        this.setBackground(bgColor, 20);
    }

    private void setBackground(int color, int cornerRadius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(px2dp(cornerRadius));
        fullToast.setBackground(drawable);
    }

    private void updateView(String icon, String text, String body) {
        imageView.startAnimation(scaleAnimation);
        try {
            imageView.setImageDrawable(utility.getIcon(icon));
        } catch (Exception e) {
            Log.e(TAG, "updateView: " + e.getMessage());
            ErrorOccurred(e.getMessage());
        }
        title.setText(text);
        this.body.setText(body);
        Toast toast = new Toast(context);
        toast.setGravity(gravity, 0, 100);
        toast.setDuration(duration);
        toast.setView(fullToast);
        toast.show();
    }

    private float px2dp(int px) {
        return context.getResources().getDisplayMetrics().density * px;
    }

    private Typeface getFont(final String path) {
        if (!path.contains("."))
            return null;
        if (form instanceof ReplForm) {
            String filePath = context.getExternalFilesDir(null).getPath() + "/assets/" + path;
            Log.i(TAG, "getFont: " + filePath);
            File file = new File(filePath);
            return Typeface.createFromFile(file);
        } else
            return Typeface.createFromAsset(context.getAssets(), path);
    }
}
