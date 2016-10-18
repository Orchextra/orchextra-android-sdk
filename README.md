# Orchextra SDK for Android
[![Build Status](https://travis-ci.org/Orchextra/orchextra-android-sdk.svg?branch=master)](https://travis-ci.org/Orchextra/orchextra-android-sdk)
[![codecov.io](https://codecov.io/github/Orchextra/orchextra-android-sdk/coverage.svg?branch=master)](https://codecov.io/github/Orchextra/orchextra-android-sdk)
![Language](https://img.shields.io/badge/Language-Android-brightgreen.svg)
![Version](https://img.shields.io/badge/Version-3.0.3-blue.svg)
[![](https://jitpack.io/v/Orchextra/orchextra-android-sdk.svg)](https://jitpack.io/#Orchextra/orchextra-android-sdk)
![](https://img.shields.io/badge/Min%20SDK-18-green.svg)

A library that gives you access to Orchextra platform from your Android sdkVersionAppInfo.

## Getting started
Start by creating a project in [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

## Overview
Orchextra SDK is composed of **Orchextra Core**, and add-ons

#### Orchextra Core
- Geofences
- IBeacons
- Push Notifications
- Barcode/qr Scanner

#### Image Recognition Add-on
- Image Recognition Scanner Module: Vuforia implementation

## Installation
You can check how SDK works with the :app module of this repository.

### Requirements
Android Jelly Bean (v. 18) or later. But Orchextra can be integrated in Android Gingerbread (v. 10), Google Play Services 7.8, 8.4 , 9.0, and now you can use flavour without google play services if you don't want geofencing, push notification, gps and scheduled actions. Now you can choose the support v7 and supportv4 versions.

## Add the dependency to Orchextra Core
We have to add the gradle dependencies. In our rootproject **build.gradle** file, we add the following maven dependency. This is required in order to advice gradle that it has to look for Orchextra sdk inside **jitpack.io** maven repository. Gradle file is this one:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/rootGradleScreenshot.png" width="300">

```java
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
Thinking about how to improve , in this version we have created different flavors using 3 different versions of GCM and one more without googleplayservices support.
And generating 4 versions of .aar possibilities.
and we add the Orchextra dependency in our **sdkVersionAppInfo** module:

with GCM 7.8
```groovy
   compile('com.github.orchextra.orchextra-android-sdk:orchextrasdk:3.0.3RC:play7Release@aar')
   {transitive true}
   compile 'com.google.android.gms:play-services-location:7.8.0'
   compile 'com.google.android.gms:play-services-gcm:7.8.0'
```
or with GCM 8.4
```groovy
   compile('com.github.orchextra.orchextra-android-sdk:orchextrasdk:3.0.3RC:play8Release@aar')
   {transitive true}
   compile 'com.google.android.gms:play-services-location:8.4.0'
   compile 'com.google.android.gms:play-services-gcm:8.4.0'
```
or with GCM 9.0
```groovy
   compile('com.github.orchextra.orchextra-android-sdk:orchextrasdk:3.0.3RC:play9Release@aar')
     {transitive true}
   compile 'com.google.android.gms:play-services-location:9.0.0'
   compile 'com.google.android.gms:play-services-gcm:9.0.0'
```
or without Google Play Services
```groovy
   compile('com.github.orchextra.orchextra-android-sdk:orchextrasdk:3.0.3RC:playnoRelease@aar')
     {transitive true}
```
If you use playnoRelease, some orchextraSDK features will not be available, geofences, notification push, scheduled Actions

The previous dependency has to be added into this file:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/appGradleScreenshot.png" width="300">

and we must sync gradle project.

## Integrate Orchextra SDK
We have to created a class which **extends from Application** (if we didn't do yet) and add the Orchextra init method is . We could implement OrchextraCompletionCallback interface in order to receive the orchextra status.

```java
@Override
public void onCreate() {
OrchextraBuilder builder = new OrchextraBuilder(this)
                .setApiKeyAndSecret(API_KEY, API_SECRET)
                .setLogLevel(OrchextraLogLevel.NETWORK)
                .setOrchextraCompletionCallback(this));
        Orchextra.initialize(builder);
        ...
        }
```
**IMPORTANT** you must make this call in **public void onCreate()** of your Application class, if you do not call initialize in this method, the SDK will not initialize properly. You can check that using the logLevel.

**IMPORTANT** if you are using Android Studio 2.1 or higher, and have "Instant Run" enabled, the first time you install the APK is installed in new device, the initialize() spends too much time, maybe a minute on older devices.The second time the problem disappears. To avoid this problem in Android Studio, disables the " Instant Run" from settings-> Build , Execution , Deployment- > Instant Run

## Image Recognition Add-on
If you choose a Google Play Services aar of Orchextra you can add to the OrchextraBuilder the project number from Google Console aka sender id
```java
import com.gigigo.vuforiaimplementation.ImageRecognitionVuforiaImpl;
...
@Override
public void onCreate() {
OrchextraBuilder builder = new OrchextraBuilder(this)
                .setApiKeyAndSecret(API_KEY, API_SECRET)
                .setLogLevel(OrchextraLogLevel.NETWORK)
                .setOrchextraCompletionCallback(this)
                .setImageRecognitionModule(new ImageRecognitionVuforiaImpl());
        Orchextra.initialize(builder);
        ...
        }
```


Then, in any part of our application we should start the orchextra sdk.

```java
Orchextra.start();
```
After calling start, you can call `Orchextra.stop()` if you need to stop all Orchextra features, so you can call again start or stop in order to fit your requirements.

## Change project/authCredentials Orchextra SDK
In the new version we set the Orchextra project authCredentials when we initialize the sdk, if we want to change the Ox Project, we can call it in any moment.
```java
Orchextra.changeCredentials(NEW_API_KEY,NEW_API_SECRET);
```
If the credetials, have no change, the method do nothing.

## Custom Scheme - Delegate
In order to get custom schemes within our sdkVersionAppInfo must conform the CustomSchemeReceiver interface, the following method will handle all the custom schemes created in Orchextra.

```java
Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
            @Override
            public void onReceive(String scheme) {
                Log.i("TAG", "SCHEME: " + scheme);
            }
        });
```
You can redefine this receiver in other places of your application. For example one in MainActivity and another diferent in DetailActivity.

## Bind/unBind user to Orchextra
CrmUser class is a local representation of a user persisted to the Orchextra Database to help to create a good user segmentation. This object is optional and could be set up at any time.

```java
Orchextra.bindUser(new CrmUser(CRM_ID,
                new GregorianCalendar(1981, Calendar.MAY, 31),
                CrmUser.Gender.GenderMale);
```
The new BindUser method is the new way to setCrmUser in OrchextraSDK, now you can unBind() the user for reset data on server side.
```java
Orchextra.unBindUser();
```

Is a good idea, always, put a unique key in *CRM_ID*, is the way for identificate your user inside Orchextra. For example you can use the next snipped for generate unique key:
```java
private String getUniqueCRMID() {
        String secureAndroidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String serialNumber = Build.SERIAL;
        String deviceToken = secureAndroidId + BuildConfig.APPLICATION_ID + serialNumber;
        return deviceToken;
    }
 ```

 All related management *CrmUser* *keywords* and *tags*  are now deprecated. In brief update this part with the new way to get and set these features, go to segmentation section to know the new operative.

## Segmentation
Orchextra SDK allows to create segmentation using tags, business units or custom fields. This segmentation can be performed by CrmUser or by device.

### Segmentation by device
Device segmentation can be created by using tags or business units.
This methods set,get and clear the local tags and bunisessUnits, in the next configuration request will set data in server side.
##### Using Device tags
You can set new device tags:
```java
List<String> lst_tags = Arrays.asList("tagTest", "tagTest1");
 Orchextra.setDeviceTags(lst_tags);
```
You can get device tags:
```java
List<String> deviceTags = Orchextra.getDeviceTags();
```
and you can clear all device tags:
```java
Orchextra.clearDeviceTags();
```

##### Using Device business units
You can set new device business units:
```java
List<String> lst_bu = Arrays.asList("BuTest", "BuTest1");
 Orchextra.setDeviceBusinessUnits(lst_tags);
```
You can get device business unit:

```java
List<String> userBusinessUnits = Orchextra.getUserBusinessUnits();
```
and you can clear all device  business unit:
```java
Orchextra.clearDeviceBusinessUnits();
```

### Segmentation by CRMUser
User segmentation can be created by using tags, business units or custom fields.

##### Using CRMUser tags
You can set new user tags:
```java
List<String> lst_tags = Arrays.asList("tagTest", "tagTest1");
 Orchextra.setUserTags(lst_tags);
```
You can get user tags:

```java
  List<String> userTags = Orchextra.getUserTags();
```
and you can clear all device  business unit:
```java
Orchextra.clearUserTags();
```

##### Using CRMUser business units
You can set new user business units:
```java
List<String> lst_tags = Arrays.asList("tagTest", "tagTest1");
 Orchextra.setUserTags(lst_tags);
```
You can get user business unit:
```java
 List<String> userbusinessunits= Orchextra.getUserBusinessUnits();
```
and you can clear all device  business unit:
```java
Orchextra.clearUserBusinessUnits();
```

##### Using CRMUser custom fields
You can set new custom fields:

```java
            Map<String, String> nameValue = new HashMap<String, String>();
            nameValue.put("name", "yourName");
            Orchextra.setUserCustomFields(nameValue);
```
You can get availables custom fields:

```java
 Map<String, String> availablesCF = Orchextra.getUserCustomFields();
```
and you can clear all device  business unit:
```java
Orchextra.clearUserCustomFields();
```



### Commit configuration send segmentation to Server Side

Afer all set segmentation information is required to commit back this to refresh new values.

```java
 Orchextra.commitConfiguration();
```
You can use this method for refresh orchextra configuration from your app.

##  Start Actions
Orchextra SDK let you invoke a couple of action within your own application to start a new user journey

### Scanner
You can scan QR and Barcode linked in Orchextra. To launch the scanner you just need to add the following line to the action.
```java
Orchextra.startScannerActivity();
```
##Customizing styles
Orchextra has default icons, colors and texts which can be overwritten. Firstly, you sdkVersionAppInfo must extends from **Theme.AppCompat.Light** style and overwrite the **colorPrimary**, **colorPrimaryDark** and **colorAccent** items to be applied to Orchextra styles. For example, the color of the toolbar is the color definied as colorPrimary style.
```xml
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <item name="colorPrimary">@color/color_primary</item>
    <item name="colorPrimaryDark">@color/color_primary_dark</item>
    <item name="colorAccent">@color/color_accent</item>
  </style>
```
  if you don't it, the default colorPrimary, colorPrimaryDark and colorAccent are

```xml
 <color name="color_primary">#3F51B5</color>
 <color name="color_primary_dark">#303F9F</color>
 <color name="color_accent">#FF4081</color>
```
In your string.xml, you can include some customized strings
```xml
<!-- Toolbar title in the scanner screen -->
<string name="ox_scanner_toolbar_title">Scanner</string>
​
<!-- Toolbar title in the webview screen -->
<string name="ox_webview_toolbar_title">Web View</string>
```
In the same way, some color you can customize are
```xml
<!-- Background color of the icon in the notification area -->
<color name="ox_notification_background_color">#EE524F</color>
​
<!-- Toolbar title and icon color -->
<color name="ox_toolbar_title_color">#FFF</color>
```
Additionally, you should customize the Orchextra Sdk with your drawables.
 - ox_notification_large_icon: This image is used as large icon in notifications and as image in the dialogs inside the application.
 - ox_notification_alpha_small_icon: Icon is showed in the notification bar in Android version 21 o higher.
 - ox_notification_color_small_icon: Icon is showed in the notification bar in Android versions lower than 21.
 - ox_close: Icon which is locate in the upper left corner of a screen and is used to close the view.

## Push Notifications Orchextra

### Creating a push project

First of all is important to create and configure your project in [Google Cloud Platform](https://console.cloud.google.com/home/dashboard). When your project is already create you should take care of two important fields:

* Regarding project information _"Project Number"_ that would be the identifier of sending events you will use inside SDK.

* Regarding API information _"Server API KEY"_, this is something you have to create. _Create authCredentials -> API key -> Server Key_ this will generate a String taht you will have to include in your parse project.

That's all in Google console. Now you should create a project in _Parse Dashboard_ and Set up notifications for android giving _"Server API KEY"_ you created in previous step to you parse project.

In this new version of OrchextraSDK you can use your own Push Notifications and use the OrchextraSDK Push Notification combine.For that you must to implements in your sdkVersionAppInfo an enrouter Service (extends *GcmListenerService*)
    , for redirect the notification using the parameter *String from* in *onMessageReceived* method. That parameter contains the sender_id or projectNumber configurated in push server(like Parse). Write us if you need more help at this point.

### SDK configRequest for using Notification Push

If you choose a Google Play Services aar of Orchextra you can add to the OrchextraBuilder the project number from Google Console aka sender id.
In this version we use the OrchextraBuilder instead of `strings.xml` for set the sender_ID.

```java
OrchextraBuilder builder = new OrchextraBuilder(this)
                ...
                .setGcmSenderId(SENDER_ID); //if is invalid, NP broadcast will be disabled

        Orchextra.initialize(builder);
```
Use here your _"Project Number"_(sender_id) and **Orchextra** and **Parse** will do all stuff for you.
For customizing the push notifications title in SDK is quite simple you should Override this strings at your `strings.xml`
`ox_notification_push_title`: Override this string resource in order to get your custom Push notification title. Default is **Orchextra**.

* Do not forget about image resources, all the explanations given in **Customizing styles** section about notifications applies here for customizing push notification image resources.

Now you can check if Push notifications are working, try to send a push using _Parse Dashboard_. Try to write and send an original message and check if it works!!.

## Image recognition integration
Image recognition is added as an add-on to Orchextra, by default SDK is not containing any Image recognition implementation, the only thing that is including is an interface that allows any of his implementations to communicate with Orchextra SDK.

So, you can add the corresponding implementation as a gradle dependency to your project, at this moment the only available implementation is using Vuforia as image recognition engine. Here you have the gradle dependency:
```groovy
 //vuforia 6.0
    compile 'com.github.GigigoGreenLabs.imgRecogModule:vuforiaimplementation:1.0'
```

Once you have added this dependency you will be able to inform OrchextraBuilder SDK about it has to use this implementation. You can do it this way:

```java
OrchextraBuilder builder = new OrchextraBuilder(this)
                ...
                .setImageRecognitionModule(new ImageRecognitionVuforiaImpl());

        Orchextra.initialize(builder);
```

This allows Orchextra to do all stuff related with image recognition. Don't forget to configure all necessary Vuforia details on [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), otherwise image recognition won't work.

To start an image recognition activity you only need to call:

```java
Orchextra.startImageRecognition();
```

Orchextra Vuforia 6.0 have support only for armeabi-v7a, you must add in the **build.gradle** of your app inside "android" node:
```java
android{
    defaultConfig {
       ...
        ndk {
            abiFilters "armeabi-v7a"
        }
        ...
    }
}
  ```

And in the **gradle.properties** file in the root project
```java
    android.useDeprecatedNdk=true
  ```
 With that Vuforia works in any kind of device
 For more infor about Vuforia visit https://developer.vuforiaCredentials.com/support

That's all!!

### Customizing Image recognition Activity

As you can see image recognition Activity is using Orchextra like ascpect, but don't worry, you can customize almost everything, take care of the following details:

#### Image Resources:
 - `ox_close_button`: close button, "x" by default, it allows to use an Android XML `selector` resource
 - `ir_scanline.png`:  is the image for the anim in video capture, you only put the newone inside drawable folder:
 - `ir_mark_point.png`:  is the image for the anim in video capture, you only put the newone inside drawable folder:

#### Colors:
- `ir_color_primary `: main color for _Activity_ _ToolBar_. Default is `color_primary`
- `ir_color_primary_dark `: color for _StatusBar_. Default is `color_primary_dark`
- `ir_color_accent `: main color for _Activity_ _ToolBar_ text. Default is `color_accent`
- `vuforia_loading_bg_color `: color for loading screen background. Default is `color_primary`


#### Texts:
 - `ox_loading_indicator_message`: Message that indicates that image recognition module is being loaded
 - `ox_ir_scan_activity_name`: Name of image recognition _Activity_, appearing in _Toolbar_

## Log level
Now you can configure log level using the level you consider to get debug info from Orchextra SDK. In order to set the level, you MUST set that in the OrchextraBuilder `OrchextraBuilder.setLogLevel(LOGLEVEL)` where _LOGLEVEL_ is an enum providing following info:

- `OrchextraLogLevel.ALL`: All info, even altbeacon lib logs
- `OrchextraLogLevel.NETWORK`: all debug from Orchextra plus network info
- `OrchextraLogLevel.DEBUG`: all debug info from SDK.
- `OrchextraLogLevel.WARN`: Only SDK errors and warnings
- `OrchextraLogLevel.ERROR`: Only errors
- `OrchextraLogLevel.NONE`: Not logging at all

Default level is `OrchextraLogLevel.NONE` for releases.

```java
OrchextraBuilder builder = new OrchextraBuilder(this)
               ...
                .setLogLevel(OrchextraLogLevel.NETWORK)
                ...
        Orchextra.initialize(builder);
```
## Realm Support
 We use this version of Realm:

 classpath 'io.realm:realm-gradle-plugin:1.0.0'

For more information to include Realm, visit the [Realm's documentation](https://realm.io/docs/java/latest/#sharing-schemas).

# Acknowledgements
We would like to mention every company and particular developers that have been contributing this repo in some way:

* Thank's to [Square](http://square.github.io/), we are using several libs they developed (Retrofit 2, OkHttp, Dagger).
* Thank's to Google, and Android Dev team, obviously, Android SDK, Support Libs, Google play Services, Dagger 2.
* Thank's to [Radius Network](http://www.radiusnetworks.com/) , for his amazing beacon lib [altBeacon](http://altbeacon.github.io/android-beacon-library/) for Android.
* Thank's to sourceForge, because we are using [zbar](http://zbar.sourceforge.net/) as Scanner
* Thank's to [Christian Panadero Martínez](https://twitter.com/PaNaVTEC) because his [blog](http://panavtec.me/) has many interesting ideas that we took as base for this project.
* Thank's to [Karumi](http://www.karumi.com/) for his great contributions to developers community in general. We are using [Dexter](https://github.com/Karumi/Dexter) from Karumi as well.
* Thank's to [Yohan Hartanto](https://twitter.com/yohan) that help us with several isues with internal lib configurations.

License
=======

    Copyright 2016 Orchextra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

