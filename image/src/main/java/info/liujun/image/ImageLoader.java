package info.liujun.image;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 16:48
 * 修改人：LiuJun
 * 修改时间：16/8/15 16:48
 * 修改备注：
 */
public class ImageLoader {

    public static RoundingParams getRoundingParamsAsCircle() {
        return RoundingParams.asCircle();
    }


    public static RoundingParams getRoundingParams(float radius) {
        return RoundingParams.fromCornersRadius(radius);
    }


    public static RoundingParams getRoundingParams(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        return RoundingParams
                .fromCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
    }


    public static LJImageRequest getImageRequest(SimpleDraweeView view, String uri, String cacheKey) {
        return getImageRequest(view, uri, cacheKey, ImageRequest.CacheChoice.DEFAULT);
    }


    public static LJImageRequest getImageRequest(SimpleDraweeView view, String uri, String cacheKey, @Nullable ImageRequest.CacheChoice cacheChoice) {
        if (cacheChoice == null) {
            cacheChoice = ImageRequest.CacheChoice.DEFAULT;
        }

        LJImageRequestBuilder builder = LJImageRequestBuilder
                .newBuilderWithSource(Uri.parse(uri), cacheKey);
        ImageRequestBuilder requestBuilder = builder.getImageRequestBuilder();
        requestBuilder.setAutoRotateEnabled(true)//自动旋转图片方向
                      .setCacheChoice(cacheChoice)//图片类型，设置后可调整图片放入小图磁盘空间还是默认图片磁盘空间
                      .setLocalThumbnailPreviewsEnabled(true)//缩略图预览，影响图片显示速度（轻微）
                      .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)//请求经过缓存级别
                      .setProgressiveRenderingEnabled(true);//渐进加载，主要用于渐进式的JPEG图，影响图片显示速度（普通）

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0 &&
                layoutParams.height > 0) {
            ResizeOptions resizeOptions = new ResizeOptions(layoutParams.width, layoutParams.height);
            requestBuilder.setResizeOptions(resizeOptions);//调整图片大小
        }

        return builder.build();
    }


    /**
     * 获取DraweeController
     *
     * @param imageRequest 设置单个图片请求
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, ImageRequest imageRequest) {
        return getDraweeController(view, imageRequest, null, false);
    }


    /**
     * 获取DraweeController
     *
     * @param imageRequest 设置单个图片请求
     * @param controllerListener 监听图片下载完毕等
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, ImageRequest imageRequest, @Nullable ControllerListener<ImageInfo> controllerListener) {
        return getDraweeController(view, imageRequest, null, null, controllerListener, false);
    }


    /**
     * 获取DraweeController
     *
     * @param imageRequest 高分辨率的图
     * @param lowResImageRequest 低分辨率的图
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, ImageRequest imageRequest, @Nullable ImageRequest lowResImageRequest) {
        return getDraweeController(view, imageRequest, lowResImageRequest, null, null, false);
    }


    /**
     * 获取DraweeController
     *
     * @param imageRequest 高分辨率的图
     * @param lowResImageRequest 低分辨率的图
     * @param tapToRetryEnabled 点击重新加载图
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, ImageRequest imageRequest, @Nullable ImageRequest lowResImageRequest, boolean tapToRetryEnabled) {
        return getDraweeController(view, imageRequest, lowResImageRequest, null, null, tapToRetryEnabled);
    }


    /**
     * 获取DraweeController
     *
     * @param firstAvailableImageRequests 本地图片复用，可加入ImageRequest数组
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, @Nullable ImageRequest[] firstAvailableImageRequests) {
        return getDraweeController(view, null, null, firstAvailableImageRequests, null, false);
    }


    /**
     * 获取DraweeController
     *
     * @param imageRequest 设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
     * @param lowResImageRequest 先下载显示低分辨率的图
     * @param firstAvailableImageRequests 本地图片复用，可加入ImageRequest数组
     * @param controllerListener 监听图片下载完毕等
     * @param tapToRetryEnabled 点击重新加载图
     */
    public static PipelineDraweeControllerBuilder getDraweeController(SimpleDraweeView view, @Nullable ImageRequest imageRequest, ImageRequest lowResImageRequest, @Nullable ImageRequest[] firstAvailableImageRequests, @Nullable ControllerListener<ImageInfo> controllerListener, boolean tapToRetryEnabled) {
        PipelineDraweeControllerBuilder builder = Fresco
                .newDraweeControllerBuilder();
        //自动播放图片动画
        builder.setAutoPlayAnimations(true);
        //点击重新加载图
        builder.setTapToRetryEnabled(tapToRetryEnabled);
        if (firstAvailableImageRequests != null &&
                firstAvailableImageRequests.length > 0) {
            //本地图片复用，可加入ImageRequest数组
            builder.setFirstAvailableImageRequests(firstAvailableImageRequests);
        } else if (imageRequest != null) {
            //设置单个图片请求～～～不可与setFirstAvailableImageRequests共用，配合setLowResImageRequest为高分辨率的图
            builder.setImageRequest(imageRequest);
            if (lowResImageRequest != null) {
                //先下载显示低分辨率的图
                builder.setLowResImageRequest(lowResImageRequest);
            }
        }
        if (controllerListener != null) {
            //监听图片下载完毕等
            builder.setControllerListener(controllerListener);
        }
        //DraweeController复用
        builder.setOldController(view.getController());
        return builder;
    }


    public static void loadImage(SimpleDraweeView view, LJImageRequest imageRequest) {
        DraweeController draweeController = getDraweeController(view, imageRequest)
                .build();
        view.setController(draweeController);
    }


    public static void loadImage(SimpleDraweeView view, ImageRequest[] firstAvailableImageRequests) {
        DraweeController draweeController = getDraweeController(view, firstAvailableImageRequests)
                .build();
        view.setController(draweeController);
    }


    public static void loadImage(SimpleDraweeView view, ImageRequest imageRequest, ImageRequest lowResImageRequest) {
        DraweeController draweeController = getDraweeController(view, imageRequest, lowResImageRequest, false)
                .build();
        view.setController(draweeController);
    }


    public static void loadImage(SimpleDraweeView view, String uri, String cacheKey, boolean tapToRetryEnabled, ImageRequest.CacheChoice cacheChoice) {
        LJImageRequest imageRequest = getImageRequest(view, uri, cacheKey, cacheChoice);
        DraweeController draweeController = getDraweeController(view, imageRequest, null, tapToRetryEnabled)
                .build();
        view.setController(draweeController);
    }


    public static void loadImage(SimpleDraweeView view, String uri, String cacheKey, boolean tapToRetryEnabled) {
        loadImage(view, uri, cacheKey, tapToRetryEnabled, ImageRequest.CacheChoice.DEFAULT);
    }


    public static void loadImage(SimpleDraweeView view, String uri, String cacheKey, ImageRequest.CacheChoice cacheChoice) {
        LJImageRequest imageRequest = getImageRequest(view, uri, cacheKey, cacheChoice);
        loadImage(view, imageRequest);
    }


    public static void loadImage(SimpleDraweeView view, String uri, String cacheKey) {
        LJImageRequest imageRequest = getImageRequest(view, uri, cacheKey);
        loadImage(view, imageRequest);
    }
}
