package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraTLMEddyStoneBeacon;

/**
 * Created by nubor on 11/01/2017.
 */
public class EddyStoneTlmBehaviourImpl implements EddyStoneTlmBehaviour {

  private OrchextraTLMEddyStoneBeacon tlmData = null;

  public EddyStoneTlmBehaviourImpl(OrchextraTLMEddyStoneBeacon tlmData) {
    this.tlmData = tlmData;
  }

  @Override public boolean isSupported() {
    return (tlmData == null) ? false : true;
  }

  @Override
  public OrchextraTLMEddyStoneBeacon getEddyStoneTlmData() {
    return tlmData;
  }
}
