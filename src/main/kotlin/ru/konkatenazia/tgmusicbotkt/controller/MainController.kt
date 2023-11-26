package ru.konkatenazia.tgmusicbotkt.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainController {

    @GetMapping("/")
    fun index(): String {
        return "redirect:/swagger-ui/"
    }
}