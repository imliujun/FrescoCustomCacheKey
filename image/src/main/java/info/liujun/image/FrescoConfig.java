package info.liujun.image;

import android.app.ActivityManager;
import android.content.Context;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpNetworkFetcher;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import java.io.File;
import okhttp3.OkHttpClient;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/16 13:51
 * 修改人：LiuJun
 * 修改时间：16/8/16 13:51
 * 修改备注：
 */
public class FrescoConfig {

    private static final String IMAGE_PIPELINE_SMALL_CACHE_DIR = "image_cache_small";//小图所放路径的文件夹名
    private static final String IMAGE_PIPELINE_CACHE_DIR = "image_cache";//默认图所放路径的文件夹名

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 8 *
            ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 20 *
            ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 200 *
            ByteConstants.MB;//默认图磁盘缓存的最大值


    private FrescoConfig() {}


    public static ImagePipelineConfig.Builder getConfigBuilder(Context context, File baseDirectoryPath, OkHttpClient okHttpClient) {

        DiskCacheConfig smallDiskCacheConfig = DiskCacheConfig
                .newBuilder(context).setBaseDirectoryPath(baseDirectoryPath)
                .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)
                .build();
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                                                         .setBaseDirectoryPath(baseDirectoryPath)//缓存图片基路径
                                                         .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                                                         .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                                                         .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,当设备磁盘空间低时。
                                                         .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备磁盘空间极低时。
                                                         .build();
        OkHttpNetworkFetcher networkFetcher = new OkHttpNetworkFetcher(okHttpClient);
        return ImagePipelineConfig.newBuilder(context)
                                  .setNetworkFetcher(networkFetcher)//自定的网络层配置：如OkHttp，Volley
                                  .setCacheKeyFactory(LJCacheKeyFactory
                                          .getInstance())//缓存Key工厂
                                  .setBitmapMemoryCacheParamsSupplier(new LJBitmapMemoryCacheSupplier((ActivityManager) context
                                          .getSystemService(Context.ACTIVITY_SERVICE)))
                                  //内存缓存配置（一级缓存，已解码的图片）
                                  .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
                                  .setSmallImageDiskCacheConfig(smallDiskCacheConfig);//磁盘缓存配置（小图片，可选～三级缓存的小图优化缓存）
    }
}
