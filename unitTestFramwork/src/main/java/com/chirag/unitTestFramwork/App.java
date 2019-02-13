package com.chirag.unitTestFramwork;

import java.util.List;

import com.chirag.unitTestFramwork.testData.TestCasebean;

/**
 * Hello world!
 *
 */
public class App 
{
    public void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
    
        
        NestedTestCaseGenerator<TestCasebean> generator = new NestedTestCaseGenerator<>(new TestCasebean(), "com.chirag");
        List<TestCasebean> list = generator.generateTestCases();
        System.out.println("hello : "+list);
    }
    
    
    
   
}
