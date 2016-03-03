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

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class InteractorPriorityBlockingQueue extends PriorityBlockingQueue<Runnable> {

  public InteractorPriorityBlockingQueue(int initialCapacity) {
    super(initialCapacity, new Comparator<Runnable>() {
      @Override public int compare(Runnable runnable1, Runnable runnable2) {
        boolean firstIsPriority = runnable1 instanceof PriorityRunnableFutureDecorated;
        boolean secondIsPriority = runnable2 instanceof PriorityRunnableFutureDecorated;

        if (!firstIsPriority && !secondIsPriority) {
          return 0;
        } else if (!firstIsPriority) {
          return -1;
        } else if (!secondIsPriority) {
          return 1;
        }

        int priority1 = ((PriorityRunnableFutureDecorated) runnable1).getPriority();
        int priority2 = ((PriorityRunnableFutureDecorated) runnable2).getPriority();

        return Integer.valueOf(priority1).compareTo(priority2);
      }
    });
  }
}
