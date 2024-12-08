package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class CorsController {
    
    @RequestMapping(value="/**", method=RequestMethod.OPTIONS)
    public ResponseEntity<String> handlePreflight() {
        return ResponseEntity.ok("Preflight Success");
    }
    
}
