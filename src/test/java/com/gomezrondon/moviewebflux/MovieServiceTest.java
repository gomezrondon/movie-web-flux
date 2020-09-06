package com.gomezrondon.moviewebflux;

import com.gomezrondon.moviewebflux.service.MovieService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @Autowired
    private MovieService myService;

    @BeforeAll
    static void initAll() {
        System.out.println("---Inside initAll---");
    }

    @BeforeEach
    void init(TestInfo testInfo) {
        System.out.println("Start..." + testInfo.getDisplayName());
    }


}
