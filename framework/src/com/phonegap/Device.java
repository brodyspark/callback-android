/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package com.phonegap;

import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.phonegap.api.PhonegapActivity;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;
import android.provider.Settings;

public class Device extends Plugin {
	
    public static String phonegapVersion = "1.2.0";             // PhoneGap version
	public static String platform = "Android";					// Device OS
	public static String uuid;									// Device UUID
    
    /**
     * Constructor.
     */
	public Device() {
    }
	
	/**
	 * Sets the context of the Command. This can then be used to do things like
	 * get file paths associated with the Activity.
	 * 
	 * @param ctx The context of the main Activity.
	 */
	public void setContext(PhonegapActivity ctx) {
		super.setContext(ctx);
        Device.uuid = getUuid();
	}

	/**
	 * Executes the request and returns PluginResult.
	 * 
	 * @param action 		The action to execute.
	 * @param args 			JSONArry of arguments for the plugin.
	 * @param callbackId	The callback id used when calling back into JavaScript.
	 * @return 				A PluginResult object with a status and message.
	 */
	public PluginResult execute(String action, JSONArray args, String callbackId) {
		PluginResult.Status status = PluginResult.Status.OK;
		String result = "";		
	
		try {
			if (action.equals("getDeviceInfo")) {
				JSONObject r = new JSONObject();
				r.put("uuid", Device.uuid);
				r.put("version", this.getOSVersion());
				r.put("platform", Device.platform);
				r.put("name", this.getProductName());
				r.put("phonegap", Device.phonegapVersion);
				//JSONObject pg = new JSONObject();
				//pg.put("version", Device.phonegapVersion);
				//r.put("phonegap", pg);
				return new PluginResult(status, r);
			}
			return new PluginResult(status, result);
		} catch (JSONException e) {
			return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
		}
	}

	/**
	 * Identifies if action to be executed returns a value and should be run synchronously.
	 * 
	 * @param action	The action to execute
	 * @return			T=returns value
	 */
	public boolean isSynch(String action) {
		if (action.equals("getDeviceInfo")) {
			return true;
		}
		return false;
	}

    //--------------------------------------------------------------------------
    // LOCAL METHODS
    //--------------------------------------------------------------------------
		
	/**
	 * Get the OS name.
	 * 
	 * @return
	 */
	public String getPlatform()	{
		return Device.platform;
	}
	
	/**
	 * Get the device's Universally Unique Identifier (UUID).
	 * 
	 * @return
	 */
	public String getUuid()	{		
		String uuid = Settings.Secure.getString(this.ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		return uuid;
	}

	/**
	 * Get the PhoneGap version.
	 * 
	 * @return
	 */
	public String getPhonegapVersion() {
		return Device.phonegapVersion;
	}	
	
	public String getModel() {
		String model = android.os.Build.MODEL;
		return model;
	}
	
	public String getProductName() {
		String productname = android.os.Build.PRODUCT;
		return productname;
	}
	
	/**
	 * Get the OS version.
	 * 
	 * @return
	 */
	public String getOSVersion() {
		String osversion = android.os.Build.VERSION.RELEASE;
		return osversion;
	}
	
	public String getSDKVersion() {
		String sdkversion = android.os.Build.VERSION.SDK;
		return sdkversion;
	}
	
    
    public String getTimeZoneID() {
       TimeZone tz = TimeZone.getDefault();
        return(tz.getID());
    } 
    
}

