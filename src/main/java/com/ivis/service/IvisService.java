package com.ivis.service;

import java.io.*;
import okhttp3.*;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ivis.ApplicationModels.Analysis;
import com.ivis.ApplicationModels.CamStreamListModel;
import com.ivis.ApplicationModels.CamStreamListModelWithActiveCams;
import com.ivis.ApplicationModels.CategoriesModel;
import com.ivis.ApplicationModels.CategoriesSubcategoriesOutputModel;
import com.ivis.ApplicationModels.MonitoringDetailsInput;
import com.ivis.ApplicationModels.MonitoringDetailsOutput;
import com.ivis.ApplicationModels.MonitoringHoursListModel;
import com.ivis.ApplicationModels.Services;
import com.ivis.ApplicationModels.SiteList;
import com.ivis.ApplicationModels.SnapshortUrlsForAccountModel;
import com.ivis.ApplicationModels.Subcategories;
import com.ivis.ApplicationModels.SnapshortUrlsForAccountModel.Camera;
import com.ivis.ApplicationModels.UserLogin;
import com.ivis.ApplicationModels.UserMgmtUserModel;
import com.ivis.ApplicationModels.account;
import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.Businessentity.BusinessCamesStreamEntity;
import com.ivis.Businessentity.BusinessEntity;
import com.ivis.Businessentity.CamerasStreamEntity;
import com.ivis.Businessentity.SitesEntity;
import com.ivis.Businessentity.UserEntity;
import com.ivis.util.BiUtils;
import com.ivis.util.HashMapUtil;
import com.ivis.util.KeycloakUtils;
import com.ivis.util.MngtServerUtils;
import com.ivis.util.ReadJson;
import com.ivis.util.UserMgmtUtils;
import com.ivis.util.util;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

@Service
public class IvisService {

	public static String cpusApi = ServerConfig.cpusapi;
	public static String keycloakApi = ServerConfig.keycloakapi;
	public static String IvisBiApi = ServerConfig.ivisBiApi;

	public static String userMgmtApi = ServerConfig.userMgmt;

	public UserEntity getImMatrixAvailability(String uId) {

		UserEntity u = new UserEntity();
		String sitesUrl = cpusApi + "/sites/GetSitesListForUser_1_0?userName=";
		sitesUrl = sitesUrl + uId;

		String response = util.readUrlData(sitesUrl);

		Gson gson = new Gson();

		String camResponse = util.readUrlData(sitesUrl);
		List<BusinessEntity> devList = new ArrayList<BusinessEntity>();

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
		String camerasUrl2 = cpusApi + "/cameras/CameraStreamList_1_0?uId=";
		camerasUrl2 = camerasUrl2 + uId + "&accountId=" + accountId;
		String response2 = util.readUrlData(camerasUrl2);

		Type collectionType2 = new TypeToken<Collection<CamerasStreamEntity>>() {
		}.getType();
		List<CamerasStreamEntity> camesData2 = (List<CamerasStreamEntity>) new Gson().fromJson(response2,
				collectionType2);
		String camerasUrl = cpusApi + "/cameras/CameraListforSiteId_1_0?uId=";
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
			for (CamerasStreamEntity c2 : camesData2) {
				if (c2.getCameraId().equals(c.getCameraId())) {
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

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + accountId;

		String client_id = util.readUrlData(sitesUrl);

		HttpPost post = new HttpPost("https://ivisbi.com/v2/api/getServices");
		HttpPost post2 = new HttpPost("https://ivisbi.com/v2/api/analytics");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));

		if (!(date == null)) {
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

	public Object getBusinessAnalystics2(int accountId, Date date) {
		List<BIAnalyticsEntity> bIAnalytics = new ArrayList<BIAnalyticsEntity>();

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + accountId;

		String client_id = util.readUrlData(sitesUrl);

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, "{\"id\": " + client_id + "}");
			Request request = new Request.Builder().url(IvisBiApi + "/getServices").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String servicesListString = response.body().string();

			JSONArray servicesListJson = new JSONArray(servicesListString);

			if (servicesListJson.toList().size() == 0)
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Failed processing request");
					}
				};

			for (Object i : servicesListJson) {

				JSONObject service = new JSONObject(i.toString());
				int serviceId = service.getInt("id");

				OkHttpClient client2 = new OkHttpClient().newBuilder().build();
				MediaType mediaType2 = MediaType.parse("application/json");
				JSONObject requestBody = new JSONObject();
				requestBody.put("id", client_id);
				requestBody.put("fieldid", serviceId);

				if (date != null) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
					String strDate = dateFormat.format(date);
					requestBody.put("startdate", strDate);
				}
				RequestBody body2 = RequestBody.create(mediaType2, requestBody.toString());
				Request request2 = new Request.Builder().url(IvisBiApi + "/getResearch").method("POST", body2)
						.addHeader("Content-Type", "application/json").build();

				Response response2 = client2.newCall(request2).execute();

				String researchString = response2.body().string();
				JSONObject researchJsonMap = new JSONObject(researchString);
				BIAnalyticsEntity _BIAnalyticsEntity = new BIAnalyticsEntity();
				_BIAnalyticsEntity.setService((String) researchJsonMap.keySet().toArray()[0]);
				System.out.println(serviceId);
				_BIAnalyticsEntity.setServiceId(serviceId);

