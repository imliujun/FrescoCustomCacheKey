package info.liujun.image;

import android.text.TextUtils;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;
import javax.annotation.Nullable;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 17:53
 * 修改人：LiuJun
 * 修改时间：16/8/15 17:53
 * 修改备注：
 */
public class LJCacheKeyFactory extends DefaultCacheKeyFactory {

    private static LJCacheKeyFactory sInstance;


    protected LJCacheKeyFactory() {}


    public static synchronized LJCacheKeyFactory getInstance() {
        if (sInstance == null) {
            sInstance = new LJCacheKeyFactory();
        }
        return sInstance;
    }


    @Override
    public CacheKey getBitmapCacheKey(ImageRequest request, Object callerContext) {
        if (request instanceof LJImageRequest) {
            LJImageRequest ljImageRequest = (LJImageRequest) request;
            return new BitmapMemoryCacheKey(getCacheKey(ljImageRequest), ljImageRequest
                    .getResizeOptions(), ljImageRequest
                    .getRotationOptions(), ljImageRequest
                    .getImageDecodeOptions(), null, null, callerContext);
        }
        return super.getBitmapCacheKey(request, callerContext);
    }


    @Override
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request, Object callerContext) {
        if (request instanceof LJImageRequest) {
            LJImageRequest ljImageRequest = (LJImageRequest) request;
            final Postprocessor postprocessor = request.getPostprocessor();
            final CacheKey postprocessorCacheKey;
            final String postprocessorName;
            if (postprocessor != null) {
                postprocessorCacheKey = postprocessor
                        .getPostprocessorCacheKey();
                postprocessorName = postprocessor.getClass().getName();
            } else {
                postprocessorCacheKey = null;
                postprocessorName = null;
            }
            return new BitmapMemoryCacheKey(getCacheKey(ljImageRequest), ljImageRequest
                    .getResizeOptions(), ljImageRequest
                    .getRotationOptions(), ljImageRequest
                    .getImageDecodeOptions(), postprocessorCacheKey, postprocessorName, callerContext);
        }
        return super.getPostprocessedBitmapCacheKey(request, callerContext);
    }


    @Override
    public CacheKey getEncodedCacheKey(ImageRequest request, @Nullable Object callerContext) {
        if (request instanceof LJImageRequest) {
            LJImageRequest ljImageRequest = (LJImageRequest) request;
            String xpCacheKey = getCacheKey(ljImageRequest);
            if (!TextUtils.isEmpty(xpCacheKey)) {
                return new SimpleCacheKey(xpCacheKey);
            }
        }
        return super.getEncodedCacheKey(request, callerContext);
    }


    public String getCacheKey(LJImageRequest imageRequest) {
        return imageRequest.getCacheKey();
    }
}
