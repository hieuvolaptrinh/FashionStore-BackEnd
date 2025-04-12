package com.HieuVo.FashionStore_BackEnd.Controller;

import com.HieuVo.FashionStore_BackEnd.Util.Anotation.ApiMessage;
import com.HieuVo.FashionStore_BackEnd.Util.GoogleDriveUploader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class DriverController {
    private final GoogleDriveUploader googleDriveUploader;

public DriverController(GoogleDriveUploader googleDriveUploader) {
        this.googleDriveUploader = googleDriveUploader;
}
    @PostMapping("/uploadToGoogleDrive")
    @ApiMessage("Upload file to Google Drive successfully")
    public Object handleFileUpload(@RequestParam("image") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return "FIle is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        String res = this.googleDriveUploader.uploadImageToDrive(tempFile);
        System.out.println(res);


        return res;
    }

}