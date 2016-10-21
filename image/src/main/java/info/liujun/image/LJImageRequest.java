package info.liujun.image;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.imagepipeline.request.ImageRequest;
import javax.annotation.concurrent.Immutable;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 18:04
 * 修改人：LiuJun
 * 修改时间：16/8/15 18:04
 * 修改备注：
 */
@Immutable
public class LJImageRequest extends ImageRequest {

    private final String mCacheKey;


    @Nullable
    public static LJImageRequest fromUri(@Nullable Uri uri, String cacheKey) {
        return uri == null
               ? null
               : LJImageRequestBuilder.newBuilderWithSource(uri, cacheKey)
                                      .build();
    }


    @Nullable
    public static LJImageRequest fromUri(@Nullable String uriString, String cacheKey) {
        return TextUtils.isEmpty(uriString)
               ? null
               : fromUri(Uri.parse(uriString), cacheKey);
    }


    protected LJImageRequest(LJImageRequestBuilder builder) {
        super(builder.getImageRequestBuilder());
        mCacheKey = builder.getCacheKey();
    }


    public String getCacheKey() {
        return mCacheKey;
    }
}
