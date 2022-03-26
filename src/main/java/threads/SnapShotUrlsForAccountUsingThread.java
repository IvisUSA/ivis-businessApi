package threads;


import java.util.ArrayList;

import com.google.gson.Gson;
import com.ivis.ApplicationModels.SnapshortUrlsForAccountModel;
import com.ivis.util.util;

import com.ivis.service.ServerConfig;

public class SnapShotUrlsForAccountUsingThread implements Runnable  {

	ArrayList<SnapshortUrlsForAccountModel> sdata = new ArrayList<SnapshortUrlsForAccountModel>();
	
	int siteId;

	public SnapShotUrlsForAccountUsingThread() {
		// TODO Auto-generated constructor stub
	}


	public void run()  
    {    

        System.out.println("Thread is running...");   
        
        if(siteId!=0) {
		String data = util.readUrlData(ServerConfig.cpusapi+"/cameras/SnapshortUrlsForAccount_1_0?accountId="+siteId);
		SnapshortUrlsForAccountModel siteListData = new Gson().fromJson(data, SnapshortUrlsForAccountModel.class);
	
		sdata.add(siteListData);
        }
        
        


    }    
	
	
	public ArrayList<SnapshortUrlsForAccountModel> getSites(ArrayList<Integer> siteList)
	{
		
		sdata = new ArrayList<SnapshortUrlsForAccountModel>();
		
        // this will call run() method   
		for(int i:siteList)
		{
			setSiteId(i);
			SnapShotUrlsForAccountUsingThread r1=new SnapShotUrlsForAccountUsingThread();    
	        Thread t1 =new Thread(r1);    
	        t1.start();  
			String data = util.readUrlData(ServerConfig.cpusapi+"/cameras/SnapshortUrlsForAccount_1_0?accountId="+i);
			SnapshortUrlsForAccountModel siteListData = new Gson().fromJson(data, SnapshortUrlsForAccountModel.class);
		
			sdata.add(siteListData);
		
		}
		return sdata;
	}


	public ArrayList<SnapshortUrlsForAccountModel> getSdata() {
		return sdata;
	}


	public void setSdata(ArrayList<SnapshortUrlsForAccountModel> sdata) {
		this.sdata = sdata;
	}


	public int getSiteId() {
		return siteId;
	}


	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	
	
}
