package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.DTO.ResDriver;
import com.HieuVo.FashionStore_BackEnd.Util.GoogleDriveUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class DriverController {


    private final GoogleDriveUploader googleDriveUploader;

public DriverController(GoogleDriveUploader googleDriveUploader) {
        this.googleDriveUploader = googleDriveUploader;
}
    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(@RequestParam("image") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return "FIle is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        ResDriver res = this.googleDriveUploader.uploadImageToDrive(tempFile);
        System.out.println(res);


        return res;
    }

}