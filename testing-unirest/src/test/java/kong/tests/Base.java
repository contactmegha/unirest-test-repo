/**
 * The MIT License
 *
 * Copyright for portions of unirest-java are held by Kong Inc (c) 2013.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package kong.tests;

import kong.unirest.MockClient;
import kong.unirest.Unirest;
import kong.unirest.UnirestAssertion;
import kong.unirest.UnirestInstance;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



public class Base {
    protected MockClient client;
    protected String path = "http://basic";;
    protected String otherPath = "http://other";;

    @BeforeMethod
    public void setUp() {
        client = MockClient.register();
    }

    @AfterMethod
    void tearDown() {
        MockClient.clear();
    }

    public static void assertException(ExRunnable runnable, String message) {
        try{
            runnable.run();
            org.testng.Assert.fail("Expected exception but got none.");
        } catch (UnirestAssertion e){
        	org.testng.Assert.assertEquals(message, e.getMessage(), "Wrong Error Message");
        }
    }

    public static void assertException(ExRunnable runnable) {
        try{
            runnable.run();
            org.testng.Assert.fail("Expected exception but got none.");
        } catch (UnirestAssertion e){ }
    }

    @FunctionalInterface
    public interface ExRunnable {
        void run() throws Error;
    }
}
