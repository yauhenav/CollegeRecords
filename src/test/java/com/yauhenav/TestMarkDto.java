package com.yauhenav;

import static org.junit.Assert.*;
import org.junit.Test;
import com.yauhenav.logic.dto.*;

/**
 * Created by yauhenav on 16.7.17.
 */
public class TestMarkDto {
    @Test
    public void TestMarkObject () {
        Mark objectUnderTest = new Mark(1);
        assertEquals (1, objectUnderTest.getId());
    }
}
