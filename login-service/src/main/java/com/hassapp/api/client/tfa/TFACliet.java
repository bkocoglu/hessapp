package com.hassapp.api.client.tfa;

import com.hassapp.api.client.tfa.dto.TFAQRRequestDTO;
import com.hassapp.api.client.tfa.dto.TFAQRResponseDTO;
import com.hassapp.api.client.tfa.dto.TFAuthRequestDTO;
import com.hassapp.api.client.tfa.dto.TFAuthResponseDTO;
import com.hassapp.api.providers.URLProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class TFACliet {
    private static final Logger LOGGER = LoggerFactory.getLogger(TFACliet.class);
    private String newQRUrl = URLProvider.apiNewQr;
    private String authUrl = URLProvider.apiTFAuth;

    private RestTemplate restTemplate;

    TFACliet(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TFAQRResponseDTO getNewQrAndPin(String nickname){
        try {
            TFAQRRequestDTO tfaqrRequestDTO = new TFAQRRequestDTO(nickname);

            URI uri = new URI(this.newQRUrl);

            ResponseEntity<TFAQRResponseDTO> res = restTemplate.postForEntity(
                    uri,
                    tfaqrRequestDTO,
                    TFAQRResponseDTO.class
            );

            if ( res.getStatusCode().isError() ){
                LOGGER.error("NewQRApiError");
                LOGGER.error(res.toString());
                return null;
            }

            return res.getBody();

        }catch (Exception e){
            LOGGER.error(e.toString());
            return null;
        }
    }

    public TFAuthResponseDTO pinControl(TFAuthRequestDTO tfAuthRequestDTO){
        try {
            URI uri = new URI(this.authUrl);

            ResponseEntity<TFAuthResponseDTO> res = restTemplate.postForEntity(
                    uri,
                    tfAuthRequestDTO,
                    TFAuthResponseDTO.class
            );

            TFAuthResponseDTO tfAuthResponseDTO = new TFAuthResponseDTO();

            if (res.getStatusCode().is5xxServerError()){
                LOGGER.error("Google Auth Api Error");
                LOGGER.error(res.toString());
                return null;
            }

            if ( res.getStatusCode().is4xxClientError() ){
                tfAuthResponseDTO.setValid(false);
                return tfAuthResponseDTO;
            }

            tfAuthResponseDTO.setValid(true);
            return tfAuthResponseDTO;

        }catch (Exception e){
            LOGGER.error(e.toString());
            return null;
        }
    }


}
