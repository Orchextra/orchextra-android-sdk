package com.gigigo.orchextra.android.mapper;

import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.Region;
import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import com.gigigo.orchextra.domain.mappers.MapperAndroidListToModelList;
import com.gigigo.orchextra.domain.mappers.MapperAndroidToModel;
import com.gigigo.orchextra.domain.mappers.MapperModelListToAndroidList;
import com.gigigo.orchextra.domain.mappers.MapperModelToAndroid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class BeaconRegionAndroidMapper implements MapperAndroidToModel<Region, OrchextraRegion>,
    MapperModelToAndroid<OrchextraRegion, Region>,
    MapperModelListToAndroidList<OrchextraRegion, Region>,
    MapperAndroidListToModelList<Region, OrchextraRegion> {

  private static final int EMPTY_MAJOR = -1;
  private static final int EMPTY_MINOR = -1;

  @Override public OrchextraRegion androidToModel(Region region) {

    Identifier uuid = region.getId1();
    Identifier major = region.getId1();
    Identifier minor = region.getId1();

    OrchextraRegion orchextraRegion = new OrchextraRegion(region.getUniqueId(),
        (uuid == null)? null : uuid.toString(),
        (major == null)? EMPTY_MAJOR : major.toInt(),
        (minor == null)? EMPTY_MINOR : major.toInt(),
        true);

      return orchextraRegion;
  }

  @Override public Region modelToAndroid(OrchextraRegion model) {

    Identifier id1 = Identifier.parse(model.getUuid());
    Identifier id2 = Identifier.parse((model.getMajor()==-1)?
        null : String.valueOf(model.getMajor()));
    Identifier id3 = Identifier.parse((model.getMinor()==-1)?
        null : String.valueOf(model.getMinor()));

    Region region = new Region(model.getCode(), id1, id2, id3);

    return region;
  }

  @Override public List<Region> modelListToAndroidList(List<OrchextraRegion> controlList) {
    List<Region> regionList = new ArrayList<>();
    for(OrchextraRegion orchextraRegion: controlList){
      regionList.add(modelToAndroid(orchextraRegion));
    }
    return regionList;
  }

  @Override public List<OrchextraRegion> androidListToModelList(List<Region> controlList) {
    List<OrchextraRegion> regionList = new ArrayList<>();

    for(Region region: controlList){
      regionList.add(androidToModel(region));
    }

    return regionList;
  }
}
