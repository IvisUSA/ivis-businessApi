package com.ivis.Businessentity;

import java.time.LocalDateTime;
import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author iv4179
 *
 */
public class CamerasEntity {
	
	public int id;
	public int indexNo;
	public int potentialId;
	public String cameraId;
	public String deviceId;
	public String name;
	public String serverHost;
	public int serverPort;
	public String serverCameraId;
	public String rtspUrl;
	public String userName;
	public String password;
	public int width;
	public int height;
	public int fps;
	public String onvifUrl;
	public String ptz;
	public String active;
	public String hTTPTunnel;
	public String pushType;
	public int serverHttpPort;
	public int eventDuration;
	public int eventFrames;
	public String eventPushUrl;
	public String motionDetectionType;
	public String motionDetectionLevel;
	public int gprsEventDuration;
	public String archiveType;
	public String archiveLocation;
	public int archiveDays;
	public String transcode;
	public int spf;
	public int bitrate;
	public int snapshotDuration;
	public String eventOnSms;
	public int motiondetectionId;
	public String eventPushType;
	public String motionAnalytics;
	public String cameraType;
	public String gtEnabled;
	public String hbCheck;
	public String displayName;
	public int eventPushRetryCount;
	public String category;
	public String status;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Calendar timestamp;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	public Calendar getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIndexNo() {
		return indexNo;
	}
	public void setIndexNo(int indexNo) {
		this.indexNo = indexNo;
	}
	public int getPotentialId() {
		return potentialId;
	}
	public void setPotentialId(int potentialId) {
		this.potentialId = potentialId;
	}
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public String getServerCameraId() {
		return serverCameraId;
	}
	public void setServerCameraId(String serverCameraId) {
		this.serverCameraId = serverCameraId;
	}
	public String getRtspUrl() {
		return rtspUrl;
	}
	public void setRtspUrl(String rtspUrl) {
		this.rtspUrl = rtspUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getFps() {
		return fps;
	}
	public void setFps(int fps) {
		this.fps = fps;
	}
	public String getOnvifUrl() {
		return onvifUrl;
	}
	public void setOnvifUrl(String onvifUrl) {
		this.onvifUrl = onvifUrl;
	}
	public String getPtz() {
		return ptz;
	}
	public void setPtz(String ptz) {
		this.ptz = ptz;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String gethTTPTunnel() {
		return hTTPTunnel;
	}
	public void sethTTPTunnel(String hTTPTunnel) {
		this.hTTPTunnel = hTTPTunnel;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public int getServerHttpPort() {
		return serverHttpPort;
	}
	public void setServerHttpPort(int serverHttpPort) {
		this.serverHttpPort = serverHttpPort;
	}
	public int getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(int eventDuration) {
		this.eventDuration = eventDuration;
	}
	public int getEventFrames() {
		return eventFrames;
	}
	public void setEventFrames(int eventFrames) {
		this.eventFrames = eventFrames;
	}
	public String getEventPushUrl() {
		return eventPushUrl;
	}
	public void setEventPushUrl(String eventPushUrl) {
		this.eventPushUrl = eventPushUrl;
	}
	public String getMotionDetectionType() {
		return motionDetectionType;
	}
	public void setMotionDetectionType(String motionDetectionType) {
		this.motionDetectionType = motionDetectionType;
	}
	public String getMotionDetectionLevel() {
		return motionDetectionLevel;
	}
	public void setMotionDetectionLevel(String motionDetectionLevel) {
		this.motionDetectionLevel = motionDetectionLevel;
	}
	public int getGprsEventDuration() {
		return gprsEventDuration;
	}
	public void setGprsEventDuration(int gprsEventDuration) {
		this.gprsEventDuration = gprsEventDuration;
	}
	public String getArchiveType() {
		return archiveType;
	}
	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}
	public String getArchiveLocation() {
		return archiveLocation;
	}
	public void setArchiveLocation(String archiveLocation) {
		this.archiveLocation = archiveLocation;
	}
	public int getArchiveDays() {
		return archiveDays;
	}
	public void setArchiveDays(int archiveDays) {
		this.archiveDays = archiveDays;
	}
	public String getTranscode() {
		return transcode;
	}
	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}
	public int getSpf() {
		return spf;
	}
	public void setSpf(int spf) {
		this.spf = spf;
	}
	public int getBitrate() {
		return bitrate;
	}
	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}
	public int getSnapshotDuration() {
		return snapshotDuration;
	}
	public void setSnapshotDuration(int snapshotDuration) {
		this.snapshotDuration = snapshotDuration;
	}
	public String getEventOnSms() {
		return eventOnSms;
	}
	public void setEventOnSms(String eventOnSms) {
		this.eventOnSms = eventOnSms;
	}
	public int getMotiondetectionId() {
		return motiondetectionId;
	}
	public void setMotiondetectionId(int motiondetectionId) {
		this.motiondetectionId = motiondetectionId;
	}
	public String getEventPushType() {
		return eventPushType;
	}
	public void setEventPushType(String eventPushType) {
		this.eventPushType = eventPushType;
	}
	public String getMotionAnalytics() {
		return motionAnalytics;
	}
	public void setMotionAnalytics(String motionAnalytics) {
		this.motionAnalytics = motionAnalytics;
	}
	public String getCameraType() {
		return cameraType;
	}
	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}
	public String getGtEnabled() {
		return gtEnabled;
	}
	public void setGtEnabled(String gtEnabled) {
		this.gtEnabled = gtEnabled;
	}
	public String getHbCheck() {
		return hbCheck;
	}
	public void setHbCheck(String hbCheck) {
		this.hbCheck = hbCheck;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public int getEventPushRetryCount() {
		return eventPushRetryCount;
	}
	public void setEventPushRetryCount(int eventPushRetryCount) {
		this.eventPushRetryCount = eventPushRetryCount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
