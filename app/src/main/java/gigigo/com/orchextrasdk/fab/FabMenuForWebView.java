package gigigo.com.orchextrasdk.fab;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.gigigo.orchextra.Orchextra;

//import gigigo.com.fabmenu.FloatingActionsMenu;
//import gigigo.com.fabmenu.FloatingActionButton;
import gigigo.com.orchextrasdk.R;

/**
 * This class manage the FabMenu in WebViewActivity
 */
public class FabMenuForWebView { //extends RelativeLayout {
//
//    FrameLayout frameOverLay;
//    RelativeLayout mnuBack;
//    FloatingActionsMenu mnuFAB;
//    FloatingActionButton btnCodigo;
//    FloatingActionButton btnQR;
//    FloatingActionButton btnImagen;
//
//    private FragmentActivity activity;
//
//    public boolean showMnuBack = true;
//    int screenH;
//
//    public FabMenuForWebView(Context context) {
//        super(context);
//        this.activity = (FragmentActivity) context;
//        init();
//    }
//
//    public FabMenuForWebView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.activity = (FragmentActivity) context;
//        init();
//    }
//
//    public FabMenuForWebView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.activity = (FragmentActivity) context;
//        init();
//    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public FabMenuForWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.activity = (FragmentActivity) context;
//        init();
//    }
//
//    private void init() {
//        initViews();
//        setListeners();
//    }
//
//    private void initViews() {
//        LayoutInflater inflater =
//                (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if(Build.VERSION.SDK_INT>17) {
//            View v = inflater.inflate(R.layout.fab_scanners_menu, this, true);
//
//            frameOverLay = (FrameLayout) findViewById(R.id.frmOverMenuButton);
//            mnuBack = (RelativeLayout) findViewById(R.id.MenuBack);
//            mnuFAB = (FloatingActionsMenu) findViewById(R.id.menuFAB);
//            btnCodigo = (FloatingActionButton) findViewById(R.id.btnBarcode);
//            btnQR = (FloatingActionButton) findViewById(R.id.btnQR);
//
//            btnImagen = (FloatingActionButton) findViewById(R.id.btnPhoto);
//
//            mnuBack.setVisibility(View.INVISIBLE);
//            mnuFAB.collapse();
//        }
//    }
//    private void setListeners() {
//        frameOverLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.invalidate();
//                showOrHideMnuLayerBack();
//            }
//        });
//        mnuBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.invalidate();
//                if (!showMnuBack) { showOrHideMnuLayerBack();}
//            }
//        });
//        btnCodigo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showOrHideMnuLayerBack();
//                onScanPressed();
//            }
//        });
//        btnQR.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showOrHideMnuLayerBack();
//                onScanPressed();
//            }
//        });
//        btnImagen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showOrHideMnuLayerBack();
//                onImageScanPressed();
//            }
//        });
//    }
//
//    public void showOrHideMnuLayerBack() {
//        Animation slide = null;
//        screenH = getMaxHeigth();
//        if (showMnuBack) {
//            mnuBack.setVisibility(View.VISIBLE);
//            mnuFAB.expand();
//            slide = new TranslateAnimation(0, 0, screenH,
//                    0);
//        } else {
//            mnuBack.setVisibility(View.INVISIBLE);
//            mnuFAB.collapse();
//            slide = new TranslateAnimation(0, 0, 0, screenH);
//        }
//        slide.setInterpolator(new Interpolator() {
//            @Override
//            public float getInterpolation(float input) {
//                if (input < screenH / 3) {
//                    return 1f;
//                } else {
//                    return 3f;
//                }
//            }
//        });
//        slide.setDuration(3000);
//        slide.setFillAfter(true);
//        slide.setFillEnabled(true);
//        mnuBack.startAnimation(slide);
//
//        slide.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                mnuBack.clearAnimation();
//                RelativeLayout.LayoutParams lp =
//                        new RelativeLayout.LayoutParams(mnuBack.getWidth(), mnuBack.getHeight());
//                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                mnuBack.setLayoutParams(lp);
//            }
//        });
//        showMnuBack = !showMnuBack;
//    }
//
//    private int getMaxHeigth() {
//        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        return display.getHeight();
//    }
//
//    public void onScanPressed() {
//        Orchextra.startScannerActivity();
//    }
//
//    private void onImageScanPressed() {
//       Orchextra.startImageRecognition();
//    }

}
