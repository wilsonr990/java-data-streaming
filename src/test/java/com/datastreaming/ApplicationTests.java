package com.datastreaming;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class ApplicationTests {

    @Test
    public void applicationStarts() {
        Application.main(new String[]{});
    }
}
