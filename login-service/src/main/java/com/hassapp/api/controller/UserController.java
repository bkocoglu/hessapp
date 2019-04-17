package com.hassapp.api.controller;

import com.hassapp.api.model.City;
import com.hassapp.api.model.User;
import com.hassapp.api.dto.UserNamesDTO;
import com.hassapp.api.service.FileStorageService;
import com.hassapp.api.service.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = UserController.END_POINT)
public class UserController {
    static final String END_POINT = "/user";
    static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private InfoService infoService;
    private FileStorageService fileStorageService;

    UserController(InfoService infoService,
                   FileStorageService fileStorageService){
        this.infoService = infoService;
        this.fileStorageService = fileStorageService;
    }

    @CrossOrigin
    @PostMapping(
            value = "/profile-photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("nick") String nick){
        try {
            System.out.println(nick);
            System.out.println(file.getOriginalFilename());

            User user = infoService.userInformation(nick);

            if (user.getPhotoUrl() != null){
                String deletePhotoUrl = user.getPhotoUrl();
                String[] urlParse = deletePhotoUrl.split("/");
                String fileName = urlParse[urlParse.length-1];
                fileStorageService.deleteFile(fileName);
            }

            fileStorageService.store(file, file.getOriginalFilename());
            String fileUrl = MvcUriComponentsBuilder.fromMethodName(
                    UserController.class,
                    "getFile",
                    file.getOriginalFilename()
            ).build().toString();
            System.out.println(fileUrl);
            user.setPhotoUrl(fileUrl);
            infoService.save(user);
            return ResponseEntity.ok(fileUrl);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @CrossOrigin
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUser(@RequestParam("nickname") String nickname) {
        User user = infoService.userInformation(nickname);
        user.setPassword(null);

        return ResponseEntity.ok().body(user);
    }

    @CrossOrigin
    @PostMapping(
            value = "/namesInfo",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getUsersName(@RequestBody UserNamesDTO nicnames){
        List<String> names = infoService.nicknamesConvertNames(nicnames.getNicnames());

        if (names == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(UserNamesDTO.builder().nicnames(names).build());
    }

    @GetMapping(value = "/city")
    public ResponseEntity getAllCity(){
        List<City> cities = infoService.getAllCity();

        return ResponseEntity.status(HttpStatus.OK).body(cities);
    }

    @CrossOrigin
    @GetMapping(value = "/deneme")
    public ResponseEntity deneme(){
        return ResponseEntity.ok().build();
    }
}
