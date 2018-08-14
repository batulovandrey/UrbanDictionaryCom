package com.github.batulovandrey.unofficialurbandictionary.data.bean;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class DefinitionResponse extends RealmObject {

    @SerializedName("definition")
    @Expose
    private String definition;

    @SerializedName("permalink")
    @Expose
    private String permalink;

    @SerializedName("thumbs_up")
    @Expose
    private int thumbsUp;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("word")
    @Expose
    private String word;

    @SerializedName("defid")
    @Expose
    private long defid;

    @SerializedName("example")
    @Expose
    private String example;

    @SerializedName("thumbs_down")
    @Expose
    private int thumbsDown;

    public DefinitionResponse() {
        // needed by Realm
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(int thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getDefid() {
        return defid;
    }

    public void setDefid(long defid) {
        this.defid = defid;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public int getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(int thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefinitionResponse that = (DefinitionResponse) o;
        return thumbsUp == that.thumbsUp &&
                defid == that.defid &&
                thumbsDown == that.thumbsDown &&
                Objects.equal(definition, that.definition) &&
                Objects.equal(permalink, that.permalink) &&
                Objects.equal(author, that.author) &&
                Objects.equal(word, that.word) &&
                Objects.equal(example, that.example);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(definition,
                permalink,
                thumbsUp,
                author,
                word,
                defid,
                example,
                thumbsDown);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("definition", definition)
                .add("permalink", permalink)
                .add("thumbsUp", thumbsUp)
                .add("author", author)
                .add("word", word)
                .add("defid", defid)
                .add("example", example)
                .add("thumbsDown", thumbsDown)
                .toString();
    }
}