package com.github.batulovandrey.unofficialurbandictionary.data.bean

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Batulov on 22/12/2017
 */

data class BaseResponse(@SerializedName("tags") val tags: List<String>,
                        @SerializedName("result_type") val resultType: String,
                        @SerializedName("list") val definitionResponses: List<DefinitionResponse>)