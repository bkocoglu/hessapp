package com.hessapp.infoservice.providers;

public class URLProvider {
    public static final String apiGateway = "https://hessapp-api-gateway.herokuapp.com/api";

    //http://localhost:8360/api
    //https://hessapp-api-gateway.herokuapp.com/api

    public static final String apiGroupCreate = "http://37.123.97.178/api/Group/Create";
    public static final String apiGroupDelete = "http://37.123.97.178/api/Group/Delete";

    public static final String apiSpendCreate = "http://37.123.97.178/api/Spend/Create";
    public static final String apiSpendGeneralStatus = "http://37.123.97.178/api/Spend/GetGeneralStatus";
    public static final String apiSpendGroupStatus = "http://37.123.97.178/api/Spend/GetGroupStatus";
    public static final String apiSpendPayDebt = "http://37.123.97.178/api/Spend/PayDebt";


    public static final String apiGetSpends = "http://37.123.97.178/api/Spend/GetSpends";
}
