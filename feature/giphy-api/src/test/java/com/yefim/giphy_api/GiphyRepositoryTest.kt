package com.yefim.giphy_api

import com.yefim.giphy_api.api.GiphySearchApi
import com.yefim.giphy_api.api.GiphySearchByIDApi
import com.yefim.giphy_api.defaults.GiphySearchApiDefaults.DEFAULT_RATING
import com.yefim.giphy_api.model.GiphySearchByIDResponse
import com.yefim.giphy_api.model.GiphySearchResponse
import com.yefim.giphy_api.model.MPAARating
import com.yefim.giphy_api.repository.GiphyRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

private const val TEST_KEY = "TEST_KEY"

class GiphyRepositoryTest {

    @Mock
    private lateinit var searchService: GiphySearchApi

    @Mock
    private lateinit var findByIdService: GiphySearchByIDApi

    private lateinit var repository: GiphyRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = GiphyRepository(searchService, findByIdService)
    }

    @Test
    fun `test search calls correct service method`() = runBlocking {
        // Arrange
        val key = TEST_KEY
        val query = "test_query"
        val limit = 10
        val offset = 0
        val rating = MPAARating.G
        val mockResponse = GiphySearchResponse()
        `when`(searchService.search(key, query, limit, offset, rating)).thenReturn(mockResponse)

        // Act
        val result = repository.search(
            key = key,
            query = query,
            limit = limit,
            offset = offset,
            rating = rating
        )

        // Assert
        verify(searchService).search(
            key = key,
            query = query,
            limit = limit,
            offset = offset,
            rating = rating
        )
        assertEquals(mockResponse, result)
    }

    @Test
    fun `test findById calls correct service method`() = runBlocking {
        // Arrange
        val key = TEST_KEY
        val gifId = "test_gif_id"
        val rating = MPAARating.G
        val mockResponse = GiphySearchByIDResponse()
        `when`(findByIdService.findByID(key = key, id = gifId, rating = rating)).thenReturn(
            mockResponse
        )

        // Act
        val result = repository.findById(key = key, gifId = gifId, rating = rating)

        // Assert
        verify(findByIdService).findByID(key = key, id = gifId, rating = rating)
        assertEquals(mockResponse, result)
    }

    @Test
    fun `test search uses default rating when not provided`() = runBlocking {
        // Arrange
        val key = TEST_KEY
        val query = "test_query"
        val limit = 10
        val offset = 0
        val defaultRating = DEFAULT_RATING
        val mockResponse = GiphySearchResponse()
        `when`(
            searchService.search(
                key = key,
                query = query,
                limit = limit,
                offset = offset,
                rating = defaultRating
            )
        ).thenReturn(
            mockResponse
        )

        // Act
        val result = repository.search(key = key, query = query, limit = limit, offset = offset)

        // Assert
        verify(searchService).search(
            key = key,
            query = query,
            limit = limit,
            offset = offset,
            rating = defaultRating
        )
        assertEquals(mockResponse, result)
    }
}
