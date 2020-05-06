<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="${packageName}">
    
    <application>
    
   		<!-- 在组件集成宿主时, 由于宿主已经声明了 CommonSDK 的公有 ConfigModule, 所以在这里只需要声明业务组件自己的 ConfigModule -->
        <meta-data
            android:name="${packageName}.config.ModuleMVPConfig"
            android:value="SSMVPConfig"/>
    </application>
</manifest>
