package com.project.hawfarm;

public class ServerData {

    private static final String LOCALHOST_IP = "http://192.168.43.28:8000/";
    private static final String SERVER_HOSTNAME = "http://hawfarm.openode.io/";

    //Change this only for choose localhost or server host
    public static final String CURRENT_HOST = LOCALHOST_IP;

    public static final String REGISTER_URL = CURRENT_HOST + "user/register";
    public static final String LOGIN_URL = CURRENT_HOST + "user/login";

    public static final String ALL_STOCK_URL = CURRENT_HOST + "stock/all";
}
