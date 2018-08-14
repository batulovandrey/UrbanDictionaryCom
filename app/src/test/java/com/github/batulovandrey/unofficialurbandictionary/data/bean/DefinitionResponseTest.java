package com.github.batulovandrey.unofficialurbandictionary.data.bean;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;


/**
 * @author Andrey Batulov on 22/12/2017
 */
public class DefinitionResponseTest {

    private static final String DIRECTORY = "app/src/test/resources";
    private static final String FILE_NAME = "definition_response.json";

    private DefinitionResponse mExpectedDefinitionResponse;

    @Before
    public void setUp() throws Exception {
        mExpectedDefinitionResponse = initExpectedDefinitionResponse();
    }

    @Test
    public void testParseObject() {
        Gson gson = new Gson();
        try {
            String file = DIRECTORY + File.separator + FILE_NAME;
            DefinitionResponse definitionResponse = gson.fromJson(new FileReader(file), DefinitionResponse.class);
            assertEquals(mExpectedDefinitionResponse, definitionResponse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DefinitionResponse initExpectedDefinitionResponse() {
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
        return definitionResponse;
    }
}