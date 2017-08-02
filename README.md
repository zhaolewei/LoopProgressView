##LoopProgressView

    自定义控件：渐变环形进度条 
    
  
## Demo
![](https://raw.githubusercontent.com/zhaolewei/LoopProgressView/master/show.gif)



## Gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
}

    dependencies {
        compile 'com.github.zhaolewei:LoopProgressView:v1.0'
     }

```    



## Attributes

|name|format|description|
|:---:|:---:|:---:|
| lpv_isGradual | boolean |是否使用渐变色
| lpv_progressLineColor | color |进度条颜色；当开启渐变时，为初始颜色
| lpv_backgroundLineColor | color |进度条背景颜色
| lpv_gradualEndColor | color |渐变最终颜色
| lpv_divideCount | integer | 小线的数量
| lpv_progress | float | 当前进度


## Use
````java
    progressView.setProgerss(progress); //设置顺时针旋转的角度  

````
