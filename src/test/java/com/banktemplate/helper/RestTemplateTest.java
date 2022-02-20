package com.banktemplate.helper;

import com.banktemplate.common.helper.RestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestTemplateTest {

    //Singleton RestHelper class didn't work correctly
    @Test
    void isInstanceCreated(){
        Assertions.assertNotNull(RestHelper.getRestTemplate(),"RestTemplate could not be created.");
    }

}