				String jsonArrayString = researchJsonMap.get(_BIAnalyticsEntity.getService()).toString();
				if (jsonArrayString.charAt(0) == '[') {
					JSONArray analysisjsonlist = new JSONArray(jsonArrayString);
					List<Analysis> analysislist = new ArrayList<Analysis>();
					for (Object j : analysisjsonlist) {
						Analysis _Analysis = new Gson().fromJson(j.toString(), Analysis.class);
						analysislist.add(_Analysis);
					}
					_BIAnalyticsEntity.setAnalytics(analysislist);
					bIAnalytics.add(_BIAnalyticsEntity);
				}
			}

			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "Valid Data");
					put("AnalyticsList", bIAnalytics);
				}
			};

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");
				}
			};
		}

	}

	// TODO WE need to change this
	public Object mapServices2(String url2, String Request_type, String accountId) {

		try {

			URL url = new URL(url2);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestProperty("Accept", "application/json");

			InputStream resp = http.getInputStream();

			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			if (sb.toString().equals(null)) {
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Sorry no data");
					}
				};
			}

			JSONObject json = new JSONObject(sb.toString());
			http.disconnect();

			HashMap<Object, Object> mappeddata = new HashMap<Object, Object>();
			HashMap<Object, Object> data = new HashMap<Object, Object>();
			if (json.equals(null)) {
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Sorry no data");
					}
				};
			} else {
				mappeddata.put("Status", "Success");
				mappeddata.put("Message", "Success");

			}

			if (!json.get("bgImagePath").equals(null))
				mappeddata.put("background", json.get("bgImagePath"));
			else
				mappeddata.put("background", null);

			if (!json.get("nonWorkingDays").equals(null))
				mappeddata.put("nonWorkingDays", json.get("nonWorkingDays"));
			else
				mappeddata.put("nonWorkingDays", null);
			mappeddata.put("SiteId", json.get("accountId"));

			mappeddata.put("SiteName", json.get("siteName"));

			if (Request_type.equals("Services"))

			{
				data.put("alarms", json.get("monitoring"));
				data.put("safety_escort", json.get("safety_escort"));
				data.put("LiveView", (((json.getInt("noOfCamerasLivePortal")) > 0) ? "T" : "F"));
				data.put("advertising", json.get("advertising"));
				data.put("business_intelligence", json.get("business_intelligence"));
				mappeddata.put("Services", data);
			}
			if (Request_type != null && Request_type.equals("Ads")) {
				data = new HashMap<Object, Object>();
				HashMap<Object, Object> data2 = new HashMap<Object, Object>();
				data2.put("screen0", "Connected");
				for (int i = 0; i <= json.getInt("noOfScreensAtSite"); i++) {
					data2.put("screen" + i, "Connected");
				}
				data.put("screens", data2);
				mappeddata.put("Ads", data);

			}
			if (Request_type != null && Request_type.equals("Analytics")) {
				mappeddata.put("Analytics", this.getBusinessAnalystics((Integer.parseInt(accountId)), null));
			}

			return mappeddata;

		} catch (IOException | JSONException e) {
			System.out.println(e);
			e.printStackTrace();
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
				}
			};
		}

	}

	// TODO WE need to change this
	public Object getAnalyticsdata(String accountId) {

		ArrayList<Object> data = new ArrayList<Object>();

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
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

	public Object getAnalyticTrends(int siteId, Date date, int analyticTypeId) {
		// TODO Auto-generated method stub

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);

		String url = IvisBiApi + "/getTrends";

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");

			JSONObject requestBody = new JSONObject();
			requestBody.put("id", client_id.replace("\n", ""));

			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(date);
				requestBody.put("startdate", strDate);
			}

			requestBody.put("fieldid", String.valueOf(analyticTypeId));

			RequestBody body = RequestBody.create(mediaType, requestBody.toString());

			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();

			Response response = client.newCall(request).execute();
			String reportsString = response.body().string();

//			System.out.println(reportsString);

