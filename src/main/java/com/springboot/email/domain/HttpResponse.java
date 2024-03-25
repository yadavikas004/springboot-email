package com.springboot.email.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Map;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@JsonInclude(Include.NON_DEFAULT)
public class HttpResponse {
  protected String timeStamp;

  protected int statusCode;

  protected HttpStatus status;

  protected String message;

  protected String DeveloperMessage;

  protected String path;

  protected String requestMethod;

  protected Map<?,?> data;
}
