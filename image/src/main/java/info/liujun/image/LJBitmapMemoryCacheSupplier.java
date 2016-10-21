package info.liujun.image;

import android.app.ActivityManager;
import android.os.Build;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

/**
 * 项目名称：ImageLoader
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：2016/10/13 18:32
 * 修改人：LiuJun
 * 修改时间：2016/10/13 18:32
 * 修改备注：
 */
public class LJBitmapMemoryCacheSupplier implements Supplier<MemoryCacheParams> {
    private static final int MAX_CACHE_ENTRIES = 256;
    private static final int MAX_CACHE_ENTRIES_LOLLIPOP = 128;
    private static final int MAX_EVICTION_QUEUE_SIZE = Integer.MAX_VALUE;
    private static final int MAX_EVICTION_QUEUE_ENTRIES = Integer.MAX_VALUE;
    private static final int MAX_CACHE_ENTRY_SIZE = Integer.MAX_VALUE;

    private final ActivityManager mActivityManager;


    public LJBitmapMemoryCacheSupplier(ActivityManager activityManager) {
        mActivityManager = activityManager;
    }


    @Override
    public MemoryCacheParams get() {
        int maxCacheEntries = MAX_CACHE_ENTRIES;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            maxCacheEntries = MAX_CACHE_ENTRIES_LOLLIPOP;
        }

        return new MemoryCacheParams(getMaxCacheSize(), maxCacheEntries, MAX_EVICTION_QUEUE_SIZE, MAX_EVICTION_QUEUE_ENTRIES, MAX_CACHE_ENTRY_SIZE);
    }


    private int getMaxCacheSize() {
        final int maxMemory = Math.min(mActivityManager.getMemoryClass() *
                ByteConstants.MB, Integer.MAX_VALUE);
        if (maxMemory < 32 * ByteConstants.MB) {
            return 4 * ByteConstants.MB;
        } else if (maxMemory < 64 * ByteConstants.MB) {
            return 6 * ByteConstants.MB;
        } else {
            // We don't want to use more ashmem on Gingerbread for now, since it doesn't respond well to
            // native memory pressure (doesn't throw exceptions, crashes app, crashes phone)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                return 8 * ByteConstants.MB;
            } else {
                return maxMemory / 4;
            }
        }
    }
}
