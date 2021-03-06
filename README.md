# DividerHandler
方便画分割线的类

## 使用方式
直接使用DividerLinearLayout或者自定义类

## demo
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    android:orientation="vertical"
    android:paddingBottom="10dp"
    android:paddingTop="10dp">

  <com.withparadox2.divider.widget.DividerLinearLayout
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:orientation="vertical"
      app:dv_color="@color/colorPrimary"
      app:dv_is_outer_most="true"
      app:dv_type="TOP">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:gravity="center_vertical"
        android:text="下面有根红色分割线，距左边缘10dp"
        android:background="#eee"
        android:paddingLeft="10dp"
        app:dv_color="@android:color/holo_red_dark"
        app:dv_margin_start="10dp"
        app:dv_type="BOTTOM"/>

    <TextView
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:layout_margin="20dp"
        android:background="#eee"
        android:gravity="center_vertical"
        android:text="有一圈分割线，并且和自身有5dp的偏移"
        app:dv_color="@android:color/holo_blue_dark"
        app:dv_margin_end="20dp"
        app:dv_margin_start="20dp"
        app:dv_padding="5dp"
        app:dv_type="BOTTOM|TOP|LEFT|RIGHT"/>
  </com.withparadox2.divider.widget.DividerLinearLayout>

</LinearLayout>
```

## screenshots
![image](https://github.com/withparadox2/divider/raw/master/images/linearlayout.png)

## Contact
* withparadox2@gmail.com
