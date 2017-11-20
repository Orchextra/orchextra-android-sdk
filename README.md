# Orchextra SDK for Android
[![Build Status](https://travis-ci.org/Orchextra/orchextra-android-sdk.svg?branch=ocm_integration)](https://travis-ci.org/Orchextra/orchextra-android-sdk)
[![codecov.io](https://codecov.io/github/Orchextra/orchextra-android-sdk/coverage.svg?branch=master)](https://codecov.io/github/Orchextra/orchextra-android-sdk)
[![Download](https://api.bintray.com/packages/gigigo-desarrollo/maven/com.gigigo.orchextra%3Acore/images/download.svg)](https://bintray.com/gigigo-desarrollo/maven/com.gigigo.orchextra%3Acore/_latestVersion)

A library that gives you access to Orchextra platform from your Android sdkVersionAppInfo.

# Getting started
Start by creating a project in [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

# Overview
Orchextra SDK is composed of **Orchextra Core**, and add-ons

#### Orchextra Core
- Geofences
- IBeacons
- Push Notifications
- Barcode/qr Scanner
- Image recognition

## Add the dependency

Add gigigo maven repository 

```groovy
allprojects {
   repositories {
       maven {
           url  "https://dl.bintray.com/gigigo-desarrollo/maven" 
       }
   }
}
```

Add dependencies you need

```groovy
  compile 'com.gigigo.orchextra:core:x.x.x'
  compile 'com.gigigo.orchextra:geofence:x.x.x'
  compile 'com.gigigo.orchextra:indoorpositioning:x.x.x'
  compile 'com.gigigo.orchextra:scanner:x.x.x'
```

### Init Orchextra

```java
  Orchextra orchextra = Orchextra.INSTANCE;
  orchextra.setStatusListener(orchextraStatusListener);
  orchextra.setErrorListener(orchextraErrorListener);
  
  OrchextraOptions options =
      new OrchextraOptions.Builder().firebaseApiKey("AIza_1234")
          .firebaseApplicationId("xxxx")
          .debuggable(true)
          .build();
  
  orchextra.init(getApplication(), apiKey, apiSecret, options);
  orchextra.setScanTime(30);
    
```

### Add trigger implementation

```java
  orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(getApplication()));
```

### Get Orchextra errors

```java
  orchextra.setErrorListener(new OrchextraErrorListener() {
    @Override public void onError(@NonNull Error error) {
      hideLoading();
      Log.e(TAG, error.toString());
      Toast.makeText(EditActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT)
          .show();
    }
  });
```

### Custom actions

```java
  orchextra.setCustomActionListener(new CustomActionListener() {
    @Override public void onCustomSchema(@NonNull String customSchema) {
      Toast.makeText(MainActivity.this, "CustomSchema: " + customSchema, Toast.LENGTH_LONG)
          .show();
    }
  });
```

### Get Auth token

```java
  orchextra.getToken(new OrchextraTokenReceiver() {
    @Override public void onGetToken(@NonNull String oxToken) {
      Toast.makeText(MainActivity.this, "Token:" + oxToken, Toast.LENGTH_SHORT).show();
    }
  });
```

## Deploy Orchextra to [Gigigo repository](https://bintray.com/gigigo-desarrollo/maven)

Update `version` in `dependencies.gradle` and execute `deployLib` gradle command.

```gradle
  ./gradlew deployLib
```


License
=======

    Copyright 2017 Orchextra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

