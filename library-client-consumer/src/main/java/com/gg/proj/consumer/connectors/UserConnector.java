package com.gg.proj.consumer.connectors;

import com.gg.proj.consumer.ConsumerProperties;
import com.gg.proj.consumer.wsdl.users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * This class performs the connection to the web service's user endpoint
 */
public class UserConnector extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(UserConnector.class);

    private String serviceLocation;

    private ConsumerProperties consumerProperties;

    public UserConnector(@Autowired ConsumerProperties consumerProperties) {
        this.consumerProperties = consumerProperties;
        this.serviceLocation = this.consumerProperties.getUri() + "/users";
    }

    public UserConnector(WebServiceMessageFactory messageFactory, @Autowired ConsumerProperties consumerProperties) {
        super(messageFactory);
        this.consumerProperties = consumerProperties;
        this.serviceLocation = this.consumerProperties.getUri() + "/users";
    }

    public Token loginUser(String pseudo, String password) {
        LoginUserRequest request = new LoginUserRequest();
        log.info("Requesting Web service's method LoginUser to login user : [" + pseudo + "]");
        log.debug("Service is located at : " + serviceLocation);

        request.setPseudo(pseudo);
        request.setPassword(password);

        try {
            LoginUserResponse response = (LoginUserResponse) getWebServiceTemplate().
                    marshalSendAndReceive(serviceLocation, request,
                            new SoapActionCallback("http://proj.gg.com/service/library-client"));
            log.info("WS Call successful");
            return response.getToken();
        } catch (Exception e) {

            log.warn("An exception has been catch : " + e);
            return null;
        }
    }

    public String logoutUser(String tokenUUID) {
        LogoutUserRequest request = new LogoutUserRequest();
        log.info("Requesting Web service's method LogoutUser to logout the user");
        log.debug("Service is located at : " + serviceLocation);

        request.setTokenUUID(tokenUUID);

        try {
            LogoutUserResponse response = (LogoutUserResponse) getWebServiceTemplate().
                    marshalSendAndReceive(serviceLocation, request,
                            new SoapActionCallback("http://proj.gg.com/service/library-client"));
            log.info("WS Call successful");
            return response.getLogoutStatus();
        } catch (Exception e) {
            log.warn("An exception has been catch : " + e);
            return null;
        }
    }
}