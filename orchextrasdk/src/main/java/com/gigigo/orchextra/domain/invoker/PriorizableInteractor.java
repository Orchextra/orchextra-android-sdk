package com.gigigo.orchextra.domain.invoker;

public interface PriorizableInteractor {
  int getPriority();
  String getDescription();
}
