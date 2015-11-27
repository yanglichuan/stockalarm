
package com.ad.block.osaadblock;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * @author tom
 */
public class NetUtils {
        /**
         * use this
         * @param context
         * @return
         */
        public static boolean checkNetState(Context context){
            if(context == null){
                return false;
            }
            boolean bConnected = false; 
            ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
            if(connectivity != null){ 
                NetworkInfo[] infos = connectivity.getAllNetworkInfo(); 
                if (infos != null) { 
                    for (int i = 0; i < infos.length; i++){ 
                    	NetworkInfo info = infos[i];
                        if (info.isAvailable() && info.getState() == NetworkInfo.State.CONNECTED){ 
                        	bConnected = true; 
                            break; 
                        } 
                    } 
                } 
            } 
            return bConnected; 
        } 
  
        /**
         * 
         * @param context
         * @return
         * @throws Exception
         */ 
        public static boolean isMobileNetEnable(Context context) throws Exception { 
            if(context == null){
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context 
                    .getSystemService(Context.CONNECTIVITY_SERVICE); 
            boolean isMobileDataEnable = false; 
       
            isMobileDataEnable = connectivityManager.getNetworkInfo( 
                    ConnectivityManager.TYPE_MOBILE).isConnected(); 
       
            return isMobileDataEnable; 
        } 
       
           
        /**
         * @param context
         * @return
         * @throws Exception
         */ 
        public static boolean isWifiNetEnable(Context context) throws Exception { 
            if(context == null){
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context 
                    .getSystemService(Context.CONNECTIVITY_SERVICE); 
            boolean isWifiDataEnable = false; 
            isWifiDataEnable = connectivityManager.getNetworkInfo( 
                    ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting(); 
            return isWifiDataEnable; 
        } 
        
        
//      public static boolean isNetworkRoaming(Context context) { 
//          if(context == null){
//              return false;
//          }
//          ConnectivityManager connectivity = (ConnectivityManager) context 
//                  .getSystemService(Context.CONNECTIVITY_SERVICE); 
//          if (connectivity == null) { 
//              Log.purple_bt(LOG_TAG, "couldn't get connectivity manager");
//          } else { 
//              NetworkInfo info = connectivity.getActiveNetworkInfo(); 
//              if (info != null 
//                      && info.getType() == ConnectivityManager.TYPE_MOBILE) { 
//                  TelephonyManager tm = (TelephonyManager) context 
//                          .getSystemService(Context.TELEPHONY_SERVICE); 
//                  if (tm != null && tm.isNetworkRoaming()) { 
//                      Log.d(LOG_TAG, "network is roaming"); 
//                      return true; 
//                  } else { 
//                      Log.d(LOG_TAG, "network is not roaming"); 
//                  } 
//              } else { 
//                  Log.d(LOG_TAG, "not using mobile network"); 
//              } 
//          } 
//          return false; 
//      } 
     
}
