package com.github.batulovandrey.unofficialurbandictionary.data.network

import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponse
import com.github.batulovandrey.unofficialurbandictionary.data.bean.BaseResponseTest
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.FileNotFoundException

class AppNetworkHelperTest {

    private lateinit var baseSingle: Single<BaseResponse>
    private lateinit var baseResponse: BaseResponse
    private lateinit var expectedResponse: BaseResponse

    @Mock
    private val networkHelper = mock(AppNetworkHelper::class.java)

    @Before
    fun setUp() {
        try {
            val baseResponseTest = BaseResponseTest()
            baseResponse = baseResponseTest.baseResponse
            baseSingle = Single.just(baseResponse)
            expectedResponse = baseResponseTest.expectedBaseResponse
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
        }
    }

    @Test
    fun getDataTest() {
        `when`(networkHelper.getData("test")).thenReturn(baseSingle)
        networkHelper.getData("test").test().assertValue(expectedResponse)
    }
}