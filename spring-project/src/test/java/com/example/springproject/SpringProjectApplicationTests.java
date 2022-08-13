package com.example.springproject;

import com.example.springproject.domain.type.SearchType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringProjectApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(SearchType.HASHTAG.name());
    }

}
