# EasyAndroid
[![EasyAndroid](https://api.bintray.com/packages/easyandroid/maven/easytools/images/download.svg)](https://bintray.com/easyandroid/maven/easytools/_latestVersion) [![API](https://img.shields.io/appveyor/ci/gruntjs/grunt.svg)](15+) [![License](https://img.shields.io/appveyor/ci/gruntjs/grunt.svg)](Apache-2.0) 

包含各种工具类的集合，会不定期更新，欢迎贡献code  

使用方法：
implementation 'com.easyandroid:easytools:1.3.0'


添加混淆：  
-keep class com.easytools.tools.DialogUtil {*;}  
-dontwarn com.easytools.tools.**  
-keep class com.easytools.constant.**{*;}  
 ![image](https://github.com/gycold/EasyAndroid/raw/master/pictures/list.png)


一行代码搞定Android7.0以上版本 FileProvider的使用：
 ![image](https://github.com/gycold/EasyAndroid/raw/master/pictures/fileprovider.png)


view使用方法：
compile 'com.easyandroid:easyviews:1.0.4'
