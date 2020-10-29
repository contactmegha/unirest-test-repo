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

import kong.unirest.HttpMethod;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


import static kong.unirest.HttpMethod.GET;
import static kong.unirest.HttpStatus.BAD_REQUEST;

import org.testng.annotations.Test;


public class ExpectedResponseTest extends Base {
    @Test
    void canExpectErrors() {
        client.expect(HttpMethod.GET, path).thenReturn().withStatus(BAD_REQUEST, "oh noes");

        HttpResponse<String> response = Unirest.get(path).asString();

        org.testng.Assert.assertEquals(BAD_REQUEST, response.getStatus());
        org.testng.Assert.assertEquals("oh noes", response.getStatusText());
    }

    @Test
    void canExpectErrorsJustStatus() {
        client.expect(HttpMethod.GET, path).thenReturn().withStatus(BAD_REQUEST);

        HttpResponse<String> response = Unirest.get(path).asString();

        org.testng.Assert.assertEquals(BAD_REQUEST, response.getStatus());
        org.testng.Assert.assertEquals("", response.getStatusText());
    }

    @Test
    public void simpleGetString() {
        client.expect(HttpMethod.GET, path)
                .thenReturn("Hello World");

        org.testng.Assert.assertEquals("Hello World", Unirest.get(path).asString().getBody());
    }

    @Test
    public void simpleGetBytes() {
        client.expect(HttpMethod.GET, path)
                .thenReturn("Hello World");

        byte[] body = Unirest.get(path).asBytes().getBody();
        org.testng.Assert.assertEquals("Hello World", new String(body));
    }

    @Test
    public void simpleJson() {
        client.expect(HttpMethod.GET, path)
                .thenReturn("{\"fruit\": \"apple\"}");

        org.testng.Assert.assertEquals("apple",
                Unirest.get(path).asJson().getBody().getObject().getString("fruit"));
    }

    @Test
    public void setReturnAsJson() {
        client.expect(HttpMethod.GET, path)
                .thenReturn(new JSONObject("{\"fruit\": \"apple\"}"));

        org.testng.Assert.assertEquals("apple",
                Unirest.get(path).asJson().getBody().getObject().getString("fruit"));
    }

    @Test
    public void canPassInAndReturnObjectsAsJson() {
        client.expect(HttpMethod.GET, path)
                .thenReturn(new Pojo("apple"));
    }

    @Test
    public void canSetResponseHeaders() {
        client.expect(GET, path)
                .thenReturn("foo")
                .withHeader("monster", "grover");

        HttpResponse<String> rez = Unirest.get(path).asString();
        org.testng.Assert.assertEquals("foo", rez.getBody());
        org.testng.Assert.assertEquals("grover", rez.getHeaders().getFirst("monster"));
    }

    @Test
    public void canReturnEmptyWithHeaders() {
        client.expect(GET, path)
                .thenReturn()
                .withHeader("monster", "grover");

        HttpResponse<String> rez = Unirest.get(path).asString();
        org.testng.Assert.assertEquals(null, rez.getBody());
        org.testng.Assert.assertEquals("grover", rez.getHeaders().getFirst("monster"));
    }
}
