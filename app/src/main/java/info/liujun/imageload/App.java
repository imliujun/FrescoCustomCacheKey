package info.liujun.imageload;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import info.liujun.image.FrescoConfig;
import java.io.File;
import okhttp3.OkHttpClient;

/**
 * 项目名称：ImageLoad
 * 类描述：
 * 创建人：LiuJun
 * 创建时间：16/8/15 21:18
 * 修改人：LiuJun
 * 修改时间：16/8/15 21:18
 * 修改备注：
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient();

        File sd = Environment.getExternalStorageDirectory();
        String image = sd.getPath() + "/imageLoad";
        //配置图片缓存目录
        File cacheDir = new File(image);
        if (!cacheDir.exists()) {
            Log.i("App", "path:" + cacheDir.getAbsolutePath());
        }
        //初始化Fresco，使用OkHttp3作为网络请求
        ImagePipelineConfig.Builder configBuilder = FrescoConfig
                .getConfigBuilder(getApplicationContext(), cacheDir, okHttpClient);
        Fresco.initialize(this, configBuilder.build());
    }
}
