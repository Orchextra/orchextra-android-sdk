# Orchextra SDK for Android
[![Build Status](https://travis-ci.org/Orchextra/orchextra-android-sdk.svg?branch=master)](https://travis-ci.org/Orchextra/orchextra-android-sdk) 
[![codecov.io](https://codecov.io/github/Orchextra/orchextra-android-sdk/coverage.svg?branch=master)](https://codecov.io/github/Orchextra/orchextra-android-sdk)
![Language](https://img.shields.io/badge/Language-Android-brightgreen.svg)
![Version](https://img.shields.io/badge/Version-2.0.3-blue.svg)
[![](https://jitpack.io/v/Orchextra/orchextra-android-sdk.svg)](https://jitpack.io/#Orchextra/orchextra-android-sdk)

A library that gives you access to Orchextra platform from your Android app.

## Getting started
Start by creating a project in [Orchextra dashboard][dashboard], if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

## Overview
Orchextra SDK is composed of **Orchextra Core**.  
#### Orchextra Core
- Geofences
- Beacons

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
    compile 'com.github.Orchextra.orchextra-android-sdk:orchextrasdk:2.0.3'
```

The previous dependency has to be added into this file:

<img src="https://github.com/Orchextra/orchextra-android-sdk/blob/master/resources/appGradleScreenshot.png" width="300">

and we must sync gradle project.

## Integrate Orchextra SDK
We have to created a class which extends from Application (if we didn't do yet) and add the Orchextra init method. We could implement OrchextraCompletionCallback interface in order to receive the orchextra status.   

```java
Orchextra.init(this, new OrchextraCompletionCallback() {              
                    @Override
                    public void onSuccess() {
                        GGGLogImpl.log("onSuccess");
                    }              
                   @Override
                    public void onError(String s) {
                        GGGLogImpl.log("onError: " + s);
                    }
                    @Override
                    public void onInit(String s) {
                        GGGLogImpl.log("onInit: " + s);
                    }
                });
```

Then, in any part of our application we should start the orchextra sdk.

```java
Orchextra.start(API_KEY, API_SECRET);
```

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

License
=======

    Copyright 2015 Orchextra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
