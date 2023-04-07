package snapp.trip.com.snapptripurlshortenerservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;


@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@RedisHash("urlLongRequestDto")
@Builder
//@ApiModel(description = "Request object for POST method")
public class UrlLongRequestDto implements Serializable {

    private Long id;

    //    @ApiModelProperty(required = true, notes = "Url to convert to short")
    private String longUrl;

    //    @ApiModelProperty(notes = "Expiration datetime of url")
    private Date expiresDate;


}
