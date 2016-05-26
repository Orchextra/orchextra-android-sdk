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

package com.gigigo.orchextra.domain.model.entities.authentication;

public class CrmTag {

  private final String prefix;
  private final String name;

  public CrmTag(String prefix, String name) {
    this.prefix = prefix;
    this.name = name;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getName() {
    return name;
  }

  @Override public String toString() {
    String toString = (this.prefix != null) ? this.prefix + "::" : "";
    toString = toString + name;
    return toString;
  }
}
