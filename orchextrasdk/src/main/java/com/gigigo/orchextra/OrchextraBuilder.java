package com.gigigo.orchextra;

import android.app.Application;

import com.gigigo.imagerecognitioninterface.ImageRecognition;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class OrchextraBuilder {

    private String apiKey;
    private String apiSecret;
    private OrchextraManagerCompletionCallback orchextraCompletionCallback;

    /**
     * Set the Api key and secret in Orchextra defined in the Orchextra dashboard
     */
//    public OrchextraBuilder setApiKeyAndSecret(String apiKey, String apiSecret) {
//        this.apiKey = apiKey;
//        this.apiSecret = apiSecret;
//        return this;
//    }

    /**
     * Callback status when orchextra is initialized
     */
    public OrchextraBuilder setOrchextraCompletionCallback(OrchextraManagerCompletionCallback orchextraCompletionCallback) {
        this.orchextraCompletionCallback = orchextraCompletionCallback;
        return this;
    }

    /**
     * Level of log which will print to console.
     *
     * @param orchextraLogLevel <p>OrchextraSDKLogLevel.NONE Print nothing</p>
     *                          <p>OrchextraSDKLogLevel.ERROR Print only error logs</p>
     *                          <p>OrchextraSDKLogLevel.WARN Print warn logs</p>
     *                          <p>OrchextraSDKLogLevel.DEBUG Print debug logs</p>
     *                          <p>OrchextraSDKLogLevel.NETWORK Print debug and network logs</p>
     *                          <p>OrchextraSDKLogLevel.ALL Print debug, network and bluethoot logs</p>
     */
    public OrchextraBuilder setLogLevel(OrchextraLogLevel orchextraLogLevel) {
        OrchextraManager.setLogLevel(orchextraLogLevel);
        return this;
    }

    /**
     * Include the image recognition module in Orchextra core.
     * <a href="https://github.com/Orchextra/orchextra-android-sdk#image-recognition-integration">For more info, visit this link</a>.
     */
    public OrchextraBuilder setImageRecognitionModule(ImageRecognition imageRecognitionModule) {
        OrchextraManager.setImageRecognition(imageRecognitionModule);
        return this;
    }

    /**
     * Initialize Orchextra with all the configuration. It only must be call the first time in the onCreate method in the application.
     * @param application The application of the app
     */
    public void initialize(Application application) {
        OrchextraManager.sdkInit(application, orchextraCompletionCallback);
    }
}
