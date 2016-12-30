package info.liujun.imageload;

import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import info.liujun.image.LJImageRequest;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 项目名称：ImageLoader
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2016/10/21 16:47
 * 修改人：LiuJun
 * 修改时间：2016/10/21 16:47
 * 修改备注：
 */
public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        final PhotoDraweeView photoDraweeView = (PhotoDraweeView) findViewById(R.id.photo_view);

        String imageUrl = "http://heixiucrm-test.img-cn-beijing.aliyuncs" +
                ".com/20160623175055_gift_select_animation_1466675455807" +
                ".gif@195w_195h_1l_1e_1an.gif";
        PipelineDraweeControllerBuilder draweeController = FrescoImageLoader
                .getDraweeController(photoDraweeView, LJImageRequest
                        .fromUri(imageUrl, imageUrl));
        draweeController
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (imageInfo == null || photoDraweeView == null) {
                            return;
                        }
                        photoDraweeView.update(imageInfo.getWidth(), imageInfo
                                .getHeight());
                    }
                });
        photoDraweeView.setController(draweeController.build());
    }
}
