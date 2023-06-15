package com.springboot.blog.springbootblogapp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CommentDto {

    private long id;

    //name should not be null or empty
    @NotEmpty(message = "Name should not be null")
    private String name;

    //email should not be null
    //valid email address
    @NotEmpty(message = "Email should not be null")
    @Email
    private String email;

    //comment should not be null
    //min 10 char
    @NotEmpty(message = "Comment should not be null")
    @Size(min = 10)
    private String body;
}
