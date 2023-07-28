package org.rangiffler.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.rangiffler.model.PhotoJson;
import org.rangiffler.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
public class PhotoController {


    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    private final String FOLDER_PATH = "/Users/ilyalogutov/Desktop/RangifflerFiles/";


    @GetMapping("/photos")
    public List<PhotoJson> getPhotosForUser(@AuthenticationPrincipal Jwt principal) {
        try {
            String username = principal.getClaim("sub");
            return photoService.getAllUserPhotos(username);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @GetMapping("/friends/photos")
    public List<PhotoJson> getAllFriendsPhotos(@AuthenticationPrincipal Jwt principal) {
        try {
            String username = principal.getClaim("sub");
            return photoService.getAllFriendsPhotos(username);
        }catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    @PostMapping(value = "/photos")
    public PhotoJson addPhoto(@AuthenticationPrincipal Jwt principal,
                              @RequestBody PhotoJson photoJson) throws IOException {
        try {
            String username = principal.getClaim("sub");
            photoJson.setUsername(username);
        return photoService.addPhoto(photoJson);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @PatchMapping("/photos/{id}")
    public PhotoJson editPhoto(@AuthenticationPrincipal Jwt principal,
                               @RequestBody PhotoJson photoJson) {
        try {
            String username = principal.getClaim("sub");

            photoJson.setUsername(username);
            return photoService.editPhoto(photoJson);
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/photos")
    public void deletePhoto(@AuthenticationPrincipal Jwt principal,
                            @RequestParam UUID photoId) {
        String username = principal.getClaim("sub");
        photoService.deletePhoto(username, photoId);
    }
}
