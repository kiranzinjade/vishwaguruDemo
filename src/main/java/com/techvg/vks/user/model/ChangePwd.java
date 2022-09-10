package com.techvg.vks.user.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePwd implements Serializable {
    private static final long serialVersionUID = -8873098761908299351L;

    private String userName;
    private String oldPassword;
    private String newPassword;

}
