package com.musicPlayer.controller;

import com.musicPlayer.bean.MusicBean;
import com.musicPlayer.entity.Music;
import com.musicPlayer.repository.MusicRepository;
import com.musicPlayer.util.FileUploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MusicRepository musicRepository;

    @GetMapping("/list")
    public List<Music> getListMusic(){
        return musicRepository.findAll();
    }

//    thêm nhạc bằng cách lấy file mp3 từ máy tính
    @PostMapping(path="/add", consumes = { "multipart/form-data" })
    public Music createBook(@ModelAttribute MusicBean musicBean) throws IOException {
        Music music = new Music();
        music.setNameMusic(musicBean.getNameMusic());
        music.setImage(musicBean.getImage());
        music.setSinger(musicBean.getSinger());

        String fileName = new String();
        if (StringUtils.isNotBlank(musicBean.getPath().getName())){
            fileName = musicBean.getPath().getOriginalFilename();
            String path = "/music/" + fileName;
            if (fileName != null) music.setPath(path);

            String uploadDir = "src/main/resources/static/music/";

            FileUploadUtil.saveFile(uploadDir, fileName, musicBean.getPath());
        }else {

        }
        return musicRepository.save(music);
    }

//    thêm nhạc bằng cách lấy link mp3
//    @PostMapping(path="/add")
//    public Music createBook(@ModelAttribute Music music) {
//        return musicRepository.save(music);
//    }

}
