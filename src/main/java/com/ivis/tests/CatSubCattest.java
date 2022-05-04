package com.ivis.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.ivis.ApplicationModels.CategoriesModel;
import com.ivis.ApplicationModels.CategoriesSubcategoriesOutputModel;
import com.ivis.ApplicationModels.Subcategories;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
public class CatSubCattest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("http://10.0.2.197:8080/api/tsc/getTService")
				  .method("GET", null)
				  .build();
				
					Response response = client.newCall(request).execute();
					JSONArray jsonList = new JSONArray(response.body().string());
					ArrayList<CategoriesModel> dataList = new ArrayList<CategoriesModel>();
					for(Object i:jsonList)
						dataList.add(new Gson().fromJson(i.toString(), CategoriesModel.class));

					
					HashMap<String,Object> outputMap = new HashMap<>();
					
					ArrayList<CategoriesSubcategoriesOutputModel> categoriesOutputList = new ArrayList<>();
					ArrayList<Integer> catIdList = new ArrayList<Integer>();
					for(CategoriesModel i : dataList)
					{
						if(!catIdList.contains(i.getCatid()))
						{
							catIdList.add(i.getCatid());
						}
					}
					System.out.println(catIdList);
					
					
					for(Integer i:catIdList)
					{
						ArrayList<Subcategories> subCatList = new ArrayList<Subcategories>();
						String catName = "";
						for(CategoriesModel j : dataList)
						{

							Subcategories subcat = new Subcategories();
							subcat.setSubcatid(j.getSubcatid());
							subcat.setSubcatname(j.getSubcatname());
							if(j.getCatid()==i&&(!(subCatList.contains(subcat))))
							{

								subCatList.add(subcat);
								catName = j.getCatname();
							}
						}
						
						CategoriesSubcategoriesOutputModel category = new CategoriesSubcategoriesOutputModel();

						category.setSubcategoriesList(subCatList);
						category.setCatid(i);
						category.setCatname(catName);
						categoriesOutputList.add(category);
					}
					ObjectMapper mapper = new ObjectMapper();
					System.out.println(mapper.writeValueAsString(categoriesOutputList));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

	
	

	
}
