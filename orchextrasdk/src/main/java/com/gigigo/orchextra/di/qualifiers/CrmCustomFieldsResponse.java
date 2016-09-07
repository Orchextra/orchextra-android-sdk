package com.gigigo.orchextra.di.qualifiers;

import java.lang.annotation.Retention;

import orchextra.javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface CrmCustomFieldsResponse {
}
