package com.project.hawfarm;

public class ServerData {

    private static final String LOCALHOST_IP = "http://192.168.43.28:8000/";
    private static final String SERVER_HOSTNAME = "https://us-central1-hawfarm-2019.cloudfunctions.net/api/";

    //Change this only for choose localhost or server host
    public static final String CURRENT_HOST = SERVER_HOSTNAME;

    public static final String REGISTER_URL = CURRENT_HOST + "user/register";
    public static final String LOGIN_URL = CURRENT_HOST + "user/login";

    public static final String ALL_STOCK_URL = CURRENT_HOST + "stock/all";
    public static final String ADD_ORDER_URL = CURRENT_HOST + "order/addOrder";
    public static final String LIST_ORDER_URL = CURRENT_HOST + "order/buyer/";
}
