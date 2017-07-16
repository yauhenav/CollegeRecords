package com.yauhenav;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class TestClassRunner
{
    public static void main(String[] args)
    {
        Result result =
                JUnitCore.runClasses(TestClass.class);
        System.out.println(result.getFailures());
    }
}