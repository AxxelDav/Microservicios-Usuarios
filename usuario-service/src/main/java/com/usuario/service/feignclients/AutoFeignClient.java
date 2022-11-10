package com.usuario.service.feignclients;

import com.usuario.service.modelos.Auto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "auto-service", url = "http://localhost:8002", path = "/auto") //path es lo que tiene el @RequestMapping
public interface AutoFeignClient {

    @PostMapping()
    public Auto save(@RequestBody Auto auto);

    @GetMapping("/usuario/{usuarioId}")
    public List<Auto> getAutos(@PathVariable("usuarioId") Integer usuarioId);
}
