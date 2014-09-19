AndroidLibrary
==============
微博：<a title="Android技术及移动互联网分享" href="http://weibo.com/1901077453" target="_blank">Rocien </a>
邮箱：<a title="欢迎邮件与我交流" href="lgpszu@gmail.com" target="_blank">lgpszu@gmail.com</a>
SwipeToDismissListView 
-----------------------
 SwipeToDismissListView可以让你在任何一个item向左滑动一定距离，item会出现一个删除按钮，通过setOnDismissListener，可以对按钮的点击事件进行监听，在OnDismiss完成你想做的事情。按钮的出现和消失已经设置有动画效果. use like this:
```java
<com.szu.library.swipetodismiss.SwipeToDismissListView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:SwipeToDismissListView="http://schemas.android.com/apk/res/com.szu.AppTest"
        android:id="@android:id/list"
        android:background="@android:color/white"
        android:dividerHeight="2dp"
        android:divider="@color/grey"
        android:scrollbarStyle="outsideOverlay"
        android:scrollbarThumbVertical="@color/blue"
        android:scrollbarSize="3dp"
        SwipeToDismissListView:dismissWith="30"
        SwipeToDismissListView:dismissHeight="20"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginLeft="5dp"
        android:layout_marginRight="5dp">
</com.szu.library.swipetodismiss.SwipeToDismissListView>
```

PullToRefreshListView
-----------------------
 PullToRefreshListView 下拉刷新的列表，支持底部加载，默认是点击加载更多，也可以再布局文件添加属性ptrMode:"auto"设置为自动加载，或者在代码里面setMode()也可以。 use like this:
```java
<com.szu.library.pulltorefresh.PullToRefreshListView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:PullToRefreshListView="http://schemas.android.com/apk/res/com.szu.AppTest"
        android:id="@android:id/list"
        android:background="@android:color/white"
        android:dividerHeight="2dp"
        android:divider="@color/grey"
        android:scrollbarStyle="outsideOverlay"
        android:scrollbarThumbVertical="@color/blue"
        android:scrollbarSize="3dp"
        PullToRefreshListView:ptrMode="auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginLeft="5dp"
        android:layout_marginRight="5dp">
</com.szu.library.pulltorefresh.PullToRefreshListView>
```

PowerImageView
-------------
可以显示静态的图片或者是播放gif动态图，use like this:

```java
 <com.szu.library.imageview.PowerImageView
        android:id="@+id/image_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:src="@drawable/img_anim"
        PowerImageView:auto_play="true"
        PowerImageView:repeat_play="false"
        />
```
Sample 
-----------------------
 Sample 是对AndroidLibrary的使用样例
 


