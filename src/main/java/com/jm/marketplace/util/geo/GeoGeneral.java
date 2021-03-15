package com.jm.marketplace.util.geo;

import org.springframework.web.reactive.function.client.WebClient;

public class GeoGeneral {

    protected static final WebClient.RequestHeadersUriSpec<?> REQUEST_HEADERS_URI_SPEC = WebClient.create().get();

    private final String baseURL;

    public GeoGeneral(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getBaseURL() {
        return baseURL;
    }
}
