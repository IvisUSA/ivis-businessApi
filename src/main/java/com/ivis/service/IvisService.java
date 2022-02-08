package com.ivis.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ivis.ApplicationModels.Analysis;
import com.ivis.ApplicationModels.Services;
import com.ivis.ApplicationModels.UserLogin;
import com.ivis.ApplicationModels.account;
import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.Businessentity.BusinessCamesStreamEntity;
import com.ivis.Businessentity.BusinessEntity;
import com.ivis.Businessentity.CamerasStreamEntity;
import com.ivis.Businessentity.SitesEntity;
import com.ivis.Businessentity.UserEntity;
import com.ivis.util.ReadJson;
import com.ivis.util.util;

@Service
public class IvisService {

	public UserEntity getImMatrixAvailability(String uId) {

		UserEntity u = new UserEntity();
		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/GetSitesListForUser_1_0?uId=";
		sitesUrl = sitesUrl + uId;

		String response = util.readUrlData(sitesUrl);

		Gson gson = new Gson();

		String camResponse = util.readUrlData(sitesUrl);
		List<BusinessEntity> devList = new ArrayList<BusinessEntity>();
		;
		SitesEntity siteData = (SitesEntity) new Gson().fromJson(camResponse, SitesEntity.class);

		List<account> sitesList = siteData.getSiteList();

		for (account s : sitesList) {
			BusinessEntity sites = new BusinessEntity();
			sites.setSiteid(s.getAccountId());
			sites.setSitename(s.getAccountName());
			sites.setProject(s.getFacility());
			sites.setAtmid(s.getCustomerSiteId());
			sites.setState(s.getState());
			sites.setCity(s.getCity());
			sites.setZone(s.getZone());
			sites.setPhase(s.getBatchNo());
			sites.setDeviceInternalId(s.getPotentialId());
			sites.setDeviceExternalId(s.getUnitId());
			sites.setLocationType(s.getSiteType());
			sites.setBusinessvertical(s.getIndustry());
			sites.setLatitude(s.getLatitude());
			sites.setLongitude(s.getLongitude());
			devList.add(sites);

		}

		u.setAddress(siteData.getAddress());
		u.setContact(siteData.getContact());
		u.setEmail(siteData.getEmail());
		u.setUserName(siteData.getUserName());
		u.setUserRole(siteData.getUserRole());
		u.setUserType(siteData.getUserType());
		u.setSiteList(devList);
		return u;

	}

