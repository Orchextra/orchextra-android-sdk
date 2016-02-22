package com.gigigo.orchextra.device.bluetooth.beacons.mapper;

import com.gigigo.ggglib.mappers.ExternalClassListToModelListMapper;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.ModelListToExternalClassListMapper;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class BeaconRegionAndroidMapper implements
    ExternalClassToModelMapper<Region, OrchextraRegion>,
    ModelToExternalClassMapper<OrchextraRegion, Region>,
    ModelListToExternalClassListMapper<OrchextraRegion, Region>,
    ExternalClassListToModelListMapper<Region, OrchextraRegion> {

  private static final int EMPTY_MAJOR = -1;
  private static final int EMPTY_MINOR = -1;

  @Override public OrchextraRegion externalClassToModel(Region region) {

    Identifier uuid = region.getId1();
    Identifier major = region.getId2();
    Identifier minor = region.getId3();

    OrchextraRegion orchextraRegion = new OrchextraRegion(region.getUniqueId(),
        (uuid == null)? null : uuid.toString(),
        (major == null)? EMPTY_MAJOR : major.toInt(),
        (minor == null)? EMPTY_MINOR : major.toInt(),
        true);

      return orchextraRegion;
  }

  @Override public Region modelToExternalClass(OrchextraRegion model) {

    Identifier id1 = Identifier.parse(model.getUuid());

    Identifier id2 = (model.getMajor() == EMPTY_MAJOR)? null :
        Identifier.parse(String.valueOf(model.getMajor()));

    Identifier id3 = (model.getMinor() == EMPTY_MINOR)? null :
        Identifier.parse(String.valueOf(model.getMinor()));

    Region region = new Region(model.getCode(), id1, id2, id3);

    return region;
  }

  @Override public List<Region> modelListToExternalClassList(List<OrchextraRegion> controlList) {
    List<Region> regionList = new ArrayList<>();
    for(OrchextraRegion orchextraRegion: controlList){
      regionList.add(modelToExternalClass(orchextraRegion));
    }
    return regionList;
  }

  @Override public List<OrchextraRegion> externalClassListToModelList(List<Region> controlList) {
    List<OrchextraRegion> regionList = new ArrayList<>();

    for(Region region: controlList){
      regionList.add(externalClassToModel(region));
    }

    return regionList;
  }
}
