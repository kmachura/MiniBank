package pl.kmachuramika.minibank.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kmachuramika.minibank.enums.CurrencyShortcutEnum;

import java.io.IOException;

@Slf4j
@SpringBootTest
public class CurrencyControllerTest {

    @Test
    @DisplayName("Test is checking, if we will get 404 Status with invalid currency shortcut")
    public void givenBadCurrencyShortcutForGettingTheExchangeRateTest() throws ClientProtocolException, IOException {

        // given
        String shortcut = CurrencyShortcutEnum.PLN.toString();
        HttpUriRequest request = new HttpGet( "http://api.nbp.pl/api/exchangerates/rates/A/" + shortcut + "/");

        // when
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // then
        Assertions.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Checking, if the default response type is JSON")
    public void checkingIfTheDefaultResponseContentTypeIsJson()
            throws ClientProtocolException, IOException {

        // given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( "http://api.nbp.pl/api/exchangerates/rates/A/USD/");

        // when
        HttpResponse response = HttpClientBuilder.create().build().execute( request );

        // then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        Assertions.assertEquals(jsonMimeType, mimeType);

    }

}
