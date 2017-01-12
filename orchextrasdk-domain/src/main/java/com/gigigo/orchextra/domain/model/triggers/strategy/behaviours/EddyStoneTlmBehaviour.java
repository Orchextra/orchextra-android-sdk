package com.gigigo.orchextra.domain.model.triggers.strategy.behaviours;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraTLMEddyStoneBeacon;
import com.gigigo.orchextra.domain.model.triggers.strategy.SupportedBehaviour;

/**
 * Created by nubor on 11/01/2017.
 */
public interface EddyStoneTlmBehaviour extends SupportedBehaviour {
  OrchextraTLMEddyStoneBeacon getEddyStoneTlmData();
}
