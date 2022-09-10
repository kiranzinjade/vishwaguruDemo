package com.techvg.vks.user.model;

import com.techvg.vks.base.BaseEntityDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserDto extends BaseEntityDto implements Serializable {
    private static final long serialVersionUID = -5040258545649904793L;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;

    public UserDto(@Null Long id, @Null LocalDateTime created, @Null String createdBy, @Null LocalDateTime lastModified,
                   @Null String lastModifiedBy, Boolean isDeleted, String username, String email, String password,
                   String firstName, String middleName, String lastName) {
        super(id, created, createdBy, lastModified, lastModifiedBy, isDeleted);
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
}
