package info.liujun.image;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 18:06
 * 修改人：LiuJun
 * 修改时间：16/8/15 18:06
 * 修改备注：
 */
public class LJImageRequestBuilder {
    private String mCacheKey;
    private ImageRequestBuilder mImageRequestBuilder;


    private LJImageRequestBuilder(ImageRequestBuilder imageRequestBuilder, String cacheKey) {
        mImageRequestBuilder = imageRequestBuilder;
        if (TextUtils.isEmpty(cacheKey)) {
            mCacheKey = imageRequestBuilder.getSourceUri().toString();
        } else {
            mCacheKey = cacheKey;
        }
    }


    /**
     * Creates a new request builder instance. The setting will be done
     * according to the source type.
     *
     * @param uri the uri to fetch
     * @param cacheKey the request image cacheKey
     * @return a new request builder instance
     */
    public static LJImageRequestBuilder newBuilderWithSource(Uri uri, String cacheKey) {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(uri);
        return new LJImageRequestBuilder(imageRequestBuilder, cacheKey);
    }


    /**
     * Creates a new request builder instance for a local resource image.
     * <p/>
     * <p>Only image resources can be used with the image pipeline (PNG, JPG,
     * GIF). Other resource
     * types such as Strings or XML Drawables make no sense in the context of
     * the image pipeline and
     * so cannot be supported. Attempts to do so will throw an
     * {@link java.lang.IllegalArgumentException} when the pipeline tries to
     * decode the resource.
     * <p/>
     * <p>One potentially confusing case is drawable declared in XML (e.g.
     * ShapeDrawable). This is not
     * an image. If you want to display an XML drawable as the main image, then
     * set it as a
     * placeholder and do not set a URI.
     * <p/>
     *
     * @param resId local image resource id.
     * @param cacheKey the request image cacheKey
     * @return a new request builder instance.
     */
    public static LJImageRequestBuilder newBuilderWithResourceId(int resId, String cacheKey) {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithResourceId(resId);
        return new LJImageRequestBuilder(imageRequestBuilder, cacheKey);
    }


    /**
     * Creates a new request builder instance with the same parameters as the
     * imageRequest passed in.
     *
     * @param imageRequest the ImageRequest from where to copy the parameters
     * to
     * the builder.
     * @return a new request builder instance
     */
    public static LJImageRequestBuilder fromRequest(LJImageRequest imageRequest) {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(imageRequest.getSourceUri())
                .setAutoRotateEnabled(imageRequest.getAutoRotateEnabled())
                .setImageDecodeOptions(imageRequest.getImageDecodeOptions())
                .setCacheChoice(imageRequest.getCacheChoice())
                .setLocalThumbnailPreviewsEnabled(imageRequest
                        .getLocalThumbnailPreviewsEnabled())
                .setLowestPermittedRequestLevel(imageRequest
                        .getLowestPermittedRequestLevel())
                .setPostprocessor(imageRequest.getPostprocessor())
                .setProgressiveRenderingEnabled(imageRequest
                        .getProgressiveRenderingEnabled())
                .setRequestPriority(imageRequest.getPriority())
                .setResizeOptions(imageRequest.getResizeOptions());
        return new LJImageRequestBuilder(imageRequestBuilder, imageRequest
                .getCacheKey());
    }


    public ImageRequestBuilder getImageRequestBuilder() {
        return mImageRequestBuilder;
    }


    public String getCacheKey() {
        return mCacheKey;
    }


    /**
     * Builds the Request.
     *
     * @return a valid image request
     */
    public LJImageRequest build() {
        getImageRequestBuilder().build();
        return new LJImageRequest(this);
    }
}
