package com.richardjameskendall.ses;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;

import com.google.gson.Gson;
import java.util.HashMap;


public class EmailSender {
    private AmazonSimpleEmailService client;
    private String configSetName;

    public EmailSender(String region, String configSetName) {
        client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.fromName(region)).build();
        this.configSetName = configSetName;
    }

    public void sendWithTemplate(String to, String from, String template, HashMap<String, String> fields) {
        Gson gson = new Gson();
        SendTemplatedEmailRequest request = new SendTemplatedEmailRequest()
                .withDestination(new Destination().withToAddresses(to))
                .withConfigurationSetName(this.configSetName)
                .withTemplate(template)
                .withTemplateData(gson.toJson(fields))
                .withSource(from);
        client.sendTemplatedEmail(request);
    }
}
