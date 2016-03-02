package com.gigigo.orchextra.domain.model.entities.proximity;

import com.gigigo.orchextra.domain.model.ProximityPointType;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public class ProximityPoint {

  private String id;
  private String code;
  private String name;
  private List<String> tags;
  private ProximityPointType type;
  private Date createdAt;
  private Date updatedAt;
  private boolean notifyOnExit;
  private boolean notifyOnEntry;
  private int stayTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public ProximityPointType getType() {
    return type;
  }

  public void setType(ProximityPointType type) {
    this.type = type;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isNotifyOnExit() {
    return notifyOnExit;
  }

  public void setNotifyOnExit(boolean notifyOnExit) {
    this.notifyOnExit = notifyOnExit;
  }

  public boolean isNotifyOnEntry() {
    return notifyOnEntry;
  }

  public void setNotifyOnEntry(boolean notifyOnEntry) {
    this.notifyOnEntry = notifyOnEntry;
  }

  public int getStayTime() {
    return stayTime;
  }

  public void setStayTime(int stayTime) {
    this.stayTime = stayTime;
  }
}
