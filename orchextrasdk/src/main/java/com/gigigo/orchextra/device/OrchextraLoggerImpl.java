package com.gigigo.orchextra.device;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraSDKLogLevel;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class OrchextraLoggerImpl implements OrchextraLogger {

  public OrchextraSDKLogLevel orchextraSDKLogLevel;

  public OrchextraLoggerImpl() {
    this.orchextraSDKLogLevel = OrchextraManager.getLogLevel();
    GGGLogImpl.log("log Level is :: " + orchextraSDKLogLevel.getStringValue(), LogLevel.DEBUG, "OrchextraSDK");
  }

  @Override public synchronized void  log(String message) {
    logLibCall(message, OrchextraSDKLogLevel.DEBUG);
  }

  @Override public synchronized void log(String message, OrchextraSDKLogLevel orchextraSDKLogLevel) {
    logLibCall(message, orchextraSDKLogLevel);
  }

  private void logLibCall(String message, OrchextraSDKLogLevel orchextraSDKLogLevel) {
    if (this.orchextraSDKLogLevel.intValue() <= orchextraSDKLogLevel.intValue()) {
      LogLevel level = orchextraLogLevelToGGGLogLevel(orchextraSDKLogLevel);
      GGGLogImpl.log("OrchextraSDK:: " + message, level, "OrchextraSDK", 2);
    }
  }

  @Override public synchronized boolean isNetworkLoggingLevelEnabled() {
    return (orchextraSDKLogLevel.intValue() <= OrchextraSDKLogLevel.NETWORK.intValue());
  }

  @Override public synchronized OrchextraSDKLogLevel getOrchextraSDKLogLevel() {
    return orchextraSDKLogLevel;
  }

  private LogLevel orchextraLogLevelToGGGLogLevel(OrchextraSDKLogLevel orchextraSDKLogLevel){
    switch (orchextraSDKLogLevel){
      case ERROR:
        return LogLevel.ERROR;
      case WARN:
        return LogLevel.WARN;
      case ALL:
      case DEBUG:
      default:
        return LogLevel.DEBUG;
    }
  }

}
