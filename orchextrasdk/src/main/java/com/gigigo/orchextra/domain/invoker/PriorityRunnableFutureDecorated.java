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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PriorityRunnableFutureDecorated<T> implements RunnableFuture<T> {

  private RunnableFuture<T> undecoratedFuture;
  private int priority;

  public PriorityRunnableFutureDecorated(RunnableFuture<T> undecoratedFuture, int priority) {
    this.undecoratedFuture = undecoratedFuture;
    this.priority = priority;
  }

  @Override public void run() {
    undecoratedFuture.run();
  }

  @Override public boolean cancel(boolean b) {
    return undecoratedFuture.cancel(b);
  }

  @Override public boolean isCancelled() {
    return undecoratedFuture.isCancelled();
  }

  @Override public boolean isDone() {
    return undecoratedFuture.isDone();
  }

  @Override public T get() throws InterruptedException, ExecutionException {
    return undecoratedFuture.get();
  }

  @Override public T get(long l, TimeUnit timeUnit)
      throws InterruptedException, ExecutionException, TimeoutException {
    return undecoratedFuture.get();
  }

  public int getPriority() {
    return priority;
  }
}
