# Orchextra SDK for Android
![Language](https://img.shields.io/badge/Language-Android-brightgreen.svg)
![Version](https://img.shields.io/badge/Version-2.0.0-blue.svg)

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
We have to add the gradle dependencies. In our **build.gradle** file, we add the next maven dependency; 

```java
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```
and we add the Orchextra dependency in our app module like this:
```java
    compile 'com.github.Orchextra.orchextra-android-sdk:orchextrasdk:2.0.0'
```
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
Then, in any part of our application we should start the orchextra sdk
```java
Orchextra.start("f7ae1c1bb7a556fdc9b517706d6f7a0c041334d9", "1a9e4aeadc38b3234b0cd119279ddfb1d9bf7f28");
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
Orchextra.setUser(new ORCUser("1234567890",
                new GregorianCalendar(1990, 10, 13),
                ORCUser.Gender.ORCGenderMale,
                new ArrayList<>(Arrays.asList("keyword1", "keyword2"))));
```


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
