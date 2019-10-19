### NeScreenAdapterPixel 自定义像素屏幕适配
自定义自适应屏幕View，只需要给出设计稿的尺寸，根据设计稿标准，自动适应其它各种屏幕。  
`注意：XML文件中的宽高需要用px`  
#### 示例代码如下所示：
##### 1. ScreenAdapterLayout
```android
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!flag) {
            float scaleX = Utils.getInstance(getContext()).getHorizontalScale();
            float scaleY = Utils.getInstance(getContext()).getVerticalScale();

            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                params.width = (int) (params.width * scaleX);
                params.height = (int) (params.height * scaleY);
                params.leftMargin = (int) (params.leftMargin * scaleX);
                params.rightMargin = (int) (params.rightMargin * scaleX);
                params.topMargin = (int) (params.topMargin * scaleY);
                params.bottomMargin = (int) (params.bottomMargin * scaleY);
            }
            flag = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
```
##### 2. Utils
```android
    private static volatile Utils utils;

    //这里是设计稿参考宽高
//    private static final float STANDARD_WIDTH = 720;
//    private static final float STANDARD_HEIGHT = 1280;
    private static final float STANDARD_WIDTH = 1080;
    private static final float STANDARD_HEIGHT = 1920;

    //这里是屏幕先上宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    private Utils(Context context) {
        //获取屏幕的宽高
        if(mDisplayWidth == 0 || mDisplayHeight == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if(manager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics);
                if(displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    mDisplayWidth = displayMetrics.heightPixels;
                    mDisplayHeight = displayMetrics.widthPixels;
                }else { //竖屏
                    mDisplayWidth = displayMetrics.widthPixels;
                    mDisplayHeight = displayMetrics.heightPixels - getStatusBarHeight(context);
                }
            }
        }

    }

    public int getStatusBarHeight(Context context) {
        int resID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resID > 0) {
            return context.getResources().getDimensionPixelSize(resID);
        }
        return 0;
    }

    public static Utils getInstance(Context context) {
        if(utils == null) {
            synchronized (Utils.class) {
                if(utils == null) {
                    utils = new Utils(context.getApplicationContext());
                }
            }
        }
        return utils;
    }

    //获取水平方向上的缩放比例
    public float getHorizontalScale() {
        return mDisplayWidth / STANDARD_WIDTH;
    }

    //获取竖直方向上的缩放比例
    public float getVerticalScale() {
        return mDisplayHeight / STANDARD_HEIGHT;
    }
```
#### 示例如下图：  
![image](https://github.com/tianyalu/NeScreenAdapterPixel/blob/master/show/show.png)