	public List<BusinessCamesStreamEntity> getCamerasList(String uId, String accountId) {
		String camerasUrl2 = "http://smstaging.iviscloud.net:8090/cpus/cameras/CameraStreamList_1_0?uId=";
		camerasUrl2 = camerasUrl2 + uId + "&accountId=" + accountId;
		String response2 = util.readUrlData(camerasUrl2);

		Type collectionType2 = new TypeToken<Collection<CamerasStreamEntity>>() {
		}.getType();
		List<CamerasStreamEntity> camesData2 = (List<CamerasStreamEntity>) new Gson().fromJson(response2, collectionType2);
		String camerasUrl = "http://smstaging.iviscloud.net:8090/cpus/cameras/CameraListforSiteId_1_0?uId=";
		camerasUrl = camerasUrl + uId + "&accountId=" + accountId;
		String response = util.readUrlData(camerasUrl);

		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<CamerasStreamEntity>>() {
		}.getType();
		List<CamerasStreamEntity> camesData = (List<CamerasStreamEntity>) new Gson().fromJson(response, collectionType);
		List<BusinessCamesStreamEntity> camesList = new ArrayList<BusinessCamesStreamEntity>();
		for (CamerasStreamEntity c : camesData) {
			BusinessCamesStreamEntity cames = new BusinessCamesStreamEntity();
			CamerasStreamEntity camsDataSecondAPI = new CamerasStreamEntity();
			for(CamerasStreamEntity c2 : camesData2)
			{
				if(c2.getCameraId().equals(c.getCameraId()))
				{
					camsDataSecondAPI.setStreamingUrl(c2.getStreamingUrl());
					camsDataSecondAPI.setStreamingType(c2.getStreamingType());
					camsDataSecondAPI.setHlsEnabled(c2.getHlsEnabled());
				}
			}
			
			cames.setCameraIndex(c.getId());
			cames.setIndexNo(c.getIndexNo());
			cames.setDeviceInternalId(c.getPotentialId());
			cames.setCameraId(c.getCameraId());
			cames.setDeviceExternalId(c.getDeviceId());
			cames.setCameraName(c.getName());
			cames.setServerHost(c.getServerHost());
			cames.setServerPort(c.getServerPort());
			cames.setServerCameraId(c.getServerCameraId());
			cames.setRtspUrl(c.getRtspUrl());
			cames.setUserName(c.getUserName());
			cames.setPassword(c.getPassword());
			cames.setWidth(c.getWidth());
			cames.setHeight(c.getHeight());
			cames.setFps(c.getFps());
			cames.setOnvifUrl(c.getOnvifUrl());
			cames.setPtz(c.getPtz());
			cames.setActive(c.getActive());
			cames.sethTTPTunnel(c.gethTTPTunnel());
			cames.setPushType(c.getPushType());
			cames.setServerHttpPort(c.getServerHttpPort());
			cames.setEventDuration(c.getEventDuration());
			cames.setEventFrames(c.getEventFrames());
			cames.setEventPushUrl(c.getEventPushUrl());
			cames.setMotionDetectionType(c.getMotionDetectionType());
			cames.setMotionDetectionLevel(c.getMotionDetectionLevel());
			cames.setGprsEventDuration(c.getGprsEventDuration());
			cames.setArchiveType(c.getArchiveType());
			cames.setArchiveLocation(c.getArchiveLocation());
			cames.setArchiveDays(c.getArchiveDays());
			cames.setTranscode(c.getTranscode());
			cames.setSpf(c.getSpf());
			cames.setBitrate(c.getBitrate());
			cames.setSnapshotDuration(c.getSnapshotDuration());
			cames.setEventOnSms(c.getEventOnSms());
			cames.setMotiondetectionId(c.getMotiondetectionId());
			cames.setEventPushType(c.getEventPushType());
			cames.setMotionAnalytics(c.getMotionAnalytics());
			cames.setCameraType(c.getCameraType());
			cames.setGtEnabled(c.getGtEnabled());
			cames.setHbCheck(c.getHbCheck());
			cames.setDisplayName(c.getDisplayName());
			cames.setEventPushRetryCount(c.getEventPushRetryCount());
			cames.setCategory(c.getCategory());
			
			String c1StatusUrl = "http://usvs1.iviscloud.net:7888/Command?action=";
			c1StatusUrl = c1StatusUrl + "status" + "&cameraId=" + c.getCameraId();
			
			String resp = util.readUrlData(c1StatusUrl);
			JSONArray camsjson = new JSONArray(resp);
			cames.setCameraStatus(camsjson.getJSONObject(0).get("status").toString());		
			
			
			cames.setStreamingUrl(camsDataSecondAPI.getStreamingUrl());
			cames.setStreamingType(camsDataSecondAPI.getStreamingType());
			cames.setHlsEnabled(camsDataSecondAPI.getHlsEnabled());
			camesList.add(cames);
			// JSONArray respArray = new JSONArray(resp);
			// JSONObject ob = respArray.getJSONObject(0);
			// String cId = ob.getString("cameraId");

			/*
			 * if (cId != null && cId.equalsIgnoreCase(c.getCameraId())) { String time = "";
			 * String camera_status = ob.getString("status");
			 * 
			 * if (camera_status != null && camera_status.equalsIgnoreCase("Connected")) {
			 * time = ob.getString("timeStamp"); cam_status = true; } else if (camera_status
			 * != null && camera_status.equalsIgnoreCase("Disconnected")) { cam_status =
			 * false; }
			 * 
			 * cames.setCameraStatus(camera_status); cames.setTimeStamp(time);
			 * camesList.add(cames); }
			 */
		}

		return camesList;

	}

