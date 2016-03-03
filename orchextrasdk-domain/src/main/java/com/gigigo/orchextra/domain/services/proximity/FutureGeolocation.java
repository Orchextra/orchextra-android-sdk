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

package com.gigigo.orchextra.domain.services.proximity;

import com.gigigo.orchextra.domain.abstractions.device.RetrieveGeolocationListener;
import com.gigigo.orchextra.domain.model.vo.GeoLocation;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class FutureGeolocation implements Future<GeoLocation>, RetrieveGeolocationListener {

  private volatile GeoLocation geoLocation = null;
  private volatile boolean cancelled = false;
  private final CountDownLatch countDownLatch;

  public FutureGeolocation() {
    countDownLatch = new CountDownLatch(1);
  }

  @Override public boolean cancel(final boolean mayInterruptIfRunning) {
    if (isDone()) {
      return false;
    } else {
      countDownLatch.countDown();
      cancelled = true;
      return !isDone();
    }
  }

  @Override public GeoLocation get() throws InterruptedException, ExecutionException {
    countDownLatch.await();
    return geoLocation;
  }

  @Override public GeoLocation get(final long timeout, final TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    countDownLatch.await(timeout, unit);
    return geoLocation;
  }

  @Override public boolean isCancelled() {
    return cancelled;
  }

  @Override public boolean isDone() {
    return countDownLatch.getCount() == 0;
  }

  public void onGeolocationResult(final GeoLocation geoLocation) {
    this.geoLocation = geoLocation;
    countDownLatch.countDown();
  }

  @Override public void retrieveGeolocation(GeoLocation geoLocation) {
    onGeolocationResult(geoLocation);
  }
}
