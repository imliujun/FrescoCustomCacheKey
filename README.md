FrescoCustomCacheKey
============
#### [Fresco](https://github.com/facebook/fresco)加载图片时使用自定义缓存Key，适应阿里OSS、七牛云存储、腾讯OSS等对图片进行防盗链设置，防止URL动态改变导致本地缓存不可用


## Usage

```java
//LJCacheKeyFactory 初始化时配置缓存工厂
ImagePipelineConfig.newBuilder(context).setCacheKeyFactory(LJCacheKeyFactory.getInstance())

//使用LJImageRequest设置cacheKey，替代Fresco的ImageRequest
LJImageRequest.fromUri(imageUrl, cacheKey)

```

##### 简单的加载方式
```java
LJImageView ljImageView = new LJImageView(this);

String uri ="https://pixabay.com/static/uploads/photo/2016/10/07/13/36/tangerines-1721590_1280.jpg";

//单独的缓存key，如果使用第三方OSS进行防盗链，建议使用ObjetKey作为cacheKey
String cacheKey = "tangerines-1721590_1280.jpg";

ljImageView.loadImage(imageUrl, cacheKey);

```




## Gradle
```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.imliujun:FrescoCustomCacheKey:1.0.1'
}
```

License
-------

    Copyright 2016 LiuJun

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
