package com.gigigo.orchextra.domain.invoker;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;

public class LogExceptionHandler implements Thread.UncaughtExceptionHandler {

  @Override public void uncaughtException(Thread thread, Throwable ex) {
    GGGLogImpl.log("Unhandled Interactor Exception: " + ex.getMessage(), LogLevel.ERROR);
  }
}
