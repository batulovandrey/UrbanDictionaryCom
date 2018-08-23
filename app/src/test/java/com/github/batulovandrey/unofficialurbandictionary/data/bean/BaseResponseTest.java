package com.github.batulovandrey.unofficialurbandictionary.data.bean;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Andrey Batulov on 22/12/2017
 */
public class BaseResponseTest {

    private static final String DIRECTORY = "src/test/resources";
    private static final String FILE_NAME = "base_definition_response.json";

    private BaseResponse mExpected;

    @Before
    public void setUp() {
        mExpected = getExpectedBaseResponse();
    }

    @Test
    public void testParseObject() {
        try {
            BaseResponse baseResponse = getBaseResponse();
            assertEquals(mExpected, baseResponse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BaseResponse getBaseResponse() throws FileNotFoundException {
        String file = DIRECTORY + File.separator + FILE_NAME;
        return new Gson().fromJson(new FileReader(file), BaseResponse.class);
    }

    public BaseResponse getExpectedBaseResponse() {
        return new BaseResponse(getTags(), "exact", getDefinitionResponses());
    }

    private List<DefinitionResponse> getDefinitionResponses() {
        List<DefinitionResponse> definitionResponses = new ArrayList<>();
        DefinitionResponse definitionResponse = new DefinitionResponse();
        definitionResponse.setDefinition("Means hello, anything new in your life? " +
                "Identical to [wagwaan] and [wagwan], but is spelt wag1 on chat websites, " +
                "because it is quicker to type.");
        definitionResponse.setPermalink("http://wag1.urbanup.com/397462");
        definitionResponse.setThumbsUp(618);
        definitionResponse.setAuthor("Krackpipe");
        definitionResponse.setWord("Wag1");
        definitionResponse.setDefid(397462);
        definitionResponse.setExample("Wag1, meh bedrins?");
        definitionResponse.setThumbsDown(291);
        definitionResponses.add(definitionResponse);
        return definitionResponses;
    }

    private List<String> getTags() {
        List<String> tags = new ArrayList<>();
        tags.add("wagwan");
        tags.add("crew");
        return tags;
    }
}