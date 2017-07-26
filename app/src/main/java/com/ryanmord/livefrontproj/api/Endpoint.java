package com.ryanmord.livefrontproj.api;

/**
 * Class that maintains endpoint string variables
 */
class Endpoint {

    /**
     * API Token required for authentication
     */
    private static final String TOKEN = "52dabde93cc64788ac49951c32d25d68";

    /**
     * Base API URL
     */
    public static final String ENDPOINT_BASE = "https://newsapi.org";

    /**
     * Endpoint for CNN API requests.
     */
    static final String ENDPOINT_CNN = "/v1/articles?source=cnn&sortBy=top&apiKey=" + TOKEN;

}
