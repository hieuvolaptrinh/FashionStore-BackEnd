package com.HieuVo.FashionStore_BackEnd.Util;


import com.HieuVo.FashionStore_BackEnd.DTO.ResDriver;
import com.HieuVo.FashionStore_BackEnd.DTO.ResponseData;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
public class GoogleDriveUploader {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACOUNT_KEY_PATH = getPathToGoodleCredentials();

    private static String getPathToGoodleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "ServiceAccount.json");
        return filePath.toString();
    }

    public  ResDriver uploadImageToDrive(File file) throws Exception {
        ResDriver res = new ResDriver();

        try{
            String folderId = "1gcZ357GSFH0pTPyXHBB6JV_BsrAKTpiK";
            Drive drive = createDriveService();
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));
            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File uploadedFile = drive.files().create(fileMetaData, mediaContent)
                    .setFields("id").execute();
            String imageUrl = "https://drive.google.com/uc?export=view&id="+uploadedFile.getId();
            System.out.println("IMAGE URL: " + imageUrl);
            file.delete();
            res.setStatus(200);
            res.setMessage("Image Successfully Uploaded To Drive");
            res.setUrl(imageUrl);
        }catch (Exception e){
            System.out.println(e.getMessage());
            res.setStatus(500);
            res.setMessage(e.getMessage());
        }
        listFilesInFolder();
        return  res;

    }

    private Drive createDriveService() throws GeneralSecurityException, IOException {

        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credential)
                .build();

    }
//    duyệt qua lấy id của folder gắn tay vào csdl
public void listFilesInFolder() throws Exception {
    Drive driveService = createDriveService(); // Dùng lại hàm của bạn

    String folderId = "1gcZ357GSFH0pTPyXHBB6JV_BsrAKTpiK";
    String query = "'" + folderId + "' in parents";

    FileList result = driveService.files().list()
            .setQ(query)
            .setFields("files(id, name)")
            .execute();

    List<com.google.api.services.drive.model.File> files = result.getFiles();
    if (files.isEmpty()) {
        System.out.println("Không tìm thấy file nào.");
    } else {
        for (com.google.api.services.drive.model.File file : files) {
            System.out.println("Tên ảnh: " + file.getName());
            System.out.println("Link truy cập: https://drive.google.com/uc?export=view&id=" + file.getId());
            System.out.println("ID: " + file.getId());
            System.out.println("-----------------------------------");
        }
    }
}
}
