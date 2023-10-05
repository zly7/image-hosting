package com.example.imagebasic.model;

import java.time.LocalDateTime;

public class Image {

    private String imageName;          // 图片的唯一名称
    private String originalFilename;   // 原始文件名
    private String contentType;        // 文件类型，例如 "image/jpeg"
    private long size;                 // 文件大小，单位：字节
    private LocalDateTime uploadTime;  // 上传的时间

    // 无参构造函数
    public Image() {
    }

    // 全参构造函数
    public Image(String imageName, String originalFilename, String contentType, long size, LocalDateTime uploadTime) {
        this.imageName = imageName;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.uploadTime = uploadTime;
    }

    // getters 和 setters

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    // override toString() 方法，有助于后续的日志记录和调试
    @Override
    public String toString() {
        return "Image{" +
                "imageName='" + imageName + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
