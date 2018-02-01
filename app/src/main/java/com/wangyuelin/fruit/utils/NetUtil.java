package com.wangyuelin.fruit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.orhanobut.logger.Logger;


/**
 * 网络相关工具类
 * @author Administrator
 *
 */
public class NetUtil {
	
	/**
	 * 检查当前的网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNet(Context context) {
		// 获取到wifi和mobile连接方式
		boolean wifiConnection = isWIFIConnection(context);
		boolean mobileConnection = isMobileConnection(context);

		if (wifiConnection == false && mobileConnection == false) {
			// 如果都不能连接
			// 提示用户设置当前网络——跳转到设置界面
			return false;
		}

		// if(mobileConnection){
		// // mobile:apn ip和端口
		// // 有部分手机：10.0.0.172 010.000.000.172 80
		// // ContentProvier——ContentReserver
		// setApn(context);
		// }
		return true;
	}

	/**
	 * 读取apn设置信息获取到ip 端口
	 */


	/**
	 * 判断wifi是否连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null)
			return networkInfo.isConnected();
		return false;
	}

	// 判断network是否已连接
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			Logger.i("**** newwork is off");
			return false;
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info == null) {
				Logger.i("**** newwork is off");
				return false;
			} else {
				if (info.isAvailable()) {
					Logger.i("**** newwork is on");
					return true;
				}
			}
		}
		Logger.i("**** newwork is off");
		return false;
	}


	/**
	 * 判断apn
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMobileConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null)
			return networkInfo.isConnected();
		return false;

	}

    /**
     * 获取网络的类型
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeNameStr = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        if (subTypeNameStr.equalsIgnoreCase("TD-SCDMA") || subTypeNameStr.equalsIgnoreCase("WCDMA") || subTypeNameStr.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = subTypeNameStr;
                        }
                        break;
                }

            }
        }
        return strNetworkType;
    }

}
