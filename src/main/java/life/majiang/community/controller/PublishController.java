package life.majiang.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by yangwanjun on 2020/3/17
 */
@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

}
