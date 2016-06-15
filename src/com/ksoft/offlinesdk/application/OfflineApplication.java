package com.ksoft.offlinesdk.application;

import com.ksoft.offlinesdk.OfflineSDK;
import com.unicom.shield.UnicomApplicationWrapper;

public class OfflineApplication extends UnicomApplicationWrapper {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		doCreate();
	}
	
	/**
	 * 执行初始化
	 */
	private void doCreate(){
		OfflineSDK.lauch(this);
	}
	
}
