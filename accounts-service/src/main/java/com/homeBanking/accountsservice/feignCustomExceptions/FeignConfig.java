package com.homeBanking.accountsservice.feignCustomExceptions;

import feign.codec.ErrorDecoder;

public class FeignConfig {

    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
