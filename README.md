# Orchextra SDK for Android

![Language](https://img.shields.io/badge/Language-Android%20Java-orange.svg)
![Version](https://img.shields.io/badge/version-0.9-blue.svg)

A library that gives you access to Orchextra platform from your Android app. 

### Getting started
Start by creating a project in [Orchextra dashboard][dashboard], if you haven't done it yet. Go to "Setting" > "SDK Configuration" to get the **api key** and **api secret**, you will need these values to start Orchextra SDK.

### Download SDK
To use *Orchextra*, head on over to the [releases][releases] page, and download the latest build "orchextraSDK.zip".

### Integrate Orchextra SDK
1. Copy the OrchextraSDK folder to your project root folder
2. Edit **settings.gradle**, and include the orchextraSDK to your project.

   ```java
   include 'yourapp', 'orchextraSDK'
   ```
3. Add the following dependencies from your Android Application to orchextraSDK. For that, edit **build.gradle** and copy the following lines on your dependencies section:

   ```java
   compile 'com.android.support:appcompat-v7:22.2.0'
   compile 'com.google.android.gms:play-services:8.1.0'
   compile 'me.dm7.barcodescanner:zbar:1.8.3'
   compile 'com.android.support:design:22.2.0'
   compile 'com.squareup.okhttp:okhttp:2.5.0'
   
   compile project (':orchextraSDK')
   ```

### Start Orchextra SDK
Edit or create an Application class (**android.app.Application**) for your project, then add the following code inside:

```java
import com.gigigo.orchextra.authentication.Orchextra;
...
 
@Override
public void onCreate() {
   super.onCreate();
   ...

   //Debug mode is default off but in order to enable it call:
   Orchextra.setDebug(true);
   ...
   Orchextra.start(this, "API_KEY", "API_SECRET", "GOOGLE_PLAY_SERVICES_SENDER_ID", orchextraCallback);
}

...

private OrchextraStartCompletionCallback orchextraCallback = new OrchextraStartCompletionCallback() {
   @Override
   public void onSuccess() {
      ...  
   }
 
   @Override
   public void onError(String errorMsg) {
      ...
   }
};
```
Don’t forget add the Application class to the (**AndroidManifest.xml**):
```xml
<application
   android:name=".App"
   ```
   
With this integration with Orchextra library your application can detect Geofences, and Beacons configured in dashboard.
   
If you want a custom icon for notifications Orchextra uses the "ic_launcher" icon for build notifications don't forget add your custom (**"drawable/ic_launcher"**) in order to get the default replaced by your own. Replace all the Screen Densities desired

### More features Orchextra Android SDK

####Customs Schemes
You can use customScheme, for communicate trigger events from Orchextra to your application.
A method can be defined to be executed when the event response is triggered by Orchextra

```java
...
 Orchextra.setCustomSchemeReceiver(new CustomSchemeReceiver() {
            @Override
            public void onReceive(String scheme) {
                Log.i("", "CUSTOM SCHEME-->" + scheme);
              }
        });
...
```

You can set the CustomSchemeReceiver(), many times as you want and wherever you want.  

####Orchextra User


For tracking, segmentation or for more integration with your application you can add your user profiles inside Orchextra.
The relationship is established through the "your_crm_id".

```java
...
 Orchextra.setUser(new ORCUser("your_crm_id",
                              new GregorianCalendar(1990,10,13),
                              ORCUser.Gender.ORCGenderMale, 
                              new ArrayList<>(Arrays.asList("keyword1", "keyword2"))));
...
```

####Open Orchextra Barcode/QR Scanner
To open Orchextra Barcode/QR Scanner from your application you need to add the next code. 

```java
...
      Orchextra.startScannerActivity();
...
```





[releases]: https://github.com/Orchextra/orchextra-android-sdk/releases
[dashboard]: https://dashboard.orchextra.io/home/

