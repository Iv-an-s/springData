package ru.isemenov.springData.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.isemenov.springData.dto.ResultDto;

@RestController
public class MainController {

    @GetMapping("/calc/add")
    public ResultDto calculateAdd(@RequestParam Integer a, @RequestParam Integer b){
        return new ResultDto(a + b);
    }
}
