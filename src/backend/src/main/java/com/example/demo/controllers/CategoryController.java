package com.example.demo.controllers;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.models.users.User;
import com.example.demo.responses.ResponseObject;
import com.example.demo.services.category.ICategoryService;
import com.example.demo.utils.FileUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllCategories(){
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .data(categoryList)
                        .message("Get all category successfully")
                        .status(HttpStatus.OK)
                        .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> createCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(ResponseObject.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .data(null)
                        .message(String.valueOf(errorMessages))
                        .build());
            }

            Category newCategory = categoryService.insertNewCategory(categoryDTO);
            return ResponseEntity.ok().body(
                    ResponseObject.builder()
                            .data(newCategory)
                            .message("Create category successfully")
                            .status(HttpStatus.OK)
                            .build()
            );
        } catch (Exception e){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping(value = "/uploadImage/{id}",
     consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseObject> uploadsImage(
        @PathVariable("id") String categoryId,
        @ModelAttribute("files")List<MultipartFile> files
    ) throws Exception {
        Category existingCategory = categoryService.getCategoryById(categoryId);

        files = files == null ? new ArrayList<MultipartFile>() : files;
        if(files.size() > Category.MAXIMUM_IMAGES_PER_CATEGORY){
            return ResponseEntity.badRequest().body(ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .message(String.valueOf("MAXIMUM_IMAGES_PER_CATEGORY"))
                    .build());
        }

//        List<String> productImages = new ArrayList<>();
        for(MultipartFile file : files){
            if(file.getSize() == 0){
                continue;
            }

            // Kiểm tra kích thước file và định dạng
            if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(ResponseObject.builder()
                                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                                .message("UPLOAD_IMAGES_FILE_LARGE")
                                .build());
            }

            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ResponseObject.builder()
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .message("UPLOAD_IMAGES_FILE_MUST_BE_IMAGE")
                        .build());
            }

            // Lưu file và cập nhật thumbnail trong DTO
            String fileName = FileUtils.storeFile(file);
            Category updateCategory = categoryService.createCategoryImage(categoryId, fileName);
        }

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .message("Upload image successfully")
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }

    @GetMapping("/viewImage/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName){
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/categories/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notfound.jpeg").toUri()));
            }
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}


