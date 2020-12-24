package com.chung.board.cmmn;

import com.chung.board.user.UserVO;
import com.chung.board.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class CmmnController {
//test
    private final MyLogger myLogger;

    public CmmnController(MyLogger myLogger) {
        this.myLogger = myLogger;
    }

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("data","안녕 나는 동현이야!");
        return "index";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request){
        String requestUrl=request.getRequestURL().toString();
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.setRequestURL(requestUrl);

        myLogger.log("controller");
        return "login";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        GenericService genericService=new GenericService();
        return genericService.getShortestRoot();
    }

    @Autowired
    UserService userService;

    @RequestMapping(value = "/joinUs.do",method = RequestMethod.POST)
    public String joinUs(UserVO vo){
        return "index";
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    public String login(UserVO vo,Model model){

        return "main";
    }

    //다운로드 하기.
    @RequestMapping("/downloadImg")
    @ResponseBody
    public ResponseEntity<Resource> downloadImg(@RequestParam Map<String, Object> param){
        System.out.println("hello");
        String filePath = "C:\\Users\\user\\Desktop\\seina.xlsx";
        File target = new File(filePath);
        HttpHeaders header = new HttpHeaders();
        Resource rs = null;
        System.out.println(target.exists());
        if(target.exists()) {
            try {
                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
                if(mimeType == null) {
                    mimeType = "octet-stream";
                }
                rs = new UrlResource(target.toURI());
                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ rs.getFilename() +"\"");
                header.setCacheControl("no-cache");
                header.setContentType(MediaType.parseMediaType(mimeType));
                System.out.println("rs.getURL() = " + rs.getURL());
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<Resource>(rs, header, HttpStatus.OK);
    }
}
