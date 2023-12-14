package com.example.spring_starter.payload.album;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {
    @NotBlank
    @Schema(description = "album name", example = "travel", requiredMode = RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "album description", example = "travel more", requiredMode = RequiredMode.REQUIRED)
    private String description;
}