# EasyAndroid
[![EasyAndroid](https://api.bintray.com/packages/easyandroid/maven/easytools/images/download.svg)](https://bintray.com/easyandroid/maven/easytools/_latestVersion)  [![Platform](https://img.shields.io/badge/platform-Android-green.svg)](https://developer.android.google.cn/) [![SDK](https://img.shields.io/badge/minSdkVersion-19%2B-green.svg)](https://developer.android.com/about/)

包含各种工具类的集合，会不定期更新，欢迎贡献code

使用方法：<br>
2.0.0以后，放弃了support库，请使用AndroidX
```
implementation 'com.easyandroid:easytools:2.0.0'

然后，在自己的Application中调用Utils.init(this);进行初始化
```

添加混淆：<br>
```
-keep class com.easytools.tools.DialogUtils {*;}
-dontwarn com.easytools.tools.**
-keep class com.easytools.constant.**{*;}
```

---

<span id="目录">

### 目录
+ [1、缓存相关](#acache)
+ [2、Activity生命周期管理类](#activitymanager)
+ [3、Activity 相关](#activityutils)
+ [4、操作系统、SDK相关](#androidutils)
+ [5、动画相关的工具类](#animationutils)
+ [6、App相关工具类](#apputils)
+ [7、关于数组的各种排序算法](#arrayutils)
+ [8、Assets目录数据库相关](#assetdatabaseopenhelper)
+ [9、设置Badge数字角标](#badgeutils)
+ [10、状态栏相关](#barutils)
+ [11、Base64相关](#base64utils)
+ [12、Bitmap相关](#bitmaputils)
+ [13、选择适配器](#checkadapter)
+ [14、数据验证相关](#checkutils)
+ [15、类操作相关](#classutils)
+ [16、清除缓存/文件相关工具类](#cleanutils)
+ [17、剪贴板相关工具类](#clipboardutils)
+ [18、关闭操作流相关](#closeutils)
+ [19、常量相关](#constantutils)
+ [20、转换操作相关](#convertutils)
+ [21、倒计时](#countdown)
+ [22、崩溃相关](#crashutils)
+ [23、日期操作相关](#dateutils)
+ [24、设备信息相关](#deviceutils)
+ [25、使用UUID生成手机唯一标示](#deviceuuidfactory)
+ [26、弹框相关](#dialogutils)
+ [27、屏幕显示相关](#displayutils)
+ [28、双击识别器](#doubleclickexitdetector)
+ [29、编码解码相关](#encodeutils)
+ [30、加密解密相关](#encryptutils)
+ [31、文件的IO流相关](#fileioutils)
+ [32、针对Android 7版本以上 FileProvider做适配](#fileprovider7)
+ [33、文件操作相关](#fileutils)
+ [34、Fragment相关](#fragmentutils)
+ [35、Gson转换相关](#gsonutils)
+ [36、16进制转换相关](#hexutils)
+ [37、图片加载器](#imageloader)
+ [38、意图相关工具类](#intentutils)
+ [39、JSON操作相关](#jsonutils)
+ [40、软键盘相关](#keyboardutils)
+ [41、打印日志](#logutils)
+ [42、循环定时器](#looptimer)
+ [43、阴历阳历相关](#lunarutils)
+ [44、m3u8文件解析类](#m3u8parserutils)
+ [45、偶对象相关](#maputils)
+ [46、获取MD5相关](#md5utils)
+ [47、音频播放相关](#mediaplayerutils)
+ [48、共享内存相关](#memoryfilehelper)
+ [49、元数据相关](#metadatautils)
+ [50、单币种货币类，处理货币算术、币种和取整](#money)
+ [51、手机网络相关](#networkutils)
+ [52、Notification相关](#notificationutils)
+ [53、对象相关](#objectutils)
+ [54、提取颜色的帮助类](#paletteutils)
+ [55、目录路径相关](#pathutils)
+ [56、权限相关](#permissionutils)
+ [57、轮询相关工具类](#pollingutils)
+ [58、判断先决条件](#preconditions)
+ [59、进程相关](#processutils)
+ [60、随机数相关](#randomutils)
+ [61、反射相关](#reflectutils)
+ [62、正则表达式相关](#regexutils)
+ [63、资源操作相关](#resourceutils)
+ [64、四舍五入相关](#roundutils)
+ [65、SD卡相关](#sdcardutils)
+ [66、服务相关](#serviceutils)
+ [67、Shell相关](#shellutils)
+ [68、短信相关](#smsutils)
+ [69、Snackbar相关](#snackbarutils)
+ [70、SharedPreferences相关](#sputils)
+ [71、状态栏背景色](#statusbarutils)
+ [72、状态栏字体颜色模式](#statustextutils)
+ [73、字符串相关](#stringutils)
+ [74、线程操作相关](#threadpoolutils)
+ [75、时间经历工具类](#timeutils)
+ [76、Toast工具相关](#toastmaster)
+ [77、Toast简单工具类](#toastutils)
+ [78、初始化Application，在Application之中调用init方法](#utils)
+ [79、视图工具](#viewutils)
+ [80、弱引用的Handler，防止内存泄漏，用法与Handler一致](#weakhandler)
+ [81、WebView常用设置](#webviewutils)
+ [82、文件压缩相关](#ziputils)
+ [83、打印长日志](#longlogutils)
---

<span id="acache">

* ### 缓存相关 -> [ACache.java][ACache.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
get                     : 获取ACache实例
put                     : 保存
getAsString             : 取值String
getAsJSONObject         : 取值Object
getAsBinary             : 取值Binary
getAsBitmap             : 取值Bitmap
getAsDrawable           : 取值Drawable
file                    : 取值file
remove                  : 移除某个key
clear                   : 清除所有数据
```

<span id="activitymanager">

* ### Activity生命周期管理类 -> [ActivityManager.java][ActivityManager.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
registerActivityLifecycleCallbacks       : 在自己的Application注册，关联Activity生命周期
currentActivity                          : 获取当前affinity栈顶Activity
closeActivity                            : 关闭Activity
closeAllActivity                         : 关闭所有Activity
closeActivityByName                      : 通过传入完整包.类名，关闭Activity
getCurrentActivityName                   : 获取当前Activity名称
getActivityStack                         : 获取Activity栈
```

<span id="activityutils">

* ### Activity 相关 -> [ActivityUtils.java][ActivityUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isActivityExists               : 判断 Activity 是否存在
startActivity                  : 启动 Activity
startActivityForResult         : 启动 Activity 为返回结果
startActivities                : 启动多个 Activity
startHomeActivity              : 回到桌面
getActivityList                : 获取 Activity 栈链表
getLauncherActivity            : 获取启动项 Activity
getTopActivity                 : 获取栈顶 Activity
isActivityExistsInStack        : 判断 Activity 是否存在栈中
finishActivity                 : 结束 Activity
finishToActivity               : 结束到指定 Activity
finishOtherActivities          : 结束所有其他类型的 Activity
finishAllActivities            : 结束所有 Activity
finishAllActivitiesExceptNewest: 结束除最新之外的所有 Activity
```

<span id="androidutils">

* ### 操作系统、SDK相关 -> [AndroidUtils.java][AndroidUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isNamedProcess            : 判断当前进程中是否包含指定名称的进程
gc                        : 清理后台进程与服务
isDalvik                  : isDalvik
isART                     : 是否ART模式
getCurrentRuntimeValue    : 获取手机当前的Runtime
getLauncherClassName      : 获取app的启动活动的名称
getAllApps                : 获取系统中所有的应用
getSDKVersion             : 获取手机系统SDK版本
```

<span id="animationutils">

* ### 动画相关的工具类 -> [AnimationUtils.java][AnimationUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getRotateAnimation              : 获取一个旋转的补间动画
getRotateAnimationByCenter      : 获取一个根据视图自身中心点旋转的补间动画
getAlphaAnimation               : 获取一个透明度渐变动画
getHiddenAlphaAnimation         : 获取一个由完全显示变为不可见的透明度渐变动画
getShowAlphaAnimation           : 获取一个由不可见变为完全显示的透明度渐变动画
getLessenScaleAnimation         : 获取一个缩小动画
getAmplificationAnimation       : 获取一个放大动画
```

<span id="apputils">

* ### App相关工具类 -> [AppUtils.java][AppUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
registerAppStatusChangedListener  : 注册 App 前后台切换监听器
unregisterAppStatusChangedListener: 注销 App 前后台切换监听器
installApp                        : 安装 App（支持 8.0）
installAppSilent                  : 静默安装 App
uninstallApp                      : 卸载 App
uninstallAppSilent                : 静默卸载 App
isAppInstalled                    : 判断 App 是否安装
isAppRoot                         : 判断 App 是否有 root 权限
isAppDebug                        : 判断 App 是否是 Debug 版本
isAppSystem                       : 判断 App 是否是系统应用
isAppForeground                   : 判断 App 是否处于前台
launchApp                         : 打开 App
relaunchApp                       : 重启 App
launchAppDetailsSettings          : 打开 App 具体设置
exitApp                           : 关闭应用
getAppIcon                        : 获取 App 图标
getAppPackageName                 : 获取 App 包名
getAppName                        : 获取 App 名称
getAppPath                        : 获取 App 路径
getAppVersionName                 : 获取 App 版本号
getAppVersionCode                 : 获取 App 版本码
getAppSignature                   : 获取 App 签名
getAppSignatureSHA1               : 获取应用签名的的 SHA1 值
getAppSignatureSHA256             : 获取应用签名的的 SHA256 值
getAppSignatureMD5                : 获取应用签名的的 MD5 值
getAppInfo                        : 获取 App 信息
getAppsInfo                       : 获取所有已安装 App 信息
```

<span id="arrayutils">

* ### 关于数组的各种排序算法 -> [ArrayUtils.java][ArrayUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isEmpty               : 判断数组是否为空
search                : 在数组objects中搜索元素element
selectSort            : 选择排序
insertSort            : 插入排序
bubbleSort            : 冒泡排序
recursiveSort         : 递归快速排序
fastStackSort         : 栈快速排序
upsideDown            : 将数组颠倒
integersToInts        : Inteher数组转换成int数组
toString              : 将给定的数组转换成字符串
```

<span id="assetdatabaseopenhelper">

* ### Assets目录数据库相关 -> [AssetDatabaseOpenHelper.java][AssetDatabaseOpenHelper.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getWriteableDatabase       : 创建或打开一个数据库
getReadableDatabase        : 创建或打开一个只读数据库
getDatabaseName            : 返回数据库名称
getFromAssets              : 获取asset文件下的资源文件信息
```

<span id="badgeutils">

* ### 设置Badge数字角标 -> [BadgeUtils.java][BadgeUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
setBadgeCount             : 设置Badge 目前支持Launcher：MIUI、Sony、Samsung、LG、HTC、Nova 需要这些权限，需要相关权限
setBadgeOfMIUI            : 设置MIUI的Badge
setBadgeOfSony            : 设置索尼的Badge
setBadgeOfSumsung         : 设置三星的Badge\设置LG的Badge
setBadgeOfHTC             : 设置HTC的Badge
setBadgeOfNova            : 设置HTC的Badge
setBadgeOfMadMode         : 设置Nova的Badge
resetBadgeCount           : 重置Badge
```

<span id="barutils">

* ### 状态栏相关 -> [BarUtils.java][BarUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
setTransparentStatusBar    : 隐藏状态栏
hideStatusBar              : 获取状态栏高度
getStatusBarHeight         : 不传Context获取状态栏高度
isStatusBarExists          : 判断状态栏是否存在
getActionBarHeight         : 获取ActionBar高度
showNotificationBar        : 显示通知栏
hideNotificationBar        : 隐藏通知栏
```

<span id="base64utils">

* ### Base64相关 -> [Base64Utils.java][Base64Utils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
encode                 : 编码
encodeToString         : 编码
encodeToChar           : 编码
decode                 : 解码
decode2String          : 解码
```

<span id="bitmaputils">

* ### Bitmap相关 -> [BitmapUtils.java][BitmapUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
bitmap2Byte                       : bitmap转字节数组
byte2Bitmap                       : 字节数组转bitmap
bitmap2String                     : 把bitmap转换成Base64编码String
drawable2Bitmap                   : Drawable转Bitmap
bitmap2Drawable                   : Bitmap转Drawable
inputStream2Byte                  : Stream转换成Byte
inputStream2Bitmap                : 从输入流中读取Bitmap
resource2Bitmap                   : 根据资源ID获取一个指定大小的bitmap
getBitmapFromFile                 : 根据文件获取一个指定大小的bitmap
saveBitmap                        : 保存bitmap到本地file中
view2Bitmap                       : 把View绘制到Bitmap上
view2Bitmap2                      : 把一个View的对象转换成bitmap
getBitmapFromUri                  : 根据uri获取图片
getPicPathFromUri                 : 图片uri转path
calculateInSampleSize             : 图片压缩处理，计算图片的压缩比例
combineImages                     : 合并Bitmap
combineImagesToSameSize           : 合并成相同大小
scale                             : 图片的缩放方法,放大缩小图片
getRoundedCornerBitmap            : 获得圆角图片
createReflectionBitmap            : 获得带倒影的图片
compressByQuality                 : 压缩图片大小
compressBySize                    : 像素压缩
convertGreyImg                    : 将彩色图转换为灰度图
getRoundBitmap                    : 转换图片成圆形
createThumbnailBitmap             : 返回指定Bitmap的缩略图
createWatermarkBitmap             : 生成水印图片 水印在右下角
codec                             : 重新编码Bitmap
rotate                            : 旋转图片
reverseByHorizontal               : 水平翻转处理
reverseByVertical                 : 垂直翻转处理
adjustTone                        : 更改图片色系，变亮或变暗
convertToBlackWhite               : 将彩色图转换为黑白图
getImageDegree                    : 读取图片属性：图片被旋转的角度
blur                              : 给Bitmap增加模糊效果
saturation                        : 饱和度处理
lum                               : 亮度处理
hue                               : 色相处理
lumAndHueAndSaturation            : 亮度、色相、饱和度处理
nostalgic                         : 怀旧效果处理
soften                            : 柔化效果处理
sunshine                          : 光照效果处理
film                              : 底片效果处理
sharpen                           : 锐化效果处理
emboss                            : 浮雕效果处理
grayMasking                       : 光晕效果
boxBlurFilter                     : 高斯模糊
rgba                              : 打印出透明度和十六进制的对应关系
yuvLandscapeToPortrait            : 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注意：将源图片的宽高互换一下就是新图片的宽高
```

<span id="checkadapter">

* ### 选择适配器 -> [CheckAdapter.java][CheckAdapter.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```

```

<span id="checkutils">

* ### 数据验证相关 -> [CheckUtils.java][CheckUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
valiObjectIsNull         : 验证对象是否为空
valiStringLength         : 验证字符串的长度是在指定范围内
valiStringMinLength      : 验证字符串的长度的最小值
valiStringMaxLength      : 验证字符串的长度的最大值
valiIntValue             : 验证Int型数据是在指定范围内
valiIntMinValue          : 验证Int数据的最小值
valiIntMaxValue          : 验证Int数据的最大值
valiLongValue            : 验证Long型数据是在指定范围内
valiLongMinValue         : 验证Long数据的最小值
valiLongMaxValue         : 验证Long数据的最大值
valiFileIsExists         : 验证文件是否存在
valiFileCanRead          : 验证文件是否能读取
valiFileCanWrite         : 验证文件是否能写入
valiFileIsDirectory      : 验证file是否是目录
valiFileIsFile           : 验证file是否是文件
valiFile                 : 对指定的文件对象进行是否null、是否存在以及是否是文件校验
valiDir                  : 对指定的文件对象进行是否null、是否存在以及是否是目录校验
valiFileByReadBefore     : 在执行读取之前对指定的文件对象进行是否null、是否存在、是否是文件以及能否读取校验
valiFileByWriteBefore    : 在执行写入之前对指定的文件对象进行是否null、是否存在、是否是文件以及能否写入校验
```

<span id="classutils">

* ### 类操作相关 -> [ClassUtils.java][ClassUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
newInstance            : 根据对象的类（Class）新建一个实例对象，用于反射
forName                : 动态加载类，返回Class类的对象，这个Class就是所有反射操作的源头
getUnboxedClass        : 取得拆箱类对象
getBoxedClass          : 取得装箱类的对象
boxed                  : 装箱
unboxed                : 拆箱
isTrue                 : 判断布尔值
getSize                : 获取大小
toURI                  : 根据资源名称获取URI
getMethodFullName      : 取得一个方法的全名，如"getPersonInfo(String name, int age)"
getGenericClass        : 运行时获取模板类类型
getJavaVersion         : 取得Java版本
isBeforeJava5          : 判断Java版本
isBeforeJava6          : 判断Java版本
toString               : 将错误或异常转换为字符串信息
checkBytecode          : 判断字节数组大小，是否影响JIT编译器的性能
getSizeMethod          : 取得一系列方法名
getMethodName          : 取得方法名称
searchProperty         : 查找属性
getGetter              : 获取getter方法
getProperties          : 获取属性
filterJavaKeyword      : Java关键字过滤
convertCompatibleType  : 兼容类型转换。null值是OK的。如果不需要转换，则返回原来的值。
```

<span id="cleanutils">

* ### 清除缓存/文件相关工具类 -> [CleanUtils.java][CleanUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
cleanInternalCache   : 清除内部缓存
cleanInternalFiles   : 清除内部文件
cleanInternalDbs     : 清除内部数据库
cleanInternalDbByName: 根据名称清除数据库
cleanInternalSp      : 清除内部 SP
cleanExternalCache   : 清除外部缓存
cleanCustomDir       : 清除自定义目录下的文件
cleanApplicationData : 清除本应用的所有的数据
getCacheSize         : 取得缓存大小
getFolderSize        : 获取文件字节大小
getFormatSize        : 格式化值大小
```

<span id="clipboardutils">

* ### 剪贴板相关工具类 -> [ClipboardUtils.java][ClipboardUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
copyText        : 复制文本到剪贴板
getText         : 获取剪贴板的文本
copyUri         : 复制uri到剪贴板
getUri          : 获取剪贴板的uri
copyIntent      : 复制意图到剪贴板
getIntent       : 获取剪贴板的意图
```

<span id="closeutils">

* ### 关闭操作流相关 -> [CloseUtils.java][CloseUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
closeIO         : 关闭IO
```

<span id="constantutils">

* ### 常量相关 -> [ConstantUtils.java][ConstantUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
常用单位常量
```

<span id="convertutils">

* ### 转换操作相关 -> [ConvertUtils.java][ConvertUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
bytes2Bits, bits2Bytes                  : bytes 与 bits 互转
bytes2Chars, chars2Bytes                : bytes 与 chars 互转
bytes2HexString, hexString2Bytes        : bytes 与 hexString 互转
input2OutputStream, output2InputStream  : inputStream 与 outputStream 互转
inputStream2Bytes, bytes2InputStream    : inputStream 与 bytes 互转
outputStream2Bytes, bytes2OutputStream  : outputStream 与 bytes 互转
inputStream2String, string2InputStream  : inputStream 与 string 按编码互转
outputStream2String, string2OutputStream: outputStream 与 string 按编码互转
bytes2Chars, chars2Bytes                : bytes 与 chars 按编码互转
byte2Object, object2Byte                : bytes 与 object 按编码互转
byte2Size, size2Byte                    : 以unit为单位的size，互转
bitmap2Bytes, bytes2Bitmap              : bitmap 与 bytes 互转
drawable2Bitmap, bitmap2Drawable        : drawable 与 bitmap 互转
drawable2Bytes, bytes2Drawable          : drawable 与 bytes 互转
view2Bitmap                             : view 转 Bitmap
dp2px, px2dp                            : dp 与 px 互转
sp2px, px2sp                            : sp 与 px 互转
```

<span id="countdown">

* ### 倒计时 -> [Countdown.java][Countdown.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
倒计时
```

<span id="crashutils">

* ### 崩溃相关 -> [CrashUtils.java][CrashUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
捕获崩溃信息，保存在本地txt文件，在Application中调用初始化方法init()即可
```

<span id="dateutils">

* ### 日期操作相关 -> [DateUtils.java][DateUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
str2Date         : 字符串转为Date
date2Str         : Date转为字符串
str2Calendar     : 字符串转为Calendar
calendar2Str     : Calendar转为字符串
getCurDate       : 返回当前的日期，这是一个重载方法，可指定格式，如：2019-01-04
getCurDateTime   : 返回当前的日期和时间：2019-01-04 13:51:27
getDateByTime    : 将给定时间以"yyyy-MM-dd"的格式进行转换
getDateByFormat  : 通过给定格式，将指定时间进行转换
getMillon        : 返回当前时间字符串
getDay           : 返回给定天的字符串
getSMillon       : 返回给定时间的毫秒字符串
addMonth         : 在日期上增加数个整月
addDay           : 在日期上增加天数
getNextHour      : 获取距现在某一小时的时刻，例如：h=-1为上一个小时，h=1为下一个小时
getTimeStamp     : 获取时间戳
getMonth         : 返回月
getDay           : 返回日
getHour          : 返回小时
getMinute        : 返回分
getSecond        : 返回秒
getMillis        : 返回毫秒
getDatePattern   : 获得默认的日期格式：yyyy-MM-dd HH:mm:ss
countDays        : 按格式的字符串距离今天的天数
parse            : 使用用户格式提取字符串日期
```

<span id="deviceutils">

* ### 设备信息相关 -> [DeviceUtils.java][DeviceUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getSDKVersion                    : 获取设备系统版本号
getAndroidID                     : 获取设备AndroidID
getMacAddressByWifiInfo          : 根据wifi获取设备MAC地址
getMacAddressByNetworkInterface  : 根据网络接口获取设备MAC地址
getMacAddressByFile              : 根据文件获取设备MAC地址
getMacAddress                    : 获取设备MAC地址
getDeviceIMEI                    : 获取设备的IMEI
getIMSI                          : 获取手机IMSI
getManufacture                   : 获取设备厂商
getModel                         : 获取设备型号
getBootTimeString                : 获取开机时间
printSystemInfo                  : 打印系统整体信息
getScreenBrightnessModeState     : 获取系统屏幕亮度模式的状态
isScreenBrightnessModeAuto       : 判断系统屏幕亮度模式是否是自动
setScreenBrightnessMode          : 设置系统屏幕亮度模式
getScreenBrightness              : 获取系统亮度
setScreenBrightness              : 设置系统亮度
setWindowBrightness              : 设置给定Activity的窗口的亮度
setScreenBrightnessAndApply      : 设置系统亮度并实时可以看到效果
getScreenDormantTime             : 获取屏幕休眠时间
setScreenDormantTime             : 设置屏幕休眠时间
getAirplaneModeState             : 获取飞行模式的状态
isAirplaneModeOpen               : 判断飞行模式是否打开
setAirplaneMode                  : 设置飞行模式的状态
getBluetoothState                : 获取蓝牙的状态
isBluetoothOpen                  : 判断蓝牙是否打开
setBluetooth                     : 设置蓝牙状态
getRingVolume                    : 获取铃声音量
setRingVolume                    : 设置媒体音量
getNumCores                      : 得到cpu的核心数
getMaxFreqency                   : 获取cpu最大频率
getMinFreqency                   : 获取cpu最小频率
getCMDOutputString               : 获取命令输出字符串.
```

<span id="deviceuuidfactory">

* ### 使用UUID生成手机唯一标示 -> [DeviceUuidFactory.java][DeviceUuidFactory.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getDeviceUuid       : 生成手机唯一标示
```

<span id="dialogutils">

* ### 弹框相关 -> [DialogUtils.java][DialogUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
弹框相关
```

<span id="displayutils">

* ### 屏幕显示相关 -> [DisplayUtils.java][DisplayUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getRealScreenWidthPixels   : 获取屏幕真实尺寸，包含虚拟按键
getRealScreenHeightPixels  : 获取屏幕真实尺寸，包含虚拟按键
getScreenWidthPixels       : 获取屏幕尺寸
getScreenHeightPixels      : 获取屏幕尺寸
getScreenDensity           : 获取屏幕密度
getScreenDensityDpi        : 获取屏幕密度 DPI
setFullScreen              : 设置屏幕为全屏
setNonFullScreen           : 设置屏幕为非全屏
toggleFullScreen           : 切换屏幕为全屏与否状态
isFullScreen               : 判断屏幕是否为全屏
setLandscape               : 设置屏幕为横屏
setPortrait                : 设置屏幕为竖屏
isLandscape                : 判断是否横屏
isPortrait                 : 判断是否竖屏
getScreenRotation          : 获取屏幕旋转角度
screenShot                 : 截屏
isScreenLock               : 判断是否锁屏
setSleepDuration           : 设置进入休眠时长
getSleepDuration           : 获取进入休眠时长
isTablet                   : 判断是否是平板
adaptScreen4VerticalSlide  : 适配垂直滑动的屏幕
adaptScreen4HorizontalSlide: 适配水平滑动的屏幕
cancelAdaptScreen          : 取消适配屏幕
restoreAdaptScreen         : 恢复适配屏幕
isAdaptScreen              : 是否适配屏幕
```

<span id="doubleclickexitdetector">

* ### 双击识别器 -> [DoubleClickExitDetector.java][DoubleClickExitDetector.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
click                      : 当两次点击时间间隔小于有效间隔时间时就会返回true，否则返回false
setEffectiveIntervalTime   : 设置有效间隔时间，单位毫秒
setHintMessage             : 设置提示消息
```

<span id="encodeutils">

* ### 编码解码相关 -> [EncodeUtils.java][EncodeUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
urlEncode          : URL 编码
urlDecode          : URL 解码
base64Encode       : Base64 编码
base64Encode2String: Base64 编码
base64Decode       : Base64 解码
htmlEncode         : Html 编码
htmlDecode         : Html 解码
```

<span id="encryptutils">

* ### 加密解密相关 -> [EncryptUtils.java][EncryptUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
encryptMD2, encryptMD2ToString                        : MD2 加密
encryptMD5, encryptMD5ToString                        : MD5 加密
encryptMD5File, encryptMD5File2String                 : MD5 加密文件
encryptSHA1, encryptSHA1ToString                      : SHA1 加密
encryptSHA224, encryptSHA224ToString                  : SHA224 加密
encryptSHA256, encryptSHA256ToString                  : SHA256 加密
encryptSHA384, encryptSHA384ToString                  : SHA384 加密
encryptSHA512, encryptSHA512ToString                  : SHA512 加密
encryptHmacMD5, encryptHmacMD5ToString                : HmacMD5 加密
encryptHmacSHA1, encryptHmacSHA1ToString              : HmacSHA1 加密
encryptHmacSHA224, encryptHmacSHA224ToString          : HmacSHA224 加密
encryptHmacSHA256, encryptHmacSHA256ToString          : HmacSHA256 加密
encryptHmacSHA384, encryptHmacSHA384ToString          : HmacSHA384 加密
encryptHmacSHA512, encryptHmacSHA512ToString          : HmacSHA512 加密
encryptDES, encryptDES2HexString, encryptDES2Base64   : DES 加密
decryptDES, decryptHexStringDES, decryptBase64DES     : DES 解密
encrypt3DES, encrypt3DES2HexString, encrypt3DES2Base64: 3DES 加密
decrypt3DES, decryptHexString3DES, decryptBase64_3DES : 3DES 解密
encryptAES, encryptAES2HexString, encryptAES2Base64   : AES 加密
decryptAES, decryptHexStringAES, decryptBase64AES     : AES 解密
encryptRSA, encryptRSA2HexString, encryptRSA2Base64   : RSA 加密
decryptRSA, decryptHexStringRSA, decryptBase64RSA     : RSA 解密
```

<span id="fileioutils">

* ### 文件的IO流相关 -> [FileIOUtils.java][FileIOUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
writeFileFromIS            : 将输入流写入文件
writeFileFromBytesByStream : 将字节数组写入文件
writeFileFromBytesByChannel: 将字节数组写入文件
writeFileFromBytesByMap    : 将字节数组写入文件
writeFileFromString        : 将字符串写入文件
readFile2List              : 读取文件到字符串链表中
readFile2String            : 读取文件到字符串中
readFile2BytesByStream     : 读取文件到字节数组中
readFile2BytesByChannel    : 读取文件到字节数组中
readFile2BytesByMap        : 读取文件到字节数组中
setBufferSize              : 设置缓冲区尺寸
```

<span id="fileprovider7">

* ### 针对Android 7版本以上 FileProvider做适配 -> [FileProvider7.java][FileProvider7.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
拍照            : 仅需改变一行：Uri fileUri = FileProvider7.getUriForFile(context, file);
安装apk         : 仅需改变一行：FileProvider7.setIntentDataAndType(context, intent, "application/vnd.android.package-archive", file, true);
```

<span id="fileutils">

* ### 文件操作相关 -> [FileUtils.java][FileUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getFileByPath                 : 根据文件路径获取文件
isFileExists                  : 根据路径判断文件是否存在
isDir                         : 判断是否是目录
isFile                        : 判断是否是文件
createOrExistsDir             : 判断目录是否存在，不存在则创建目录，并判断是否创建成功
createOrExistsFile            : 判断文件是否存在，不存在则判断是否创建成功
createFileByDeleteOldFile     : 判断文件是否存在，存在则在创建之前删除
deleteFile                    : 删除文件
deleteFilesInDir              : 删除目录下的所有文件(包括文件夹)
deleteDir                     : 删除目录(删除目录下的所有文件包含所有子文件夹里的文件，不包括文件夹)
deleteFilesByDirectory        : 只删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理，同时返回false
listFilesInDir                : 获取目录下所有文件，包括/不包含子目录
listFilesInDirWithFilter      : 获取目录下所有后缀名为suffix的文件，包括/不包含子目录
searchFileInDir               : 查找文件
getFileName                   : 获取全路径中的文件名
getFileNameNoExtension        : 获取全路径中的不带拓展名的文件名
getFileCharsetSimple          : 简单获取文件编码格式
getFileSize                   : 获取文件大小
getFileMD5                    : 获取文件的MD5校验码
closeIO                       : 关闭IO
renameFile                    : 文件重命名
makeDirs                      : 创建文件目录
getFolderName                 : 通过给定路径取得文件目录
getFileNameNoEx               : 去掉文档扩展名
getMimeType                   : 获取本地文件的媒体类型
```

<span id="fragmentutils">

* ### Fragment相关 -> [FragmentUtils.java][FragmentUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
add                   : 新增 fragment
show                  : 显示 fragment
hide                  : 隐藏 fragment
showHide              : 先显示后隐藏 fragment
replace               : 替换 fragment
pop                   : 出栈 fragment
popTo                 : 出栈到指定 fragment
popAll                : 出栈所有 fragment
remove                : 移除 fragment
removeTo              : 移除到指定 fragment
removeAll             : 移除所有 fragment
getTop                : 获取顶部 fragment
getTopInStack         : 获取栈中顶部 fragment
getTopShow            : 获取顶部可见 fragment
getTopShowInStack     : 获取栈中顶部可见 fragment
getFragments          : 获取同级别的 fragment
getFragmentsInStack   : 获取同级别栈中的 fragment
getAllFragments       : 获取所有 fragment
getAllFragmentsInStack: 获取栈中所有 fragment
findFragment          : 查找 fragment
dispatchBackPress     : 处理 fragment 回退键
setBackgroundColor    : 设置背景色
setBackgroundResource : 设置背景资源
setBackground         : 设置背景
```

<span id="gsonutils">

* ### Gson转换相关 -> [GsonUtils.java][GsonUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
json2Bean     : JSON格式字符串转为实体类
bean2Json     : 实体类转为JSON格式的字符串
json2List     : JSON格式字符串转换为实体类数组
```

<span id="hexutils">

* ### 16进制转换相关 -> [HexUtils.java][HexUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
encodeHex          : 字节数组转换为十六进制字符数组
encodeHexStr       : 字节数组转换为十六进制字符串
decodeHex          : 十六进制字符数组转换为字节数组
toDigit            : 十六进制字符转换成一个整数
```

<span id="imageloader">

* ### 图片加载器 -> [ImageLoader.java][ImageLoader.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
图片加载的工具类，采用线程池
```

<span id="intentutils">

* ### 意图相关工具类 -> [IntentUtils.java][IntentUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isIntentAvailable                : 判断意图是否可用
getInstallAppIntent              : 获取安装 App（支持 6.0）的意图
getUninstallAppIntent            : 获取卸载 App 的意图
getLaunchAppIntent               : 获取打开 App 的意图
getLaunchAppDetailsSettingsIntent: 获取 App 具体设置的意图
getShareTextIntent               : 获取分享文本的意图
getShareImageIntent              : 获取分享图片的意图
getComponentIntent               : 获取其他应用组件的意图
getShutdownIntent                : 获取关机的意图
getCaptureIntent                 : 获取拍照的意图
```

<span id="jsonutils">

* ### JSON操作相关 -> [JSONUtils.java][JSONUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getLong                 : get Long
getInt                  : get Int
getDouble               : get Double
getBoolean              : get Boolean
getString               : get String
getStringCascade        : get String
getStringArray          : get String[]
getStringList           : get List<String>
getJSONObject           : get JSONObject
getJSONObjectCascade    : get JSONObject
getJSONArray            : get JSONArray
getMap                  : get map from jsonObject/jsonData
parseKeyAndValueToMap   : parse key-value pairs to map
```

<span id="keyboardutils">

* ### 软键盘相关 -> [KeyboardUtils.java][KeyboardUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
showSoftInput                     : 显示软键盘
showSoftInputUsingToggle          : 显示软键盘用 toggle
hideSoftInput                     : 隐藏软键盘
hideSoftInputUsingToggle          : 隐藏软键盘用 toggle
toggleSoftInput                   : 切换键盘显示与否状态
isSoftInputVisible                : 判断软键盘是否可见
registerSoftInputChangedListener  : 注册软键盘改变监听器
unregisterSoftInputChangedListener: 注销软键盘改变监听器
fixAndroidBug5497                 : 修复安卓 5497 BUG
fixSoftInputLeaks                 : 修复软键盘内存泄漏
clickBlankArea2HideSoftInput      : 点击屏幕空白区域隐藏软键盘
```

<span id="logutils">

* ### 打印日志 -> [LogUtils.java][LogUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getConfig                : 获取 log 配置
Config.setLogSwitch      : 设置 log 总开关
Config.setConsoleSwitch  : 设置 log 控制台开关
Config.setGlobalTag      : 设置 log 全局 tag
Config.setLogHeadSwitch  : 设置 log 头部信息开关
Config.setLog2FileSwitch : 设置 log 文件开关
Config.setDir            : 设置 log 文件存储目录
Config.setFilePrefix     : 设置 log 文件前缀
Config.setBorderSwitch   : 设置 log 边框开关
Config.setSingleTagSwitch: 设置 log 单一 tag 开关（为美化 AS 3.1 的 Logcat）
Config.setConsoleFilter  : 设置 log 控制台过滤器
Config.setFileFilter     : 设置 log 文件过滤器
Config.setStackDeep      : 设置 log 栈深度
Config.setStackOffset    : 设置 log 栈偏移
Config.setSaveDays       : 设置 log 可保留天数
Config.addFormatter      : 新增 log 格式化器
log                      : 自定义 tag 的 type 日志
v                        : tag 为类名的 Verbose 日志
vTag                     : 自定义 tag 的 Verbose 日志
d                        : tag 为类名的 Debug 日志
dTag                     : 自定义 tag 的 Debug 日志
i                        : tag 为类名的 Info 日志
iTag                     : 自定义 tag 的 Info 日志
w                        : tag 为类名的 Warn 日志
wTag                     : 自定义 tag 的 Warn 日志
e                        : tag 为类名的 Error 日志
eTag                     : 自定义 tag 的 Error 日志
a                        : tag 为类名的 Assert 日志
aTag                     : 自定义 tag 的 Assert 日志
file                     : log 到文件
json                     : log 字符串之 json
xml                      : log 字符串之 xml
```

<span id="looptimer">

* ### 循环定时器 -> [LoopTimer.java][LoopTimer.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
```

<span id="lunarutils">

* ### 阴历阳历相关 -> [LunarUtils.java][LunarUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
lunarYearToGanZhi       : 农历年份转干支
LunarToSolar            : 农历转公历
SolarToLunar            : 公历转农历
```

<span id="m3u8parserutils">

* ### m3u8文件解析类 -> [M3U8ParserUtils.java][M3U8ParserUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
parseString           : 解析m3u8的ts下载地址
getM3u8Key            : 获取m3u8文件中的uri对应的key
```

<span id="maputils">

* ### 偶对象相关 -> [MapUtils.java][MapUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isEmpty                     : is null or its size is 0
putMapNotEmptyKey           : add key-value pair to map, and key need not null or empty
putMapNotEmptyKeyAndValue   : add key-value pair to map, both key and value need not null or empty
putMapNotNullKey            : add key-value pair to map, key need not null
putMapNotNullKeyAndValue    : add key-value pair to map, both key and value need not null
getKeyByValue               : get key by value, match the first entry front to back
parseKeyAndValueToMap       : parse key-value pairs to map, ignore empty key
toJson                      : join map
```

<span id="md5utils">

* ### 获取MD5相关 -> [MD5Utils.java][MD5Utils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getDigest      : 获取 MessageDigest 实例
md5            : 获取md5
```

<span id="mediaplayerutils">

* ### 音频播放相关 -> [MediaPlayerUtils.java][MediaPlayerUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getMediaPlayer       : 获取 MediaPlayer 实例
playAudio            : 播放音频文件
stopAudio            : 停止播放音频文件
```

<span id="memoryfilehelper">

* ### 共享内存相关 -> [MemoryFileHelper.java][MemoryFileHelper.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
createMemoryFile          : 创建共享内存对象
openMemoryFile            : 打开共享内存
getParcelFileDescriptor   : 获取共享内存的文件描述符
getFileDescriptor         : 获取共享内存的文件描述符
```

<span id="metadatautils">

* ### 元数据相关 -> [MetaDataUtils.java][MetaDataUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getMetaDataInApp     : 获取 application 的 meta-data 值
getMetaDataInActivity: 获取 activity 的 meta-data 值
getMetaDataInService : 获取 service 的 meta-data 值
getMetaDataInReceiver: 获取 receiver 的 meta-data 值
```

<span id="money">

* ### 单币种货币类，处理货币算术、币种和取整 -> [Money.java][Money.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
Money                     : 构造器
getAmount                 : 获取本货币对象代表的金额数，以元为单位。
setAmount                 : 设置本货币对象代表的金额数，以元为单位。
getCent                   : 获取本货币对象代表的金额数，以分为单位。
setCent                   : 设置本货币对象代表的金额数，以分为单位。
getCurrency               : 获取本货币对象代表的币种
getCentFactor             : 获取本货币币种的元/分换算比率
equals                    : 判断本货币对象与另一对象是否相等
hashCode                  : 计算本货币对象的杂凑值
compareTo                 : 对象比较
greaterThan               : 货币比较
add                       : 货币加法
addTo                     : 货币累加
subtract                  : 货币减法
subtractFrom              : 货币累减
multiply                  : 货币乘法
multiplyBy                : 货币累乘
divide                    : 货币除法
divideBy                  : 货币累除
allocate                  : 货币分配
toString                  : 生成本对象的缺省字符串表示
assertSameCurrencyAs      : 断言本货币对象与另一货币对象是否具有相同的币种
rounding                  : 对BigDecimal型的值按指定取整方式取整
newMoneyWithSameCurrency  : 创建一个币种相同，具有指定金额的货币对象
dump                      : 生成本对象内部变量的字符串表示，用于调试
```

<span id="networkutils">

* ### 手机网络相关 -> [NetworkUtils.java][NetworkUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
openWirelessSettings  : 打开网络设置界面
isConnected           : 判断网络是否连接
isAvailableByPing     : 判断网络是否可用
getMobileDataEnabled  : 判断移动数据是否打开
setMobileDataEnabled  : 打开或关闭移动数据
isMobileData          : 判断网络是否是移动数据
is4G                  : 判断网络是否是 4G
getWifiEnabled        : 判断 wifi 是否打开
setWifiEnabled        : 打开或关闭 wifi
isWifiConnected       : 判断 wifi 是否连接状态
isWifiAvailable       : 判断 wifi 数据是否可用
getNetworkOperatorName: 获取移动网络运营商名称
getNetworkType        : 获取当前网络类型
getIPAddress          : 获取 IP 地址
getDomainAddress      : 获取域名 IP 地址
getIpAddressByWifi    : 根据 WiFi 获取网络 IP 地址
getGatewayByWifi      : 根据 WiFi 获取网关 IP 地址
getNetMaskByWifi      : 根据 WiFi 获取子网掩码 IP 地址
getServerAddressByWifi: 根据 WiFi 获取服务端 IP 地址
```

<span id="notificationutils">

* ### Notification相关 -> [NotificationUtils.java][NotificationUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
createNotificationChannel  : 8.0以上，获取NotificationChannel对象
getNotificationBuilder     : 获取 NotificationBuilder
showNotification           : 显示一条消息
nofity                     : 显示一条消息
cancle                     : 设置通知信息
getNotification            :获取Notification实例
```

<span id="objectutils">

* ### 对象相关 -> [ObjectUtils.java][ObjectUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isEmpty         : 判断是否为空
isNotEmpty      : 判断是否不为空
equals          : 判断是否相等
requireNonNull  : 对象不能为空，为空则抛出空指针
getOrDefault    : 获取非空对象
hashCode        : 获取对象的哈希值
```

<span id="paletteutils">

* ### 提取颜色的帮助类 -> [PaletteUtils.java][PaletteUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
init                  : 初始化对象
changedImageViewShape : 创建Drawable对象
colorEasy             : 颜色变浅处理
colorBurn             : 颜色加深处理
```

<span id="pathutils">

* ### 目录路径相关 -> [PathUtils.java][PathUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getRootPath                    : 获取根路径
getDataPath                    : 获取数据路径
getDownloadCachePath           : 获取下载缓存路径
getInternalAppDataPath         : 获取内存应用数据路径
getInternalAppCodeCacheDir     : 获取内存应用代码缓存路径
getInternalAppCachePath        : 获取内存应用缓存路径
getInternalAppDbsPath          : 获取内存应用数据库路径
getInternalAppDbPath           : 获取内存应用数据库路径
getInternalAppFilesPath        : 获取内存应用文件路径
getInternalAppSpPath           : 获取内存应用 SP 路径
getInternalAppNoBackupFilesPath: 获取内存应用未备份文件路径
getExternalStoragePath         : 获取外存路径
getExternalMusicPath           : 获取外存音乐路径
getExternalPodcastsPath        : 获取外存播客路径
getExternalRingtonesPath       : 获取外存铃声路径
getExternalAlarmsPath          : 获取外存闹铃路径
getExternalNotificationsPath   : 获取外存通知路径
getExternalPicturesPath        : 获取外存图片路径
getExternalMoviesPath          : 获取外存影片路径
getExternalDownloadsPath       : 获取外存下载路径
getExternalDcimPath            : 获取外存数码相机图片路径
getExternalDocumentsPath       : 获取外存文档路径
getExternalAppDataPath         : 获取外存应用数据路径
getExternalAppCachePath        : 获取外存应用缓存路径
getExternalAppFilesPath        : 获取外存应用文件路径
getExternalAppMusicPath        : 获取外存应用音乐路径
getExternalAppPodcastsPath     : 获取外存应用播客路径
getExternalAppRingtonesPath    : 获取外存应用铃声路径
getExternalAppAlarmsPath       : 获取外存应用闹铃路径
getExternalAppNotificationsPath: 获取外存应用通知路径
getExternalAppPicturesPath     : 获取外存应用图片路径
getExternalAppMoviesPath       : 获取外存应用影片路径
getExternalAppDownloadPath     : 获取外存应用下载路径
getExternalAppDcimPath         : 获取外存应用数码相机图片路径
getExternalAppDocumentsPath    : 获取外存应用文档路径
getExternalAppObbPath          : 获取外存应用 OBB 路径
```

<span id="permissionutils">

* ### 权限相关 -> [PermissionUtils.java][PermissionUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getPermissions          : 获取应用权限
isGranted               : 判断权限是否被授予
launchAppDetailsSettings: 打开应用具体设置
permission              : 设置请求权限
rationale               : 设置拒绝权限后再次请求的回调接口
callback                : 设置回调
theme                   : 设置主题
request                 : 开始请求
```

<span id="pollingutils">

* ### 轮询相关工具类 -> [PollingUtils.java][PollingUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
startPollingService     : 开启轮询服务
stopPollingService      : 停止轮询
```

<span id="preconditions">

* ### 判断先决条件 -> [Preconditions.java][Preconditions.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isEmpty                    : 判断list数据是否为空
isNotEmpty                 : 判断list数据是否不为空
checkArgument              : 判决布尔值
checkState                 : 判决布尔值
checkNotNull               : 判决非空
checkElementIndex          : 判决数组越界
badElementIndex            : 生成数组越界提示
checkPositionIndex         : 判决位置是否越界
checkStringNotEmpty        : 判决String非空
checkArrayElementsInRange  : 判决浮点数组中的所有元素都在指定的范围内
```

<span id="processutils">

* ### 进程相关 -> [ProcessUtils.java][ProcessUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getForegroundProcessName  : 获取前台线程包名
killAllBackgroundProcesses: 杀死所有的后台服务进程
killBackgroundProcesses   : 杀死后台服务进程
isMainProcess             : 判断是否运行在主进程
getCurrentProcessName     : 获取当前进程名称
```

<span id="randomutils">

* ### 随机数相关 -> [RandomUtils.java][RandomUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getRandomNumbersAndLetters  : 获取固定长度的随机数（大小写字母、整数）
getRandomNumbers            : 获取固定长度数据（仅数字）
getRandomLetters            : 获取固定长度字母（不分大小写）
getRandomCapitalLetters     : 获取固定长度字母（仅大写）
getRandomLowerCaseLetters   : 获取固定长度字母（仅小写）
getRandom(String, int)      : 给定字符数组，从中随机抽取一定长度字符，返回字符串
getRandom(int)              : 获取一个随机的数值
getRandom(int, int)         : 获取一个范围的数值
shuffle                     : 洗牌算法，随机排列指定数组
```

<span id="reflectutils">

* ### 反射相关 -> [ReflectUtils.java][ReflectUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
reflect        : 设置要反射的类
newInstance    : 实例化反射对象
field          : 设置反射的字段
method         : 设置反射的方法
get            : 获取反射想要获取的
invoke         : 执行instance的方法
invokeMethod   : 执行指定的对方法
getInstance    : 根据类名，参数实例化对象
getField       : 取得属性值
setField       : 设置属性
```

<span id="regexutils">

* ### 正则表达式相关 -> [RegexUtils.java][RegexUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isMobileSimple   : 验证手机号（简单）
isMobileExact    : 验证手机号（精确）
isTel            : 验证电话号码
isIDCard15       : 验证身份证号码15位
isIDCard18       : 验证身份证号码18位
isEmail          : 验证邮箱
isURL            : 验证URL
isZh             : 验证汉字
isUsername       : 验证用户名
isDate           : 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
isIP             : 验证IP地址
isMatch          : 判断是否匹配正则
getMatches       : 获取正则匹配的部分
getSplits        : 获取正则匹配分组
getReplaceFirst  : 替换正则匹配的第一部分
getReplaceAll    : 替换所有正则匹配的部分
```

<span id="resourceutils">

* ### 资源操作相关 -> [ResourceUtils.java][ResourceUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
copyFileFromAssets: 从 assets 中拷贝文件
readAssets2String : 从 assets 中读取字符串
readAssets2List   : 从 assets 中按行读取字符串
copyFileFromRaw   : 从 raw 中拷贝文件
readRaw2String    : 从 raw 中读取字符串
readRaw2List      : 从 raw 中按行读取字符串
```

<span id="roundutils">

* ### 四舍五入相关 -> [RoundUtils.java][RoundUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
round               : 四舍五入操作
twoStringPoint      : 四舍五入操作保留小数点后两位
twoBigDecimalPoint  : BigDecimal四舍五入操作保留小数点后两位
convertMoney        : 超过一万用小数点的方式代替
convertNum          : 数字转换，不带小数位
getFormatMoney      : 转换成金钱的字符串，这是一个重载方法
getMoneyStr         : 获取money类型的字符串，这是一个重载方法
```

<span id="sdcardutils">

* ### SD卡相关 -> [SDCardUtils.java][SDCardUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isSDCardEnable          : 判断SD卡是否可用
getDataPath             : 获取SD卡Data路径
getSDCardPath           : 获取SD卡路径
savePic                 : 在缓存路径下创建指定图片名称的文件
getSDCardPathByCmd      : 通过cmd指令获取SD卡路径
getExternalMusicDir     : 获取外部存储Music路径
getExternalMovieDir     : 获取外部存储Movies路径
getExternalDownloadDir  : 获取外部存储Download路径
getFreeSpace            : 获取SD卡剩余空间
getSDCardInfo           : 获取SD卡信息
```

<span id="serviceutils">

* ### 服务相关 -> [ServiceUtils.java][ServiceUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getAllRunningServices   : 返回正在运行的服务
startService            : 开启服务
stopService             : 停止服务
bindService             : 绑定服务
unbindService           : 解绑服务
isServiceRunning        : 监测服务是否正在运行
```

<span id="shellutils">

* ### Shell相关 -> [ShellUtils.java][ShellUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
execCmd : 在 root 下执行命令，这是一个重载方法
```

<span id="smsutils">

* ### 短信相关 -> [SMSUtils.java][SMSUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
send       : 发送短信
```

<span id="snackbarutils">

* ### Snackbar相关 -> [SnackbarUtils.java][SnackbarUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
with           : 设置 snackbar 依赖 view
setMessage     : 设置消息
setMessageColor: 设置消息颜色
setBgColor     : 设置背景色
setBgResource  : 设置背景资源
setDuration    : 设置显示时长
setAction      : 设置行为
setBottomMargin: 设置底边距
show           : 显示 snackbar
showSuccess    : 显示预设成功的 snackbar
showWarning    : 显示预设警告的 snackbar
showError      : 显示预设错误的 snackbar
dismiss        : 消失 snackbar
getView        : 获取 snackbar 视图
addView        : 添加 snackbar 视图
```

<span id="sputils">

* ### SharedPreferences相关 -> [SpUtils.java][SpUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
getInstance    : 获取单例

putString      : 保存String，这是一个重载方法
putInt         : 保存int值，这是一个重载方法
putLong        : 保存long值，这是一个重载方法
putFloat       : 保存float值，这是一个重载方法
putBoolean     : 保存boolean值，这是一个重载方法
putStringSet   : 保存类型为String的集合值

getString      : 获取String值，这是一个重载方法
getInt         : 获取int值，这是一个重载方法
getLong        : 获取long值，这是一个重载方法
getFloat       : 获取float值，这是一个重载方法
getBoolean     : 获取boolean值，这是一个重载方法
getStringSet   : 获取类型为String的集合值，这是一个重载方法

getAll         : 获取所有缓存数据，以偶对象返回
contains       : 指定数据是否被缓存
remove         : 删除一条缓存，这是一个重载方法
clear          : 清空缓存，这是一个重载方法
```

<span id="statusbarutils">

* ### 状态栏相关 -> [StatusBarUtils.java][StatusBarUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
setColor                              : 设置状态栏颜色
setColorForSwipeBack                  : 为滑动返回界面设置状态栏颜色，这是一个重载方法
setColorNoTranslucent                 : 设置状态栏纯色，不加半透明效果
setColorDiff                          : 设置状态栏颜色(5.0以下无半透明效果,不建议使用)
setTranslucent                        : 使状态栏半透明，这是一个重载方法
setTranslucentForCoordinatorLayout    : 针对根布局是 CoordinatorLayout, 使状态栏半透明
setTransparent                        : 设置状态栏全透明
setTranslucentDiff                    : 使状态栏透明(5.0以上半透明效果,不建议使用)，这是一个重载方法
setColorForDrawerLayout               : 为DrawerLayout 布局设置状态栏变色
setColorNoTranslucentForDrawerLayout  : 为DrawerLayout 布局设置状态栏颜色,纯色
setColorForDrawerLayoutDiff           : 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
setTranslucentForDrawerLayout         : 为 DrawerLayout 布局设置状态栏透明，这是一个重载方法
setTranslucentForDrawerLayoutDiff     : 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)
setTransparentForImageView            : 为头部是 ImageView 的界面设置状态栏全透明，这是一个重载方法
setTranslucentForImageViewInFragment  : 为 fragment 头部是 ImageView 的设置状态栏透明，这是一个重载方法
hideFakeStatusBarView                 : 隐藏伪状态栏 View
```

<span id="statustextutils">

* ### 状态栏相关 -> [StatusTextUtils.java][StatusTextUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
StatusBarLightMode(Activity activity, boolean dark)              : dark=true黑色模式，dark=false白色模式
```

<span id="stringutils">

* ### 字符串相关 -> [StringUtils.java][StringUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isEmpty                : 判断字符串是否为null或长度为0（不包含空格，即如果有空格，则返回false）
isSpace                : 判断字符串是否为null或全为空格（如果有空格，则返回true）
nullStrToEmpty         : null对象转为长度为0的空字符串
length                 : 返回字符串长度
isEquals               : 字符串比较
capitalizeFirstLetter  : 首字母大写
lowerFirstLetter       : 首字母小写
reverse                : 反转字符串
utf8Encode             : 使用utf8编码
toDBC                  : 转化为半角字符
toSBC                  : 转化为全角字符
getPYFirstLetter       : 获得第一个汉字首字母
cn2PY                  : 中文转拼音
isNumeric              : 判断字符串是否只包含unicode数字
```

<span id="threadpoolutils">

* ### 线程操作相关 -> [ThreadPoolUtils.java][ThreadPoolUtils.java] ->[ThreadPoolUtilsTest.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
isMainThread            : 判断当前是否主线程
getFixedPool            : 获取固定线程池
getSinglePool           : 获取单线程池
getCachedPool           : 获取缓冲线程池
getIoPool               : 获取 IO 线程池
getCpuPool              : 获取 CPU 线程池
executeByFixed          : 在固定线程池执行任务
executeByFixedWithDelay : 在固定线程池延时执行任务
executeByFixedAtFixRate : 在固定线程池按固定频率执行任务
executeBySingle         : 在单线程池执行任务
executeBySingleWithDelay: 在单线程池延时执行任务
executeBySingleAtFixRate: 在单线程池按固定频率执行任务
executeByCached         : 在缓冲线程池执行任务
executeByCachedWithDelay: 在缓冲线程池延时执行任务
executeByCachedAtFixRate: 在缓冲线程池按固定频率执行任务
executeByIo             : 在 IO 线程池执行任务
executeByIoWithDelay    : 在 IO 线程池延时执行任务
executeByIoAtFixRate    : 在 IO 线程池按固定频率执行任务
executeByCpu            : 在 CPU 线程池执行任务
executeByCpuWithDelay   : 在 CPU 线程池延时执行任务
executeByCpuAtFixRate   : 在 CPU 线程池按固定频率执行任务
executeByCustom         : 在自定义线程池执行任务
executeByCustomWithDelay: 在自定义线程池延时执行任务
executeByCustomAtFixRate: 在自定义线程池按固定频率执行任务
cancel                  : 取消任务的执行
```

<span id="timeutils">

* ### 时间经历工具类 -> [TimeUtils.java][TimeUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
format       : 输入时间毫秒数，输出时间差，如：1分钟前、3小时前、5天前、3月前、1年前等
getTime      : 输入时间秒，获得时间，格式为00:00:00
```

<span id="toastmaster">

* ### Toast工具相关 -> [ToastMaster.java][ToastMaster.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
setGravity     : 设置吐司位置
setBgColor     : 设置背景颜色
setBgResource  : 设置背景资源
setMsgColor    : 设置消息颜色
setMsgTextSize : 设置消息字体大小
showShort      : 显示短时吐司
showLong       : 显示长时吐司
showCustomShort: 显示短时自定义吐司
showCustomLong : 显示长时自定义吐司
cancel         : 取消吐司显示
```

<span id="toastutils">

* ### Toast简单工具类 -> [ToastUtils.java][ToastUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
showToast     : 显示吐司
cancel        : 取消吐司
```

<span id="utils">

* ### 初始化Application，在Application之中调用init方法 -> [Utils.java][Utils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
init            : 初始化Application
```

<span id="viewutils">

* ### 视图工具 -> [ViewUtils.java][ViewUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
forceGetViewSize : 在 onCreate 中获取视图的尺寸
measureView      : 测量视图尺寸
getMeasuredWidth : 获取测量视图宽度
getMeasuredHeight: 获取测量视图高度
measureView      : 视图测量
setLayoutX       : 设置控件所在的位置X，并且不改变宽高，X为绝对位置，此时Y可能归0
setLayoutY       : 设置控件所在的位置Y，并且不改变宽高，Y为绝对位置，此时X可能归0
setLayout        : 设置控件所在的位置YY，并且不改变宽高，XY为绝对位置
```

<span id="weakhandler">

* ### 弱引用的Handler，防止内存泄漏，用法与Handler一致 -> [WeakHandler.java][WeakHandler.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```

```

<span id="webviewutils">

* ### WebView常用设置 -> [WebViewUtils.java][WebViewUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
enableAdaptive                              : 开启自适应功能
disableAdaptive                             : 禁用自适应功能
enableZoom                                  : 开启缩放功能
disableZoom                                 : 禁用缩放功能
enableJavaScript                            : 开启JavaScript
disableJavaScript                           : 禁用JavaScript
enableJavaScriptOpenWindowsAutomatically    : 开启JavaScript自动弹窗
disableJavaScriptOpenWindowsAutomatically   : 禁用JavaScript自动弹窗
goBack                                      : 返回
```

<span id="ziputils">

* ### 文件压缩相关 -> [ZipUtils.java][ZipUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)
```
zipFiles          : 批量压缩文件
zipFile           : 压缩文件
unzipFile         : 解压文件
unzipFileByKeyword: 解压带有关键字的文件
getFilesPath      : 获取压缩文件中的文件路径链表
getComments       : 获取压缩文件中的注释链表
```

<span id="longlogutils">

* ### 打印长日志 -> [LongLogUtils.java][LongLogUtils.java]&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;&#8195;[回到目录](#目录)


[ACache.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ACache.java

[ActivityManager.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ActivityManager.java

[ActivityUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ActivityUtils.java

[AndroidUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/AndroidUtils.java

[AnimationUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/AnimationUtils.java

[AppUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/AppUtils.java

[ArrayUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ArrayUtils.java

[AssetDatabaseOpenHelper.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/AssetDatabaseOpenHelper.java

[BadgeUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/BadgeUtils.java

[BarUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/BarUtils.java

[Base64Utils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/Base64Utils.java

[BitmapUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/BitmapUtils.java

[CheckAdapter.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/CheckAdapter.java

[CheckUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/CheckUtils.java

[ClassUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ClassUtils.java

[CleanUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/CleanUtils.java

[ClipboardUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ClipboardUtils.java

[CloseUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/CloseUtils.java

[ConstantUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ConstantUtils.java

[ConvertUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ConvertUtils.java

[Countdown.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/Countdown.java

[CrashUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/CrashUtils.java

[DateUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DateUtils.java

[DeviceUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DeviceUtils.java

[DeviceUuidFactory.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DeviceUuidFactory.java

[DialogUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DialogUtils.java

[DisplayUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DisplayUtils.java

[DoubleClickExitDetector.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/DoubleClickExitDetector.java

[EncodeUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/EncodeUtils.java

[EncryptUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/EncryptUtils.java

[FileIOUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/FileIOUtils.java

[FileProvider7.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/FileProvider7.java

[FileUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/FileUtils.java

[FragmentUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/FragmentUtils.java

[GsonUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/GsonUtils.java

[HexUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/HexUtils.java

[ImageLoader.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ImageLoader.java

[IntentUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/IntentUtils.java

[JSONUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/JSONUtils.java

[KeyboardUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/KeyboardUtils.java

[LogUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/LogUtils.java

[LoopTimer.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/LoopTimer.java

[LunarUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/LunarUtils.java

[M3U8ParserUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/M3U8ParserUtils.java

[MapUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/MapUtils.java

[MD5Utils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/MD5Utils.java

[MediaPlayerUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/MediaPlayerUtils.java

[MemoryFileHelper.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/MemoryFileHelper.java

[MetaDataUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/MetaDataUtils.java

[Money.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/Money.java

[NetworkUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/NetworkUtils.java

[NotificationUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/NotificationUtils.java

[ObjectUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ObjectUtils.java

[PaletteUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/PaletteUtils.java

[PathUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/PathUtils.java

[PermissionUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/PermissionUtils.java

[PollingUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/PollingUtils.java

[Preconditions.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/Preconditions.java

[ProcessUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ProcessUtils.java

[RandomUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/RandomUtils.java

[ReflectUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ReflectUtils.java

[RegexUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/RegexUtils.java

[ResourceUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ResourceUtils.java

[RoundUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/RoundUtils.java

[SDCardUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/SDCardUtils.java

[ServiceUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ServiceUtils.java

[ShellUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ShellUtils.java

[SMSUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/SMSUtils.java

[SnackbarUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/SnackbarUtils.java

[SpUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/SpUtils.java

[StatusBarUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/StatusBarUtils.java

[StatusTextUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/StatusTextUtils.java

[StringUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/StringUtils.java

[ThreadPoolUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ThreadPoolUtils.java
[ThreadPoolUtilsTest.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/test/java/com/easytools/tools/ThreadPoolUtilsTest.java

[TimeUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/TimeUtils.java

[ToastMaster.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ToastMaster.java

[ToastUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ToastUtils.java

[Utils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/Utils.java

[ViewUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ViewUtils.java

[WeakHandler.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/WeakHandler.java

[WebViewUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/WebViewUtils.java

[ZipUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/ZipUtils.java

[LongLogUtils.java]: https://github.com/gycold/EasyAndroid/blob/master/easytools/src/main/java/com/easytools/tools/LongLogUtils.java

一行代码搞定Android7.0以上版本 FileProvider的使用：
 ![image](https://github.com/gycold/EasyAndroid/raw/master/pictures/fileprovider.png)
