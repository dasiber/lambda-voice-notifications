package com.ufonia;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.ufonia.model.EventInput;

public class VoiceNotifications implements RequestHandler<EventInput, APIGatewayProxyResponseEvent> {
    private static final String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("AUTH_TOKEN");

    @Override
    public APIGatewayProxyResponseEvent handleRequest(EventInput event, Context context) {
        int statusCode = 400;
        String from = "+1567313-2021";
        String to = event.getPhone();
        String message = event.getMessage();

        APIGatewayProxyResponseEvent lambdaResponse = new APIGatewayProxyResponseEvent();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        if (!to.isEmpty() && !message.isEmpty()) {
            try {
                VoiceResponse twiml = new VoiceResponse.Builder()
                    .say(new Say.Builder(message)
                            .voice(Say.Voice.ALICE)
                            .build())
                    .build();

                Call call = Call.creator(
                        new com.twilio.type.PhoneNumber(to),
                        new com.twilio.type.PhoneNumber(from),
                        new com.twilio.type.Twiml(twiml.toXml()))
                    .create();

                statusCode = 200;

            } catch (Exception e) {
                System.out.println("Exception sending voice notification: " + e);
            }
        }

        return lambdaResponse.withStatusCode(statusCode);
    }
}
