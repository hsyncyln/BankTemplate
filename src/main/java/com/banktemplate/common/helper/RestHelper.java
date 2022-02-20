package com.banktemplate.common.helper;

import org.springframework.web.client.RestTemplate;

public class RestHelper {

    private static RestTemplate restTemplate = null;

    public static RestTemplate getRestTemplate(){

        if(restTemplate == null)
            restTemplate = new RestTemplate();

        return restTemplate;
    }



}
