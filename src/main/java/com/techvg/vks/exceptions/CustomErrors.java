package com.techvg.vks.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
*/

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomErrors {

   @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", shape=JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    private String errorMessage;
    private String requestedURI;
    private String errorCode;

}
