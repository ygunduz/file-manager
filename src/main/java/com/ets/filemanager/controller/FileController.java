package com.ets.filemanager.controller;

import com.ets.filemanager.dto.ApiResponse;
import com.ets.filemanager.dto.FileResponseDto;
import com.ets.filemanager.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FileResponseDto>>> getAllFiles() {
        return ResponseEntity.ok(ApiResponse.ok(fileService.list()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable Long id) {
        FileResponseDto file = fileService.get(id);
        byte[] bytes = fileService.readFile(id);
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename(file.getName() + "." + file.getExtension().getExtension())
                                .build().toString())
                .body(resource);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FileResponseDto>> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.save(file)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FileResponseDto>> update(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.ok(fileService.update(id, file)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        fileService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.ok(null));
    }

}
