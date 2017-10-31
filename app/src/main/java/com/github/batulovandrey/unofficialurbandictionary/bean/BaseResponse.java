package com.github.batulovandrey.unofficialurbandictionary.bean;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Andrey Batulov on 26/10/2017
 */

public class BaseResponse {

    @SerializedName("tags")
    @Expose
    private List<String> mTags;

    @SerializedName("result_type")
    @Expose
    private String mResultType;

    @SerializedName("list")
    @Expose
    private List<DefinitionResponse> mDefinitionResponses;

    @SerializedName("sounds")
    @Expose
    private List<String> mSounds;

    public BaseResponse() {
        // needed by GSON
    }

    public List<String> getTags() {
        return mTags;
    }

    public void setTags(List<String> tags) {
        mTags = tags;
    }

    public String getResultType() {
        return mResultType;
    }

    public void setResultType(String resultType) {
        this.mResultType = resultType;
    }

    public List<DefinitionResponse> getDefinitionResponses() {
        return mDefinitionResponses;
    }

    public void setDefinitionResponses(List<DefinitionResponse> definitionResponses) {
        mDefinitionResponses = definitionResponses;
    }

    public List<String> getSounds() {
        return mSounds;
    }

    public void setSounds(List<String> sounds) {
        mSounds = sounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseResponse that = (BaseResponse) o;
        return Objects.equal(mTags, that.mTags) &&
                Objects.equal(mResultType, that.mResultType) &&
                Objects.equal(mDefinitionResponses, that.mDefinitionResponses) &&
                Objects.equal(mSounds, that.mSounds);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mTags, mResultType, mDefinitionResponses, mSounds);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mTags", mTags)
                .add("mResultType", mResultType)
                .add("mDefinitionResponses", mDefinitionResponses)
                .add("mSounds", mSounds)
                .toString();
    }
}