	public List<BIAnalyticsEntity> getBusinessAnalystics(int accountId, Date date) {
		List<BIAnalyticsEntity> bIAnalytics = new ArrayList<BIAnalyticsEntity>();

		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + accountId;

		String client_id = util.readUrlData(sitesUrl);

		HttpPost post = new HttpPost("https://ivisbi.com/v2/api/getServices");
		HttpPost post2 = new HttpPost("https://ivisbi.com/v2/api/analytics");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));

		if(!(date==null))
		{
			urlParameters.add(new BasicNameValuePair("date", date.toString()));

		}
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			Type collection = new TypeToken<Services>() {
			}.getType();
			Services services = new Gson().fromJson(input, collection);

			for (com.ivis.ApplicationModels.Service s : services.getServices()) {

				urlParameters.add(new BasicNameValuePair("parameter", s.getId().toString()));
				post2.setEntity(new UrlEncodedFormEntity(urlParameters));
				CloseableHttpResponse response2 = httpClient.execute(post2);

				int statusCode = response2.getStatusLine().getStatusCode();
				BIAnalyticsEntity biEntity = new BIAnalyticsEntity();

				if (statusCode == 200) {
					String result = EntityUtils.toString(response2.getEntity());

					JsonObject jsonResp = new Gson().fromJson(result, JsonObject.class);
					JsonArray analysis = jsonResp.getAsJsonArray(s.getParameter());
					Type collectionType = new TypeToken<List<Analysis>>() {
					}.getType();
					List<Analysis> analyticsObj = new Gson().fromJson(analysis, collectionType);

					biEntity.setService(s.getParameter());
					biEntity.setAnalytics(analyticsObj);

				} else {
					biEntity.setService(s.getParameter());
					biEntity.setAnalytics(new ArrayList<Analysis>());
				}
				bIAnalytics.add(biEntity);
				urlParameters.remove(1);
			}

		} catch (Exception e) {
			System.err.println(e);
		}

		return bIAnalytics;
	}

	public Object mapServices2(String url,String Request_type, String accountId ) {

		ReadJson reader = new ReadJson();
		HashMap<Object, Object> mappeddata = new HashMap<Object, Object>();
		HashMap<Object, Object> data = new HashMap<Object, Object>();
		org.json.JSONObject json;
		try {
			json = reader.readJsonFromUrl(url);

			mappeddata.put("SiteId", json.get("accountId"));

			mappeddata.put("SiteName", json.get("siteName"));

			if(Request_type.equals("Services")) {
			data.put("alarms", json.get("monitoring"));
			data.put("safety_escort", json.get("safety_escort"));
			data.put("LiveView", (((json.getInt("noOfCamerasLivePortal")) > 0) ? "T" : "F"));
			data.put("advertising", json.get("advertising"));
			data.put("business_intelligence", json.get("business_intelligence"));
			mappeddata.put("Services", data);
			}
			if(Request_type!=null && Request_type.equals("Ads")) {
			data = new HashMap<Object, Object>();
			HashMap<Object, Object> data2 = new HashMap<Object, Object>();
			data2.put("screen0", "Connected");
			for (int i = 0; i <= json.getInt("noOfScreensAtSite"); i++) {
				data2.put("screen" + i, "Connected");
			}
			data.put("screens", data2);
			mappeddata.put("Ads", data);
			}
			if(Request_type!=null && Request_type.equals("Analytics")) {
			mappeddata.put("Analytics", this.getBusinessAnalystics((Integer.parseInt(accountId)),null));
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return mappeddata;

	}

	public Object getAnalyticsdata(String accountId) {
		
		ArrayList<Object> data = new ArrayList<Object>();
		
		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + accountId;

		String client_id = util.readUrlData(sitesUrl);


		HttpPost post = new HttpPost("https://ivisbi.com/v2/api/analytics");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));
		urlParameters.add(new BasicNameValuePair("parameter", "13"));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(input);
			data.add(json.toMap());
			
			return data;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public Object getbiAnalyticsReport(int siteId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub

		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);
		
		HttpPost post = new HttpPost("http://ivisbi.com/v2/api/analyticsReport");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));
		urlParameters.add(new BasicNameValuePair("fromDate", fromDate.toString()));
		urlParameters.add(new BasicNameValuePair("toDate", toDate.toString()));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			System.out.println(input);
			JSONObject json = new JSONObject(input);
			//System.out.println(json);
			
			
			return input;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}
	
	public Object getbiAnalyticsReport2(int siteId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub

		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);
		
		HttpPost post = new HttpPost("http://ivisbi.com/v2/api/analyticsReport");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));
		urlParameters.add(new BasicNameValuePair("fromDate", fromDate.toString()));
		urlParameters.add(new BasicNameValuePair("toDate", toDate.toString()));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(input);
			System.out.println(json.keySet().toArray()[0]);
			ArrayList<Object> data = new ArrayList<Object>();
			for(int i=0;i< json.keySet().toArray().length;i++)
			{
				System.out.println(json.keySet().toArray()[i]);
				HashMap<Object,Object> hashData = new HashMap<Object,Object>();
				
				
				
				
				
				JSONArray dataObject = json.getJSONArray(json.keySet().toArray()[i].toString());
				 
				hashData.put("data", dataObject.toList());
				hashData.put("name", json.keySet().toArray()[i]);
				data.add(hashData);
			}
			
			return data;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	public Object getMonitoringDetails(String deviceId) {

//		String cameraUrl = "http://usmgmt.iviscloud.net:777/ivigil-crm/Camera?action=get&deviceId="+deviceId+"&version=1.0";
//		String camdata = util.readUrlData(cameraUrl);
//		JSONArray camdatajson = new JSONArray(camdata);
//		
//		String kernalUrl1 = "http://kernel.iviscloud.net:8182/ivis-kernel-server/CameraMonitoringHours?action=get&deviceId="+deviceId;
//		String kernal1data = util.readUrlData(kernalUrl1);
//		JSONArray kernal1datajson = new JSONArray(kernal1data);
//		
//		
//		
//		System.out.println(camdatajson.getJSONObject(0).get("cameraId"));
//		System.out.println(camdatajson.getJSONObject(0).get("name"));
//		System.out.println(camdatajson.getJSONObject(0).get("index"));
//		HashMap<Object,Object> camIndex = new HashMap<Object,Object>();
//		for(int i=0;i<camdatajson.length();i++) {
//		
//		HashMap<Object,Object> camDetails = new HashMap<Object,Object>();
//		camDetails.put("name", camdatajson.getJSONObject(i).get("name"));
//		camDetails.put("index", camdatajson.getJSONObject(i).get("index"));
//		
//		camIndex.put(camdatajson.getJSONObject(i).get("cameraId"), camDetails);
//		}
//		
//		
//		String kernalUrl2 = "http://kernel.iviscloud.net:8182/ivis-kernel-server/MonitoringHours?action=getbyIndex&index=5&deviceId="+deviceId;
//
//		
//		return camIndex;
		return null;
		
		
		
	}
	public Map<String, String> userLogin(UserLogin user) {
		String url="http://smstaging.iviscloud.net:8080/auth/realms/"+user.getRealm()+"/protocol/openid-connect/token";
		HttpPost post = new HttpPost(url);
		Map<String,String> access_token=new HashMap<String,String>();
		
		// add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("grant_type", user.getGrant_type()));
        urlParameters.add(new BasicNameValuePair("client_id", user.getClient_id()));
        urlParameters.add(new BasicNameValuePair("client_secret", user.getClient_secret()));
        urlParameters.add(new BasicNameValuePair("username", user.getUsername()));
        urlParameters.add(new BasicNameValuePair("password", user.getPassword()));
        
        
        try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
	             CloseableHttpResponse response = httpClient.execute(post)) {

	        	String input = EntityUtils.toString(response.getEntity());
	        	JSONObject json = new JSONObject(input);
	            //System.out.println(json.get("access_token"));
	            access_token.put("access_token", json.getString("access_token"));
	            
        }
        catch (Exception e) {
        	System.err.println(e);
		} 
		
		return access_token;
	}

	public List<BusinessCamesStreamEntity> getCamerasStreamList(String uId, String accountId) {
		String camerasUrl2 = "http://smstaging.iviscloud.net:8090/cpus/cameras/CameraStreamList_1_0?uId=";
		camerasUrl2 = camerasUrl2 + uId + "&accountId=" + accountId;
		String response = util.readUrlData(camerasUrl2);

		Type collectionType2 = new TypeToken<Collection<CamerasStreamEntity>>() {
		}.getType();
		List<CamerasStreamEntity> camesData = (List<CamerasStreamEntity>) new Gson().fromJson(response, collectionType2);
		List<BusinessCamesStreamEntity> camesList = new ArrayList<BusinessCamesStreamEntity>();
		for (CamerasStreamEntity c : camesData) {
			BusinessCamesStreamEntity cames = new BusinessCamesStreamEntity();
			cames.setStreamingType(c.getStreamingType());
			cames.setHlsEnabled(c.getHlsEnabled());
			cames.setCameraIndex(c.getId());
			cames.setIndexNo(c.getIndexNo());
			cames.setDeviceInternalId(c.getPotentialId());
			cames.setCameraId(c.getCameraId());
			

			
			
			cames.setDeviceExternalId(camesData.get(0).getCameraId().substring(0,c.getCameraId().lastIndexOf("C")));
			cames.setCameraName(c.getName());
			cames.setServerHost(c.getServerHost());
			cames.setServerPort(c.getServerPort());
			cames.setServerCameraId(c.getServerCameraId());
			cames.setRtspUrl(c.getRtspUrl());
			cames.setUserName(c.getUserName());
			cames.setPassword(c.getPassword());
			cames.setWidth(c.getWidth());
			cames.setHeight(c.getHeight());
			cames.setFps(c.getFps());
			cames.setOnvifUrl(c.getOnvifUrl());
			cames.setPtz(c.getPtz());
			cames.setActive(c.getActive());
			cames.sethTTPTunnel(c.gethTTPTunnel());
			cames.setPushType(c.getPushType());
			cames.setServerHttpPort(c.getServerHttpPort());
			cames.setEventDuration(c.getEventDuration());
			cames.setEventFrames(c.getEventFrames());
			cames.setEventPushUrl(c.getEventPushUrl());
			cames.setMotionDetectionType(c.getMotionDetectionType());
			cames.setMotionDetectionLevel(c.getMotionDetectionLevel());
			cames.setGprsEventDuration(c.getGprsEventDuration());
			cames.setArchiveType(c.getArchiveType());
			cames.setArchiveLocation(c.getArchiveLocation());
			cames.setArchiveDays(c.getArchiveDays());
			cames.setTranscode(c.getTranscode());
			cames.setSpf(c.getSpf());
			cames.setBitrate(c.getBitrate());
			cames.setSnapshotDuration(c.getSnapshotDuration());
			cames.setEventOnSms(c.getEventOnSms());
			cames.setMotiondetectionId(c.getMotiondetectionId());
			cames.setEventPushType(c.getEventPushType());
			cames.setMotionAnalytics(c.getMotionAnalytics());
			cames.setCameraType(c.getCameraType());
			cames.setGtEnabled(c.getGtEnabled());
			cames.setHbCheck(c.getHbCheck());
			cames.setDisplayName(c.getDisplayName());
			cames.setEventPushRetryCount(c.getEventPushRetryCount());
			cames.setCategory(c.getCategory());
			cames.setStreamingUrl(c.getStreamingUrl());
			String c1StatusUrl = "http://usvs1.iviscloud.net:7888/Command?action=";
			c1StatusUrl = c1StatusUrl + "status" + "&cameraId=" + c.getCameraId();
			
			boolean cam_status = false;
			String resp = util.readUrlData(c1StatusUrl);
			JSONArray camsjson = new JSONArray(resp);
			cames.setCameraStatus(camsjson.getJSONObject(0).get("status").toString());			
			camesList.add(cames);

		}

		return camesList;

	}

	public Object biAnalyticRepThandTd_1_0(int siteId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}
}