//			ArrayList<Object> reportsList = new Gson().fromJson(reportsString, null);
			if (new JSONArray(reportsString).toList().size() > 0)
				return new HashMap<Object, Object>() {
					{
						put("Status", "Success");
						put("Message", "List generated successfully");
						put("AnalyticsTrends", new JSONArray(reportsString).toList());
					}
				};
			else
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Sorry, no data found.Please try again later...");
					}
				};

		} catch (Exception e) {
			System.out.println(e);
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Sorry, cannot process your request.Please try again later...");
				}
			};
		}
	}

	public Object getAnalyticTrends2(int siteId, Date date, int analyticTypeId) {

		return new BiUtils().getTrendsMobile(siteId, date, analyticTypeId);
	}

	public Object getbiAnalyticsReport2(int siteId, Date fromDate, Date toDate) {

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);

		String url = IvisBiApi + "/getReports";
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");

			JSONObject requestBody = new JSONObject();
			requestBody.put("id", client_id);

			if (fromDate != null) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(fromDate);
				requestBody.put("startdate", strDate);
			}
			if (toDate != null) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(toDate);
				requestBody.put("enddate", strDate);
			}

			RequestBody body = RequestBody.create(mediaType, requestBody.toString());

			Request request = new Request.Builder().url(url).method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String reportsString = response.body().string();
			if (new JSONArray(reportsString).toList().size() > 0)
				return new HashMap<String, Object>() {
					{
						put("Status", "Success");
						put("Message", "List generated successfully");
						put("AnalyticsReportList", new JSONArray(reportsString).toList());
					}
				};
			else
				return new HashMap<String, String>() {
					{
						put("Status", "Failed");
						put("Message", "Sorry, no data found.Please try again later...");
					}
				};

		} catch (Exception e) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Sorry, cannot process your request.Please try again later...");
				}
			};
		}

	}

	public Object getbiAnalyticsReport3(int siteId, String fromDate, String toDate) {
		// TODO Auto-generated method stub

		String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);

		try {
			String urlForAddRequest = IvisBiApi + "/getReportsMobile";

			URL url = new URL(urlForAddRequest);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/json");

			JSONObject datajson = new JSONObject();

			datajson.put("id", client_id);
			// if(!fromDate.equals(null)) {
			datajson.put("startdate", fromDate);
			datajson.put("enddate", toDate);
			// }

			byte[] out = datajson.toString().getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			InputStream resp = http.getInputStream();

			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			// System.out.println(sb);
			JSONArray json = new JSONArray(sb.toString());

			http.disconnect();
//			return json.toList();
			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "List generated successfully");
					put("AnalyticsReportList", json.toList());

				}
			};

		} catch (Exception e) {
			System.err.println(e);
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");

				}
			};

		}
	}

	public Object getMonitoringDetails(String accountId) {

		String data = util.readUrlData(cpusApi + "/Monitoring/monitoringHours?accountId=" + accountId);

		MonitoringDetailsInput input = new Gson().fromJson(data, MonitoringDetailsInput.class);

		MonitoringDetailsOutput output = new MonitoringDetailsOutput();

		output.setSiteId(input.getPotentailId());

		if (input.getMonitoringHours().size() > 0)
			output.setMonitoringStatus("Enabled");
		else
			output.setMonitoringStatus("Disabled");

		ArrayList<MonitoringHoursListModel> lis = new ArrayList<MonitoringHoursListModel>();

		Set<MonitoringHoursListModel> s = new HashSet<MonitoringHoursListModel>();

		s.addAll(input.getMonitoringHours());

		System.out.println(s);
		lis.addAll(s);

		output.setMonitoringHours(lis);
		output.setStatus("Success");
		output.setMessage("Valid details");
		return output;

	}

	public Map<String, String> userLogin(UserLogin user) {
		String url = keycloakApi + "/realms/" + user.getRealm() + "/protocol/openid-connect/token";
		HttpPost post = new HttpPost(url);
		Map<String, String> access_token = new HashMap<String, String>();

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
			// System.out.println(json.get("access_token"));
			access_token.put("access_token", json.getString("access_token"));
			httpClient.close();

		} catch (Exception e) {
			System.err.println(e);
		}

		return access_token;
	}

	public ArrayList<CamStreamListModelWithActiveCams> getCamerasStreamList2(String userName, String accountId) {

		// Read cpus api camstreamlist
		// get video servers list and add cameras list to the videoserverCameraList
		// Hashmap
		// ArrayList<CamStreamListModelWithActiveCams> camsData2 = new ArrayList<>();

		long starttime = System.currentTimeMillis();

		JSONArray json = new JSONArray();

		Gson gson = new Gson();
		try {
			URL url = null;
			if (accountId != null)
				url = new URL(
						cpusApi + "/cameras/CameraStreamList_1_0?userName=" + userName + "&accountId=" + accountId);
			else
				url = new URL(cpusApi + "/cameras/CameraStreamList_1_0?userName=" + userName);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestProperty("Accept", "application/json");
			InputStream resp = http.getInputStream();

			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONArray(sb.toString());
			http.disconnect();
			ArrayList<CamStreamListModelWithActiveCams> camsData2 = new ArrayList<>();
			HashMap<String, List<String>> cameraVideoServerMap = new HashMap<String, List<String>>();

			for (Object i : json) {
				List<String> cameraList = new ArrayList<String>();

				CamStreamListModelWithActiveCams camstream = gson.fromJson(i.toString(),
						CamStreamListModelWithActiveCams.class);
				String videoServer = "http://" + camstream.getHost() + ":" + camstream.getHttpPort()
						+ "/Command?action=status";
				if (cameraVideoServerMap.containsKey(videoServer)) {
					cameraList = cameraVideoServerMap.get(videoServer);
					if (!cameraList.contains(camstream.getCameraId()))
						cameraList.add(camstream.getCameraId());
				} else {
					if (!cameraList.contains(camstream.getCameraId()))
						cameraList.add(camstream.getCameraId());
				}
				cameraVideoServerMap.put(videoServer, cameraList);
			}
			HashMap<String, String> cameraStatusMap = new HashMap<String, String>();
			System.out.println(cameraVideoServerMap.keySet());
			for (String i : cameraVideoServerMap.keySet()) {

				JSONArray listOfcameraStatus = new JSONArray();
				try {
					listOfcameraStatus = new ReadJson().readJsonArrayFromUrl(i);

				} catch (Exception exception) {

				}
				for (Object j : listOfcameraStatus) {

					JSONObject cam = new JSONObject(j.toString());
					cameraStatusMap.put(cam.getString("cameraId"), cam.getString("status"));
				}
				long endtime = System.currentTimeMillis();
				System.out.println(i);
				System.out.println("Time spent = " + (endtime - starttime));

			}
			
			for (Object i : json) {
				String dataString = gson.toJson(i);
				JSONObject datajson = new JSONObject(dataString);
				CamStreamListModelWithActiveCams camstream = gson.fromJson(i.toString(),
						CamStreamListModelWithActiveCams.class);
				if (cameraStatusMap.containsKey(camstream.getCameraId()))
					camstream.setCameraStatus(cameraStatusMap.get(camstream.getCameraId()));
				else
					camstream.setCameraStatus("Disconnected");

				camstream.setDeviceInternalId(datajson.getJSONObject("map").getInt("potentialId"));
				camstream.setCameraname(datajson.getJSONObject("map").getString("name"));
				camstream.setSnapShotUrl("http://" + camstream.getHost() + ":" + camstream.getHttpPort()
						+ "/SnapShot?cameraId=" + camstream.getCameraId());
				camsData2.add(camstream);
			}
			return camsData2;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<BusinessCamesStreamEntity> getCamerasStreamList(String uId, String accountId) {
		String camerasUrl2 = cpusApi + "/cameras/CameraStreamList_1_0?uId=";
		camerasUrl2 = camerasUrl2 + uId + "&accountId=" + accountId;
		String response = util.readUrlData(camerasUrl2);

		Type collectionType2 = new TypeToken<Collection<CamerasStreamEntity>>() {
		}.getType();
		List<CamerasStreamEntity> camesData = (List<CamerasStreamEntity>) new Gson().fromJson(response,
				collectionType2);
		List<BusinessCamesStreamEntity> camesList = new ArrayList<BusinessCamesStreamEntity>();
		for (CamerasStreamEntity c : camesData) {
			BusinessCamesStreamEntity cames = new BusinessCamesStreamEntity();
			cames.setStreamingType(c.getStreamingType());
			cames.setHlsEnabled(c.getHlsEnabled());
			cames.setCameraIndex(c.getId());
			cames.setIndexNo(c.getIndexNo());
			cames.setDeviceInternalId(c.getPotentialId());
			cames.setCameraId(c.getCameraId());

			cames.setDeviceExternalId(camesData.get(0).getCameraId().substring(0, c.getCameraId().lastIndexOf("C")));
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

	public Object getSiteListByUserName(String username) {
		JSONObject outobj = new JSONObject();
		String siteUrl = cpusApi + "/sites/GetSitesListForUser_1_0?userName=" + username;

		String response = util.readUrlData(siteUrl);

		if (response == null) {
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Data not available");

				}
			};
		}

		Gson gson = new Gson();
		SiteList sitesListData = gson.fromJson(response, SiteList.class);
		if (sitesListData.getSiteList().size() > 0) {
			sitesListData.setStatus("Success");
			sitesListData.setMessage("Site data is valid");
		} else {
			sitesListData.setStatus("Failed");
			sitesListData.setMessage("Data not available");
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Data not available");

				}
			};
		}
		String jsondata = gson.toJson(sitesListData);
		JSONObject jsonobj = new JSONObject(jsondata);
		JSONArray jsonarr = new JSONArray(jsonobj.getJSONArray("siteList"));
		outobj.put("Status", sitesListData.getStatus());
		outobj.put("Message", sitesListData.getMessage());
		JSONArray outarr = new JSONArray();
		for (int i = 0; i < jsonarr.length(); i++) {
			JSONObject outarrobj = new JSONObject();
			JSONObject jsonarrobj = new JSONObject(jsonarr.getJSONObject(i).toString());
			outarrobj.put("siteid", jsonarrobj.get("accountId"));
			outarrobj.put("sitename", jsonarrobj.get("accountName"));
			outarrobj.put("project", jsonarrobj.get("facility"));
			outarrobj.put("atmid", jsonarrobj.get("customerSiteId"));
			outarrobj.put("state", jsonarrobj.get("state"));
			outarrobj.put("City", jsonarrobj.get("city"));
			outarrobj.put("zone", jsonarrobj.get("zone"));
			outarrobj.put("business_vertical", jsonarrobj.get("industry"));
			outarrobj.put("phase", jsonarrobj.get("batchNo"));
			outarrobj.put("deviceInternalId", jsonarrobj.get("potentialId"));
			outarrobj.put("deviceExternalId", jsonarrobj.get("unitId"));
			outarrobj.put("LocationType", jsonarrobj.get("siteType"));
			outarrobj.put("latitude", jsonarrobj.get("latitude"));
			outarrobj.put("longitude", jsonarrobj.get("longitude"));
			if (jsonarrobj.keySet().contains("accountShortName")) {
				outarrobj.put("siteShortName", jsonarrobj.get("accountShortName"));
			} else
				outarrobj.put("siteShortName", "");
			outarr.put(outarrobj);
		}
		outobj.put("siteList", outarr);
		return outobj.toMap();
	}

	public Object resetPasswordSendEmailByUserName(String userName) {
		try {
			URL url = new URL(keycloakApi + "/resetPasswordUsingEmail");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("PUT");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{    \"userName\" : \"" + userName + "\"}";
			System.out.println(data);
			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

			InputStream resp = http.getInputStream();

			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			System.out.println(sb);
			JSONObject json = new JSONObject(sb.toString());

			http.disconnect();

			return json.toMap();
		} catch (Exception e) {
			System.err.println(e);
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Invalid username");

				}
			};
		}

	}

	public Object getServiceRequests(HashMap<String, String> inputData) {

		try {
			JSONObject jsonOutput = new JSONObject();
			String link = "";
			if (inputData.containsKey("serviceId"))
				link = cpusApi + "/serviceRequest/getServiceReq_1_0?" + "&serviceId=" + inputData.get("serviceId")
						+ "&userName=" + inputData.get("userName");
			else
				link = cpusApi + "/serviceRequest/getServiceReq_1_0?&userName=" + inputData.get("userName");

			if (inputData.containsKey("siteId"))
				link += "&siteId=" + inputData.get("siteId");

			String jsonstring = util.readUrlData(link);
			JSONObject json = new JSONObject(jsonstring);

			JSONArray jsonList = json.getJSONArray("helpDeskList");
			List<Object> jsonList2 = new ArrayList<Object>();
			String givenFormat = "yyyy-MM-dd'T'HH:mm:ss";
			String reqdFormat = "yyyy-MM-dd HH:mm:ss";
			DateTimeFormatter givenFormatF = DateTimeFormatter.ofPattern(givenFormat);
			DateTimeFormatter reqdFormatF = DateTimeFormatter.ofPattern(reqdFormat);

			for (Object i : jsonList) {
				JSONObject jsonObject = new JSONObject(i.toString());

				if (jsonObject.get("createdBy").equals(inputData.get("userName"))) {

					if (!jsonObject.get("createdTime").equals(null)) {
//						String str = jsonObject.getString("createdTime");
//						LocalDateTime dateTime = LocalDateTime.parse(str, givenFormatF);
//					    String formattedDate = dateTime.format(reqdFormatF);
						jsonObject.put("createdTime", LocalDateTime
								.parse(jsonObject.getString("createdTime"), givenFormatF).format(reqdFormatF));
					}

					if (!jsonObject.get("PrefTimeToCall").equals(null)) {
//						String str = jsonObject.getString("PrefTimeToCall");
//						LocalDateTime dateTime = LocalDateTime.parse(str, givenFormatF);
//					    String formattedDate = dateTime.format(reqdFormatF);
						jsonObject.put("PrefTimeToCall", LocalDateTime
								.parse(jsonObject.getString("PrefTimeToCall"), givenFormatF).format(reqdFormatF));
					}

					if (!jsonObject.get("editedTime").equals(null)) {

//						String str = jsonObject.getString("editedTime");
//						LocalDateTime dateTime = LocalDateTime.parse(str, givenFormatF);
//					    String formattedDate = dateTime.format(reqdFormatF);
						jsonObject.put("editedTime", LocalDateTime
								.parse(jsonObject.getString("editedTime"), givenFormatF).format(reqdFormatF));
					}
					jsonList2.add(jsonObject);
				}

			}
			jsonOutput.put("helpDeskList", jsonList2);
			if (jsonList2.isEmpty()) {
				jsonOutput.put("Status", "Failed");
				jsonOutput.put("Message", "Sorry, no data available...☺");
			} else {
				{
					jsonOutput.put("Status", "Success");
					jsonOutput.put("Message", "Request processed successfully");
				}
			}
			return jsonOutput.toMap();
		} catch (Exception e) {
			return new HashMap<String, String>() {
				{
					System.err.println(e);
					e.printStackTrace();
					put("Status", "Failed");
					put("Message", "Failed processing request");

				}
			};
		}
	}

	public Object addOrUpdateServiceRequest(Integer accountId, Integer serviceId, String serviceCategoryName,
			String serviceSubCategoryName, String userName, String calling_System_Detail, MultipartFile[] attachements,
			String description, String preferredTimeToCall, String priority, String remarks) {

		try {
			List<String> fileNames = new ArrayList<String>();
			List<String> files = new ArrayList<String>();
			if (!(attachements == null))
				for (MultipartFile i : attachements) {
					System.out.println(i.getOriginalFilename());
					fileNames.add(i.getOriginalFilename());
					files.add(i.getBytes().toString());
				}
			Map<String, Object> bodyMap = new HashMap<>();

			String url = cpusApi + "/serviceRequest/addServiceReq_1_0";
			String requestType = "POST";
			bodyMap.put("accountId", accountId);

			if (!(serviceId == null)) {
				bodyMap.put("serviceId", serviceId);
				url = cpusApi + "/serviceRequest/updateServiceReq_1_0";
				requestType = "PUT";
			}
			bodyMap.put("serviceCategoryName", serviceCategoryName);
			bodyMap.put("serviceSubCategoryName", serviceSubCategoryName);
			bodyMap.put("createdBy", userName);
			bodyMap.put("requestType", calling_System_Detail);
			if (fileNames.size() > 0) {
				bodyMap.put("imageName", fileNames.get(0));
				bodyMap.put("file", files.get(0));
				System.out.println(bodyMap.get(bodyMap));
			}
			bodyMap.put("description", description);
			if (!(preferredTimeToCall == null))
				bodyMap.put("preferredTimeToCall", preferredTimeToCall);
			if (!(priority == null))
				bodyMap.put("priority", priority);
			if (!(remarks == null))
				bodyMap.put("remarks", remarks);

			System.out.println(new Gson().toJson(bodyMap));
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));

			// RequestBody body = RequestBody.create(mediaType,
			// "{\r\n\"accountId\":1007,\r\n\"userName\" :
			// \"us1\",\r\n\"serviceName\":\"Forty One ten
			// IVISUSA1007\",\r\n\"serviceSubCategory\":\"Test\",\r\n\"calling_system\":\"Test\",\r\n\"imageName\":\"\",\r\n\"file\":\"\",\r\n\"requestType\":\"Test\",\r\n\"preferredTimeToCall\":\"02:00:00\",\r\n\"description\":\"TEST\",\r\n\"remarks\":\"Test\",\r\n\"priority\":\"Test\",\r\n\"project\":\"Test\"\r\n\r\n}");
			Request request = new Request.Builder().url(url).method(requestType, body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			System.out.println("Response from addservicerequest  :" + responseString);
			JSONObject hashMap = new JSONObject(responseString);

			return hashMap.toMap();
		} catch (Exception e) {
			System.err.print("***********	Error	*************");
			System.err.println("addservicerequest : " + e);
			e.printStackTrace();
			System.err.print("***********	End	*************");
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Request Cannot be processed");

				}
			};
		}

	}

	public Object getsnapshotUrlsForSitesList_1_0(ArrayList siteList) {

		try {
			ArrayList<SnapshortUrlsForAccountModel> siteDataList = new ArrayList<>();

			JSONObject output = new JSONObject();

			for (Object i : siteList) {
				String data = util.readUrlData(cpusApi + "/cameras/SnapshortUrlsForAccount_1_0?accountId=" + i);
				SnapshortUrlsForAccountModel siteListData = new Gson().fromJson(data,
						SnapshortUrlsForAccountModel.class);
				siteDataList.add(siteListData);
			}

			JSONArray siteListOut = new JSONArray();

			for (SnapshortUrlsForAccountModel i : siteDataList) {
				JSONObject site = new JSONObject();
				site.put("siteid", i.getAccountId());
				JSONArray camlist = new JSONArray();
				String camsStatusList = util.readUrlData("http://" + i.getCamList().get(0).getHost() + ":"
						+ i.getCamList().get(0).getHttpPort() + "/Command?action=status");
				JSONArray camsStatusListjson = new JSONArray(camsStatusList);
				HashMap<String, String> camStatusMap = new HashMap<String, String>();
				for (int j = 0; j < camsStatusListjson.toList().size(); j++) {
					camStatusMap.put(camsStatusListjson.getJSONObject(j).getString("cameraId"), "status");
				}

				for (SnapshortUrlsForAccountModel.Camera j : i.getCamList()) {
					JSONObject cam = new JSONObject();
					cam.put("displayName", j.getDisplayName());
					cam.put("snapShotUrl",
							"http://" + j.getHost() + ":" + j.getHttpPort() + "/SnapShot?cameraId=" + j.getCameraId());
					cam.put("cameraId", j.getCameraId());
					cam.put("displayOrder", j.getDisplayOrder());
					// String camStatus =
					// util.readUrlData("http://"+j.getHost()+":"+j.getHttpPort()+"/Command?action=status&cameraId="+j.getCameraId());

					// if(camStatus!=null) {
					// JSONArray camsjson = new JSONArray(camStatus);
					// cam.put("cameraStatus", camsjson.getJSONObject(0).get("status").toString());
					// }
					// else
					// cam.put("cameraStatus", "Disconnected");
					cam.put("cameraStatus", camStatusMap.get(j.getCameraId()));
					camlist.put(cam);

				}
				site.put("CameraList", camlist);
				siteListOut.put(site);
			}
			output.put("Status", "Success");
			output.put("Message", "Success");
			output.put("siteList", siteListOut);

			return output.toMap();
		} catch (Exception e) {
			System.err.println(e);
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");

				}
			};
		}
	}

	public Object deleteServiceRequest(String userName, Integer accountId, Integer serviceId, String remarks) {

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");

			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("accountId", accountId);
			bodyMap.put("serviceId", serviceId);
			bodyMap.put("userName", userName);
			bodyMap.put("remarks", remarks);

			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));

			// RequestBody body = RequestBody.create(mediaType, "{\r\n \"accountId\" :
			// 1007,\r\n \"serviceId\" : 26,\r\n \"userName\" : \"us1\",\r\n \"remarks\" :
			// \"Calling\"\r\n}");
			Request request = new Request.Builder().url(cpusApi + "/serviceRequest/deleteServiceReq_1_0")
					.method("DELETE", body).addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from addservicerequest  :" + responseString);
			JSONObject hashMap = new JSONObject(responseString);

			return hashMap.toMap();

		} catch (Exception e) {
			System.err.print("***********	Error	*************");
			System.err.println("deleteServiceRequest : " + e);
			e.printStackTrace();
			System.err.print("***********	End	*************");
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");
				}
			};
		}
	}

	public Object getCategoryList(HashMap<String, String> inputData) {
		// TODO Auto-generated method stub

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder().url(cpusApi + "/serviceHelpDesk/getServiceCategoryDetails_1_0")
					.method("GET", null).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			System.out.println(responseString);

			JSONObject jsonObj = new JSONObject(responseString);

//			JSONArray jsonList = new JSONArray(responseString);
			jsonObj.put("Status", "Success");
			jsonObj.put("Message", "Category List is valid");
			jsonObj.put("CategoryList", jsonObj.get("categoryList"));
			jsonObj.remove("categoryList");
			return jsonObj.toMap();
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Request cannot be processed.Try again later...");
				}
			};
		}

	}

	public Object createServiceCat_1_0(HashMap<String, String> inputData) {

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("catName", inputData.get("catName"));
			bodyMap.put("description", inputData.get("description"));
			bodyMap.put("catIconPath", inputData.get("catIconPath"));

			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			// RequestBody body = RequestBody.create(mediaType, "{\r\n \"catName\":\"Safety
			// Escort\",\r\n \"description\":\"Safety Escort\",\r\n
			// \"catIconPath\":\"http://usmgmt.iviscloud.net:444/ivis-us-allsiteimages/AnalyticsIcon/customer.png\"\r\n}");
			Request request = new Request.Builder().url(cpusApi + "/serviceHelpDesk/addServiceCategory_1_0")
					.method("POST", body).addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();

			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "Request processed");
					putAll(new JSONObject(responseString).toMap());
				}
			};

		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Request cannot be processed.Try again later...");
				}
			};
		}

	}

	public Object createServiceSubcat(HashMap<String, String> inputData) {

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			Map<String, Object> bodyMap = new HashMap<>();
			bodyMap.put("serviceSubcatName", inputData.get("serviceSubcatName"));
			bodyMap.put("description", inputData.get("description"));
			bodyMap.put("serviceSubcatIconPath", inputData.get("serviceSubcatIconPath"));
			bodyMap.put("serviceCatId", inputData.get("serviceCatId"));

			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			// RequestBody body = RequestBody.create(mediaType, "{\r\n \"catName\":\"Safety
			// Escort\",\r\n \"description\":\"Safety Escort\",\r\n
			// \"catIconPath\":\"http://usmgmt.iviscloud.net:444/ivis-us-allsiteimages/AnalyticsIcon/customer.png\"\r\n}");
			Request request = new Request.Builder().url(cpusApi + "/serviceHelpDesk/addServiceSubCategory_1_0")
					.method("POST", body).addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();

			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "Request processed");
					putAll(new JSONObject(responseString).toMap());
				}
			};

		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Request cannot be processed.Try again later...");
				}
			};
		}

	}

	/**
	 * @author Deepika
	 * @param input
	 * @return
	 */
	public Object addUser(HashMap<String, Object> input) {

		if (!input.get("username").toString().toLowerCase().equals(input.get("username").toString()))
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Username must be in lowercase");
				}
			};

		HashMap bodymap = new HashMap<>();

		bodymap.put("UserName", input.get("username"));
		bodymap.put("Password", input.get("password"));
		bodymap.put("FirstName", input.get("firstname"));
		bodymap.put("LastName", input.get("lastname"));
		bodymap.put("Role-List", input.get("roleList"));
		bodymap.put("Email", input.get("email"));
		bodymap.put("Gender", input.get("gender"));
		bodymap.put("Realm", input.get("realm"));
		bodymap.put("ContactNumber-1", input.get("contactNumber1"));
		bodymap.put("ContactNumber-2", input.get("contactNumber2"));
		bodymap.put("Active", "T");
		bodymap.put("Country", input.get("country"));
		bodymap.put("Address_line1", input.get("addressLine1"));
		bodymap.put("Address_Line2", input.get("addressLine2"));
		bodymap.put("District", input.get("district"));
		bodymap.put("State", input.get("state"));
		bodymap.put("City", input.get("city"));
		bodymap.put("PIN", input.get("pin"));
		bodymap.put("employee", input.get("employee"));
		bodymap.put("employeeId", input.get("employeeId"));
		bodymap.put("access_token", input.get("accesstoken"));
		bodymap.put("calling_user_name", input.get("callingUsername"));
		bodymap.put("Calling_system_detail", input.get("callingSystemDetail"));
		bodymap.put("Safety_escort", input.get("safetyEscort"));

		return new KeycloakUtils().createUser(bodymap);

	}

	public Object getNotWorkingDays_1_0(int siteId, int year) {
		// TODO Auto-generated method stub
		return new BiUtils().getNotWorkingDays(siteId, year);
	}

	/**
	 * @author Deepika
	 * @param input
	 * @return
	 */

	public Object updateUser(HashMap<String, Object> input) {

		HashMap bodymap = new HashMap<>();

		bodymap.put("UserName", input.get("username"));
		bodymap.put("FirstName", input.get("firstname"));
		bodymap.put("LastName", input.get("lastname"));
		bodymap.put("Role-List", input.get("roleList"));
		bodymap.put("Email", input.get("email"));
		bodymap.put("Gender", input.get("gender"));
		bodymap.put("Realm", input.get("realm"));
		bodymap.put("ContactNumber-1", input.get("contactNumber1"));
		bodymap.put("ContactNumber-2", input.get("contactNumber2"));
		bodymap.put("Active", input.get("active"));
		bodymap.put("Country", input.get("country"));
		bodymap.put("Address_line1", input.get("addressLine1"));
		bodymap.put("Address_Line2", input.get("addressLine2"));
		bodymap.put("District", input.get("district"));
		bodymap.put("State", input.get("state"));
		bodymap.put("City", input.get("city"));
		bodymap.put("PIN", input.get("pin"));
		bodymap.put("employee", input.get("employee"));
		bodymap.put("employeeId", input.get("employeeId"));
		bodymap.put("access_token", input.get("accesstoken"));
		bodymap.put("calling_user_name", input.get("callingUsername"));
		bodymap.put("Calling_system_detail", input.get("callingSystemDetail"));
		bodymap.put("Safety_escort", input.get("safetyEscort"));

		return new KeycloakUtils().updateUser(bodymap);

	}

	/**
	 * @author Deepika
	 * @param input
	 * @return
	 */
	public Object getuserDetailsPortal(HashMap<String, String> input) {

		HashMap bodymap = new HashMap<>();

		bodymap.put("UserName", input.get("callingUsername"));
		return new KeycloakUtils().getUserMaster(bodymap);

	}

	/**
	 * @author Deepika
	 * @param input
	 * @return
	 */

	public Object getuserDetails(HashMap<String, String> input) {

		HashMap bodymap = new HashMap<>();
		bodymap.put("UserName", input.get("username"));
		bodymap.put("Email", input.get("email"));
		bodymap.put("calling_user_name", input.get("callingUsername"));
		bodymap.put("access_token", input.get("accesstoken"));

		Map x = new KeycloakUtils().getUser(bodymap);
		if (!(x.get("Status").equals("Success")))
			return x;

		x = (Map) x.get("UserDetails");
		LinkedHashMap m = new LinkedHashMap<>();

		m.put("username", x.get("UserName"));
		m.put("password", x.get("Password"));
		m.put("firstname", x.get("FirstName"));
		m.put("lastname", x.get("LastName"));
		m.put("roleList", x.get("Role-List"));
		m.put("email", x.get("Email"));
		m.put("active", x.get("Active"));
		m.put("gender", x.get("Gender"));
		m.put("contactNumber1", x.get("ContactNumber-1"));
		m.put("contactNumber2", x.get("ContactNumber-2"));
		m.put("country", x.get("Country"));
		m.put("addressLine1", x.get("Address_line1"));
		m.put("addressLine2", x.get("Address_Line2"));
		m.put("district", x.get("District"));
		m.put("state", x.get("State"));
		m.put("city", x.get("City"));
		m.put("pin", x.get("PIN"));
		m.put("employee", x.get("employee"));
		m.put("employeeId", x.get("employeeId"));
		m.put("safetyEscort", x.get("Safety_escort"));

		LinkedHashMap s = new LinkedHashMap<>();
		s.put("Status", "Success");
		s.put("Message", "User information retreived");
		s.put("userDetails", m);
		return s;

	}

	public Object deleteUser(UserMgmtUserModel input) {
		// TODO Auto-generated method stub

		return null;
	}

	/**
	 * 
	 * @param siteId
	 * @param requestType
	 * @return
	 */
	public Object getDeviceDetailsByRequestType(int siteId, String requestType) {

		switch (requestType) {
		case "Cameras":

			JSONObject response = new MngtServerUtils().getCentralUnitDetails_1_0(siteId);
			if (response.getString("Status").equals("Failed")) {
				return response.toMap();
			}
			response.remove("siteId");

			JSONObject centralBoxDetails = response.getJSONObject("CentralBoxDetails");

			JSONArray centralBoxDetailsList = new JSONArray();

			for (String i : centralBoxDetails.keySet()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("title", i);
				jsonObj.put("value", centralBoxDetails.get(i));
				centralBoxDetailsList.put(jsonObj);
			}
			response.put("CentralBoxDetails", centralBoxDetailsList);

			JSONArray cameraList = response.getJSONArray("Camera");
			JSONArray cameraListOfLists = new JSONArray();
			for (Object i : cameraList) {
				JSONObject cameraJsonObj = new JSONObject(i.toString());

				JSONArray cameraListTemp = new JSONArray();

				for (String j : cameraJsonObj.keySet()) {
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("title", j);
					jsonObj.put("value", cameraJsonObj.get(j));
					cameraListTemp.put(jsonObj);
				}
				cameraListOfLists.put(cameraListTemp);
			}

			response.put("Camera", cameraListOfLists);

			return response.toMap();
		case "Devices":
			response = new MngtServerUtils().getCentralUnitDetails_1_0(siteId);
			if (response.getString("Status").equals("Failed")) {
				return response.toMap();
			}
			JSONObject response1 = new MngtServerUtils().getDeviceDetailsForSite_1_0(siteId);
			if (response1.getString("Status").equals("Failed")) {
				return response1.toMap();
			}
			response1.remove("siteId");

			response1.put("CentralBoxDetails", response.get("CentralBoxDetails"));
			JSONObject centralBoxDetails1 = response1.getJSONObject("CentralBoxDetails");

			JSONArray centralBoxDetailsList1 = new JSONArray();

			for (String i : centralBoxDetails1.keySet()) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("title", i);
				jsonObj.put("value", centralBoxDetails1.get(i));
				centralBoxDetailsList1.put(jsonObj);
			}
			response1.put("CentralBoxDetails", centralBoxDetailsList1);

			JSONArray devicesDataList = response1.getJSONArray("devices");
			JSONArray devicesListOfList = new JSONArray();
			for (Object i : devicesDataList) {
				JSONObject tempObj = new JSONObject(i.toString());
				JSONArray tempArray = new JSONArray();
				for (String j : tempObj.keySet()) {
					System.out.println("j.." + j);
					if (j.equals("accessories")) {

						JSONObject tempObj2 = new JSONObject();
						JSONArray tempArr2 = tempObj.getJSONArray(j);
						JSONObject ktempobj = new JSONObject();
						for (Object k : tempArr2) {
							JSONObject kobj = new JSONObject(k.toString());
							JSONArray ktempArray = new JSONArray();

							for (String l : kobj.keySet()) {
								// System.out.println("titl1............"+l);
								// System.out.println("value1............"+kobj.get(l));
								JSONObject ltempObj = new JSONObject();
								ltempObj.put("title", l);
								ltempObj.put("value", kobj.get(l));
								ktempArray.put(ltempObj);
							}

							ktempobj.put("title", j);
							ktempobj.put("value", ktempArray);

						}
						System.out.println("title for............" + j);
						System.out.println("value for............" + ktempobj);
						tempObj2.put("title", j);
						tempObj2.put("value", ktempobj);
						tempArray.put(tempObj2);
					} else {
						// System.out.println("title.. else............"+j);
						// System.out.println("value..else............"+tempObj.get(j));
						JSONObject tempObj2 = new JSONObject();
						tempObj2.put("title", j);
						tempObj2.put("value", tempObj.get(j));
						tempArray.put(tempObj2);
					}
				}
				devicesListOfList.put(tempArray);
			}
			response1.put("devices", devicesListOfList);

			return response1.toMap();
		default:
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Please send request type either Cameras or Devices");
				}
			};
		}

	}

	/**
	 * 
	 * @param siteId
	 * @param requestType
	 * @return
	 */
	public Object getDeviceDetailsByRequestType2(int siteId, String requestType) {

		switch (requestType) {
		case "Cameras":

			JSONObject response = new MngtServerUtils().getCentralUnitDetails_1_0(siteId);
			if (response.getString("Status").equals("Failed")) {
				return response.toMap();
			}
			response.remove("siteId");

			JSONObject centralBoxDetails = response.getJSONObject("CentralBoxDetails");

			JSONArray centralBoxDetailsList = new HashMapUtil().hashmapToList(centralBoxDetails);

			response.put("CentralBoxDetails", centralBoxDetailsList);

			JSONArray cameraList = response.getJSONArray("Camera");
			JSONArray cameraListOfLists = new JSONArray();
			for (Object i : cameraList) {
				JSONObject cameraJsonObj = new JSONObject(i.toString());
				JSONArray cameraListTemp = new HashMapUtil().hashmapToList(cameraJsonObj);
				cameraListOfLists.put(cameraListTemp);
			}
			response.put("Camera", cameraListOfLists);

			return response.toMap();

		case "Devices":
			response = new MngtServerUtils().getCentralUnitDetails_1_0(siteId);
			if (response.getString("Status").equals("Failed")) {
				return response.toMap();
			}
			JSONObject response1 = new MngtServerUtils().getDeviceDetailsForSite_1_0(siteId);
			if (response1.getString("Status").equals("Failed")) {
				return response1.toMap();
			}
			response1.remove("siteId");

			response1.put("CentralBoxDetails", response.get("CentralBoxDetails"));
			JSONObject centralBoxDetails1 = response1.getJSONObject("CentralBoxDetails");

			JSONArray centralBoxDetailsList1 = new HashMapUtil().hashmapToList(centralBoxDetails1);
			response1.put("CentralBoxDetails", centralBoxDetailsList1);

			JSONArray devicesDataList = response1.getJSONArray("devices");
			JSONArray devicesListOfList = new JSONArray();
			for (Object i : devicesDataList) {
				JSONObject tempObj = new JSONObject(i.toString());
				JSONArray tempArray = new JSONArray();
				for (String j : tempObj.keySet()) {

					if (j.equals("accessories")) {

						JSONObject tempObj2 = new JSONObject();
						JSONArray tempArr2 = tempObj.getJSONArray(j);
						JSONObject ktempobj = new JSONObject();
						for (Object k : tempArr2) {
							JSONObject kobj = new JSONObject(k.toString());
							JSONArray ktempArray = new HashMapUtil().hashmapToList(kobj);

//							ktempobj.put("title", j);
//							ktempobj.put("value", ktempArray);

							tempArray.put(new JSONObject() {
								{
									put("title", "accessories");
									put("value", ktempArray);
								}
							});
						}

//						System.out.println("title for2............" + j);
//						System.out.println("value for2............" + ktempobj);
//						tempObj2.put("title", j);
//						tempObj2.put("value", ktempobj);
//						tempArray.put(tempObj2);
					} else {

						JSONObject tempObj2 = new JSONObject();
						tempObj2.put("title", j);
						tempObj2.put("value", tempObj.get(j));
						tempArray.put(tempObj2);
					}
				}
				devicesListOfList.put(tempArray);
			}
			response1.put("devices", devicesListOfList);

			return response1.toMap();
		default:
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Please send request type either Cameras or Devices");
				}
			};
		}

	}

	public Object getVerticalsCount() {

		return new MngtServerUtils().getVerticalCounts_1_0().toMap();
	}

	public Object customersList_1_0() {
		return new MngtServerUtils().listCustomers_1_0().toMap();
	}

	public Object sitesList_1_0() {
		return new MngtServerUtils().listSites_1_0().toMap();
	}

	public Object createCustomer_1_0(HashMap<String, Object> data) {
		// TODO Auto-generated method stub
		return new MngtServerUtils().createCustomer_1_0(data).toMap();
	}

}
