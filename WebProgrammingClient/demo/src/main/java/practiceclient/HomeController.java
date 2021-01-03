package practiceclient;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeController(Model model){ 
        model.addAttribute("page", "Quản lý thông tin sinh viên");
        return "index.html";
    }
}
