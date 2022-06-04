package com.ivis.util;

import com.ivis.service.ServerConfig;

public class CpusUtils {

	public static String cpusApi = ServerConfig.cpusapi;
	public static String keycloakApi = ServerConfig.keycloakapi;
	public static String IvisBiApi = ServerConfig.ivisBiApi;

	
	//Note:- Here SiteId is sometimes referred as accountId
	public String GetClientIdFromSiteId(int siteId) {
		try {
			String sitesUrl = cpusApi + "/sites/getBICustomerSiteId_1_0?accId=";
			sitesUrl = sitesUrl + siteId;

			String client_id = util.readUrlData(sitesUrl);
			return client_id.replace("\n", "");
		} catch (Exception e) {
			System.err.println("Error finding customerId from SiteId");

			return null;
		}

	}
}
