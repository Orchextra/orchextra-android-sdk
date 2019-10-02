# Orchextra SDK for Android
[![Build Status](https://travis-ci.org/Orchextra/orchextra-android-sdk.svg?branch=master)](https://travis-ci.org/Orchextra/orchextra-android-sdk)
[![codecov.io](https://codecov.io/github/Orchextra/orchextra-android-sdk/coverage.svg?branch=master)](https://codecov.io/github/Orchextra/orchextra-android-sdk)
[![Download](https://api.bintray.com/packages/gigigo-desarrollo/maven/com.gigigo.orchextra%3Acore/images/download.svg)](https://bintray.com/gigigo-desarrollo/maven/com.gigigo.orchextra%3Acore/_latestVersion)

A library that gives you access to Orchextra platform from your Android sdkVersionAppInfo.

# Getting started
Start by creating a project in [Orchextra Dashboard](https://dashboard.orchextra.io/start/login), if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

# Overview
Orchextra SDK is composed of **Orchextra Core**, and add-on


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
  implementation 'com.gigigo.orchextra:core:x.x.x'
  implementation 'com.gigigo.orchextra:geofence:x.x.x'
  implementation 'com.gigigo.orchextra:indoorpositioning:x.x.x'
  implementation 'com.gigigo.orchextra:scanner:x.x.x'
```

Staging environment
```groovy
  stagingImplementation 'com.gigigo.orchextra:core:x.x.x-S'
  stagingImplementation 'com.gigigo.orchextra:geofence:x.x.x-S'
  stagingImplementation 'com.gigigo.orchextra:indoorpositioning:x.x.x-S'
  stagingImplementation 'com.gigigo.orchextra:scanner:x.x.x-S'
```
Quality environment
```groovy
  debugImplementation 'com.gigigo.orchextra:core:x.x.x-Q'
  debugImplementation 'com.gigigo.orchextra:geofence:x.x.x-Q'
  debugImplementation 'com.gigigo.orchextra:indoorpositioning:x.x.x-Q'
  debugImplementation 'com.gigigo.orchextra:scanner:x.x.x-Q'
```

### Init Orchextra

```java
  Orchextra orchextra = Orchextra.INSTANCE;
  orchextra.setStatusListener(orchextraStatusListener);
  orchextra.setErrorListener(orchextraErrorListener);

  OrchextraOptions options =
      new OrchextraOptions.Builder().firebaseApiKey(config.getFirebaseApiKey())
          .firebaseApplicationId(config.getFirebaseApplicationId())
          .deviceBusinessUnits(deviceBusinessUnits)
          .triggeringEnabled(true)
          .anonymous(false)
          .debuggable(true)
          .build();
  
  orchextra.init(getApplication(), apiKey, apiSecret, options);
  orchextra.setScanTime(30);
    
```

### Add trigger implementation

```java
  orchextra.getTriggerManager().setGeofence(OxGeofenceImp.Factory.create(getApplication()));
```

### Set notification activity

```java
   orchextra.setNotificationActivityClass(MainActivity.class);
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
  orchextra.setOrchextraCustomActionListener(new CustomActionListener() {
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

Add `bintray.user` and `bintray.apikey` on your **local.propeties**
Update `version` in `dependencies.gradle` and execute `deployLib` gradle command.

```gradle
  ./gradlew deployLib
```


License
=======

    Copyright 2018 Orchextra

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

