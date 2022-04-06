package com.anyungu.data.mapping.v1.models.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank
    private String email;
}