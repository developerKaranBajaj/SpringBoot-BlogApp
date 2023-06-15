package com.springboot.blog.springbootblogapp.controller;

import com.springboot.blog.springbootblogapp.payload.PostDto;
import com.springboot.blog.springbootblogapp.payload.PostResponse;
import com.springboot.blog.springbootblogapp.service.PostService;
import com.springboot.blog.springbootblogapp.utils.AppConstant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
@Tag(
        name = "CRUD REST APIs for Post Resource"
)
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    //create blog post apis
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST APIs is used to store post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Https Status 201 Created"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @Operation(
            summary = "Get All Post REST API",
            description = "Get ALL Post REST APIs is used to fetch all post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Https Status 201 SUCCESS"
    )
    @GetMapping
    public PostResponse getALlPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
    }

    //get post by id
    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post REST APIs is used to get single post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Https Status 200 SUCCESS"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){

        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST APIs is used to update post into database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Https Status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable long id){

        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //delete post by  id
    @Operation(
            summary = "Delete Post REST API",
            description = "Delete Post REST APIs is used to Delete post from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Https Status 201 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id){
        postService.deletePostById(id);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    //Get Posts by Category ID
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") long categoryId){
         List<PostDto> posts = postService.getPostsByCategory(categoryId);
         return ResponseEntity.ok(posts);
    }
}
