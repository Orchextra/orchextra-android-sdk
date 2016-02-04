package com.gigigo.orchextra.android.service;

import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/2/16.
 */
public class OrchextraGcmTaskService extends GcmTaskService {

  @Override public int onRunTask(TaskParams taskParams) {
    return 0;
  }
}
