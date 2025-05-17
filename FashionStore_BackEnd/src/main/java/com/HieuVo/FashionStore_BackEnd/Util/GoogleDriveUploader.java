package com.HieuVo.FashionStore_BackEnd.Util;


import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

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

    public String uploadImageToDrive(File file) throws Exception {
        String imageUrl = "";
        try {
            String folderId = "1gcZ357GSFH0pTPyXHBB6JV_BsrAKTpiK";
            Drive drive = createDriveService();

            FileContent mediaContent = new FileContent("image/jpeg", file);
            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            // 👇 THÊM PHẦN NÀY
            com.google.api.services.drive.model.Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            drive.permissions()
                    .create(uploadedFile.getId(), permission)
                    .setFields("id")
                    .execute();

            imageUrl = "https://drive.google.com/thumbnail?id=" + uploadedFile.getId();
            file.delete();


        } catch (Exception e) {
            System.out.println("Error uploading file: " + e.getMessage());
        }
        listFilesInFolder();
        return imageUrl;
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


    //    delete file
    public void deleteFileFromDrive(String url) throws Exception {
        try {
            Drive drive = createDriveService();
            String fileId = extractFileIdFromUrl(url);
            drive.files().delete(fileId).execute();
            System.out.println("Đã xóa file với ID: " + fileId);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa file: " + e.getMessage());
            throw e;
        }
    }

    public String extractFileIdFromUrl(String url) {
        String fileId = "";
        try {
            String[] parts = url.split("id=");
            if (parts.length > 1) {
                fileId = parts[1].split("&")[0];
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi trích xuất file ID từ URL: " + e.getMessage());
        }
        return fileId;
    }


}
