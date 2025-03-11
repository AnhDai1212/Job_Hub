package java_spring.job_hub.repository.httpClient;

import java_spring.job_hub.dto.response.OutboundUserResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "outbound-user", url = "https://www.googleapis.com")
public interface OutboundUserClient {
    @GetMapping(value = "/oauth2/v1/userinfo")
    OutboundUserResponse getUserInfor(
            @RequestParam("alt") String alt, @RequestParam("access_token") String access_token);
}
