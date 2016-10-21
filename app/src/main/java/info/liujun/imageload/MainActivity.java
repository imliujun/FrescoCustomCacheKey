package info.liujun.imageload;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.facebook.common.util.SecureHashUtil;
import info.liujun.image.ImageLoader;
import info.liujun.image.LJImageView;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LJImageView imageView = (LJImageView) findViewById(R.id.image);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });

        //String imageUrl = "http://img.pconline.com" +
        //        ".cn/images/upload/upc/tx/wallpaper/1212/06/c1/16396010_1354784049718.jpg";
        String imageUrl = "http://heixiucrm-test.img-cn-beijing.aliyuncs" +
                ".com/20160623175055_gift_select_animation_1466675455807" +
                ".gif@195w_195h_1l_1e_1an.gif";
        //String imageUrl = "http://heixiucrm-test.img-cn-beijing.aliyuncs.com/20160623175055_gift_select_animation_1466675455807.gif";
        String cacheKey = imageUrl;
        //最简单的加载方式，只需要传入URL和cacheKey就可以显示图片
        //imageView.loadImage(imageUrl, cacheKey);

        //内置一些工具方法，开启了渐进式JPEG图片加载，自动播放GIF动画
        // 支持 GIF 动图，需要在gradle添加
        //compile 'com.facebook.fresco:animated-gif:0.12.0'

        ImageLoader.loadImage(imageView, imageUrl, cacheKey);

        try {
            //Fresco 将图片缓存到磁盘时，使用以下方式对cacheKey进行加密作为文件名
            String s = SecureHashUtil
                    .makeSHA1HashBase64(cacheKey.getBytes("UTF-8"));
            Log.i("MainActivity", "key:" + s);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
