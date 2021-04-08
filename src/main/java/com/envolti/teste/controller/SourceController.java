package com.envolti.teste.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/source")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SourceController implements SourceControllerDocs {


    @Override
    @GetMapping()
    public String getSource() {
        return "https://github.com/vinicionagel";
    }
}
