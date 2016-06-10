# Orchextra SDK for Android
[![Build Status](https://travis-ci.org/Orchextra/orchextra-android-sdk.svg?branch=master)](https://travis-ci.org/Orchextra/orchextra-android-sdk) 
[![codecov.io](https://codecov.io/github/Orchextra/orchextra-android-sdk/coverage.svg?branch=master)](https://codecov.io/github/Orchextra/orchextra-android-sdk)
![Language](https://img.shields.io/badge/Language-Android-brightgreen.svg)
![Version](https://img.shields.io/badge/Version-2.3.2-blue.svg)
[![](https://jitpack.io/v/Orchextra/orchextra-android-sdk.svg)](https://jitpack.io/#Orchextra/orchextra-android-sdk)
![](https://img.shields.io/badge/Min%20SDK-18-green.svg)

A library that gives you access to Orchextra platform from your Android app.

## Getting started
Start by creating a project in [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

## Overview
Orchextra SDK is composed of **Orchextra Core**.
  
#### Orchextra Core
- Geofences
- Beacons
- Push Notifications
- Barcode Scanner

#### Image Recognition Add-on
- Image Recognition Scanner Module: Vuforia implementation

## Installation
Download [Orchextra Android Sample ](https://github.com/Orchextra/orchextra-android-sample-app) to understand how to use the SDK.

### Requirements
Android Jelly Bean (v. 18) or later. But Orchextra can be integrated in Android Gingerbread (v. 10)

## Add the dependencies
We have to add the gradle dependencies. In our **build.gradle** file, we add the following maven dependency. This is required in order to advice gradle that it has to look for Orchextra sdk inside **jitpack.io** maven repository. Gradle file is this one:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/rootGradleScreenshot.png" width="300">

```java
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
and we add the Orchextra dependency in our **app** module like this:
```java
    compile 'com.github.Orchextra.orchextra-android-sdk:orchextrasdk:2.3.2-dev'
```

The previous dependency has to be added into this file:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/appGradleScreenshot.png" width="300">

and we must sync gradle project.

## Integrate Orchextra SDK
We have to created a class which extends from Application (if we didn't do yet) and add the Orchextra init method. We could implement OrchextraCompletionCallback interface in order to receive the orchextra status.   

```java
Orchextra.init(this, new OrchextraCompletionCallback() {              
                    @Override
                    public void onSuccess() { }              
                   @Override
                    public void onError(String s) { }
                    @Override
                    public void onInit(String s) { }
                });
```

Then, in any part of our application we should start the orchextra sdk.

```java
Orchextra.start(API_KEY, API_SECRET);
```

After calling start, you can call `Orchextra.stop()` if you need to stop all Orchextra features, so you can call again start or stop in order to fit your requirements.

## Custom Scheme - Delegate
In order to get custom schemes within our app must conform the CustomSchemeReceiver interface, the following method will handle all the custom schemes created in Orchextra.

```java
Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
            @Override
            public void onReceive(String scheme) {
                Log.i("TAG", "SCHEME: " + scheme);
            }
        });
```

## Add user to Orchextra
ORCUser class is a local representation of a user persisted to the Orchextra Database to help to create a good user segmentation. This object is optional and could be set up at any time.

```java
Orchextra.setUser(new ORCUser(CRM_ID,
                new GregorianCalendar(1990, 10, 29), //any Birth date as a calendar instance
                ORCUser.Gender.ORCGenderMale, //ORCGenderMale or ORCGenderFemale Enum
                new ArrayList<>(Arrays.asList("keyword1", "keyword2"))));
```

Regarding *Keywords* they are not used any more instead of use this constructor you should use the new one having TAG for replacing keywords, so the previous code could be something like:
```java
Orchextra.setUser(new ORCUser("123456789",
        new GregorianCalendar(1990, 10, 29), //any Birth date as a calendar instance
        ORCUser.Gender.ORCGenderMale, //ORCGenderMale or ORCGenderFemale Enum
        new ORCUserTag("tag1"),
        new ORCUserTag("tag2")));
```

 
##  Start Actions
Orchextra SDK let you invoke a couple of action within your own application to start a new user journey

### Scanner
You can scan QR and Barcode linked in Orchextra. To launch the scanner you just need to add the following line to the action.
```java
Orchextra.startScannerActivity();
```
##Customizing styles
Orchextra has default icons, colors and texts which can be overwritten. Firstly, you app must extends from **Theme.AppCompat.Light** style and overwrite the **colorPrimary**, **colorPrimaryDark** and **colorAccent** items to be applied to Orchextra styles. For example, the color of the toolbar is the color definied as colorPrimary style. 
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

## Push Notifications

### Creating a push project

First of all is important to create and configure your project in [Google Cloud Platform](https://console.cloud.google.com/home/dashboard). When your project is already create you should take care of two important fields: 

* Regarding project information _"Project Number"_ that would be the identifier of sending events you will use inside SDK.

* Regarding API information _"Server API KEY"_, this is something you have to create. _Create credentials -> API key -> Server Key_ this will generate a String taht you will have to include in your parse project.

That's all in Google console. Now you should create a project in _Parse Dashboard_ and Set up notifications for android giving _"Server API KEY"_ you created in previous step to you parse project.

### SDK config for using Push

Enabling push notifications in SDK is quite simple you should Override this two strings at your `strings.xml`

`ox_notifications_GCM_sender_id`: Override this string resource is compulsory if you want have push notifications enabled. Use here your _"Project Number"_ and **Orchextra** and **Parse** will do all stuff for you.

`ox_notification_push_title`: Override this string resource in order to get your custom Push notification title. Default is **Orchextra**.

* Do not forget about image resources, all the explanations given in **Customizing styles** section about notifications applies here for customizing push notification image resources.

Now you can check if Push notifications are working, try to send a push using _Parse Dashboard_. Try to write and send an original message and check if it works!!. 

## Image recognition integration
Image recognition is added as an add-on to Orchextra, by default SDK is not containing any Image recognition implementation, the only thing that is including is an interface that allows any of his implementations to communicate with Orchextra SDK.

So, you can add the corresponding implementation as a gradle dependency to your project, at this moment the only available implementation is using Vuforia as image recognition engine. Here you have the gradle dependency:

```groovy
compile 'com.github.GigigoGreenLabs.imgRecognition:vuforiaimplementation:1.0.1'
```

Once you have added this dependency you will be able to inform Orchextra SDK about it has to use this implementation. You can do it this way:

```java
Orchextra.setImageRecognitionModule(new ImageRecognitionVuforiaImpl());
```

This allows Orchextra to do all stuff related with image recognition. Don't forget to configure all necessary Vuforia details on [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), otherwise image recognition won't work.

To start an image recognition activity you only need to call: 

```java
Orchextra.startImageRecognition();
```

That's all!!

### Customizing Image recognition Activity

As you can see image recognition Activity is using Orchextra like ascpect, but don't worry, you can customize almost everything, take care of the following details:

#### Image Resources:
 - `ox_close_button`: close button, "x" by default, it allows to use an Android XML `selector` resource

#### Colors:
- `ir_color_primary `: main color for _Activity_ _ToolBar_. Default is `color_primary`
- `ir_color_primary_dark `: color for _StatusBar_. Default is `color_primary_dark`
- `ir_color_accent `: main color for _Activity_ _ToolBar_ text. Default is `color_accent`
- `vuforia_loading_indicator_color `: color for loading _ProgressBar_. Default is `color_accent`
- `vuforia_loading_bg_color `: color for loading screen background. Default is `color_primary`
- `ir_scan_point_color `: color scanner points. Default is `color_primary`
- `ir_scan_line_color `: color scanner line. Default is `color_accent`

#### Texts:
 - `ox_loading_indicator_message`: Message that indicates that image recognition module is being loaded
 - `ox_ir_scan_activity_name`: Name of image recognition _Activity_, appearing in _Toolbar_

## Log level
Now you can configure log level using the level you consider to get debug info from Orchextra SDK. In order to set the level, you MUST call `rchextra.setLogLevel(LOGLEVEL)` where _LOGLEVEL_ is an enum providing following info:

- `OrchextraLogLevel.ALL`: All info, even altbeacon lib logs
- `OrchextraLogLevel.NETWORK`: all debug from Orchextra plus network info 
- `OrchextraLogLevel.DEBUG`: all debug info from SDK.
- `OrchextraLogLevel.WARN`: Only SDK errors and warnings
- `OrchextraLogLevel.ERROR`: Only errors
- `OrchextraLogLevel.NONE`: Not logging at all

Default level is `OrchextraLogLevel.NONE` for releases. 

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
