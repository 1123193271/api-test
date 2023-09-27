package com.example.authtoken.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        Date date = new Date();
        return ResponseEntity.ok("This is a protected endpoint.now is " + simpleDateFormat.format(date));
    }

    @GetMapping("/holiday/year/{year}")
    public Map<String, Object> getHoliday(@PathVariable String year) throws InterruptedException {
        // 休眠0-200毫秒
        Thread.sleep((long) (Math.random() * 201));


        Map<String, Object> response = new HashMap<>();
        response.put("year", year);
        Map<String, Object> holidays = new HashMap<>();
        holidays.put("09-29", createHoliday("中秋节", "2023-09-29", 3, 3));
        response.put("holiday", holidays);
        return response;
    }

    @GetMapping("/weather/{location}")
    public Map<String, Object> getWeather(@PathVariable String location) throws IOException, InterruptedException {
        // 休眠0-200毫秒
        Thread.sleep((long) (Math.random() * 201));
        ClassPathResource resource = new ClassPathResource("templates/weather.json");
        InputStream inputStream = resource.getInputStream();
        String jsonData = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        Map response = objectMapper.readValue(jsonData, Map.class);
        HashMap<String, Object> coord = (HashMap<String, Object>) response.get("coord");
        coord.put("location", location);

        return response;
    }

    private Map<String, Object> createHoliday(String name, String date, int wage, int rest) {
        Map<String, Object> holiday = new HashMap<>();
        holiday.put("holiday", true);
        holiday.put("name", name);
        holiday.put("date", date);
        holiday.put("wage", wage);
        holiday.put("rest", rest);
        return holiday;
    }
}