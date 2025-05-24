package com.solvenyang.image.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageSaveRequest {

    private String originalFilename;
    private String storedFilename;

}
