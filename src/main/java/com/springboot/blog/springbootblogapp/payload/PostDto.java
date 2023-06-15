package com.springboot.blog.springbootblogapp.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Set;

@Data
@Schema(
        description = "PostDto Model Information"
)
public class PostDto {
    private Long id;

    //title should not be empty and null and have at least 2 char
    @Schema(
            description = "Blog Post Title"
    )
    @NotEmpty
    @Size(min = 2, message = "Post message should have at least 2 char")
    private String title;

    //description should not be empty and null and have at least 2 char
    @Schema(
            description = "Blog Post Description"
    )
    @NotEmpty
    @Size(min = 10, message = "Description message should have at least 10 char")
    private String description;


    @Schema(
            description = "Blog Post content"
    )
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog Post category"
    )
    private long categoryId;

}
