package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CorsController {
    
    @RequestMapping(value="/**", method=RequestMethod.OPTIONS)
    public ResponseEntity<String> handlePreflight() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Allow-Origin", "*"); // 允许的域名，可根据需求改为具体域名
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        headers.add("Access-Control-Allow-Credentials", "true"); // 是否允许发送 Cookie

        ResponseEntity<String> response = new ResponseEntity<>("Preflight Success", headers, HttpStatus.OK);
        System.out.println(response);
        return response;
    }
    
}
