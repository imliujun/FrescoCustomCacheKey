package info.liujun.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 21:01
 * 修改人：LiuJun
 * 修改时间：16/8/15 21:01
 * 修改备注：
 */
public class LJImageView extends SimpleDraweeView {

    public LJImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }


    public LJImageView(Context context) {
        super(context);
    }


    public LJImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public LJImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LJImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void loadImage(String uri, String cacheKey) {
        loadImage(Uri.parse(uri), cacheKey);
    }


    public void loadImage(Uri uri, String cacheKey) {
        LJImageRequest request = LJImageRequestBuilder
                .newBuilderWithSource(uri, cacheKey).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                                            .setImageRequest(request)
                                            .setOldController(getController())
                                            .build();
        setController(controller);
    }
}
