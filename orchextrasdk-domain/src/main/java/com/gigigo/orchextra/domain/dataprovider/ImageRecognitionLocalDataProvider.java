package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/5/16.
 */
public interface ImageRecognitionLocalDataProvider {
  BusinessObject<Vuforia> obtainVuforiaInfo();
}
