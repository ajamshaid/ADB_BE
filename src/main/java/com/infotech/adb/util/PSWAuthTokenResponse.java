package com.infotech.adb.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;

import java.io.IOException;

@Data
public class PSWAuthTokenResponse {
    private Integer responseCode;
    private String apiToken;
    private String expireIn;
    private String tokenType;
    private String scope;
    private String message;


    public static PSWAuthTokenResponse jsonToObject(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(PSWAuthTokenResponse.class, new PSWAuthTokenResponseDeserializer());
        mapper.registerModule(module);
        return mapper.readValue(json, PSWAuthTokenResponse.class);
    }

    public static  class PSWAuthTokenResponseDeserializer extends StdDeserializer<PSWAuthTokenResponse> {

        public PSWAuthTokenResponseDeserializer() {
            this(null);
        }

        public PSWAuthTokenResponseDeserializer(Class<PSWAuthTokenResponse> t) {
            super(t);
        }

        @Override
        public PSWAuthTokenResponse deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            PSWAuthTokenResponse resp = new PSWAuthTokenResponse();
            ObjectCodec codec = parser.getCodec();
            JsonNode node = codec.readTree(parser);

            resp.setApiToken(node.get("access_token").asText());
            resp.setTokenType(node.get("token_type").asText());
            resp.setExpireIn(node.get("expires_in").asText());
            resp.setScope(node.get("scope").asText());

            return resp;
        }
    }
}
