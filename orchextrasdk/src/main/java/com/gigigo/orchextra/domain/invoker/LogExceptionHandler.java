/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.invoker;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;

public class LogExceptionHandler implements Thread.UncaughtExceptionHandler {

  @Override public void uncaughtException(Thread thread, Throwable ex) {
    if (ex!=null){
      ex.printStackTrace();
      GGGLogImpl.log("Unhandled Interactor Exception: " + ex.getMessage(), LogLevel.ERROR);
    }else{
      GGGLogImpl.log("Unhandled Interactor Exception: exception trace not available");
    }
  }
}
