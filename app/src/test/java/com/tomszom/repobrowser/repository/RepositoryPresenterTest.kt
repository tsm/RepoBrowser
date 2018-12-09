package com.tomszom.repobrowser.repository

import com.tomszom.repobrowser.RepositoriesQuery
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RepositoryPresenterTest {

    @Mock
    lateinit var mockGetRepositoriesUseCase: GetRepositoriesUseCase

    @Mock
    lateinit var mockView: RepositoryContract.View

    private lateinit var repositoryPresenter: RepositoryPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repositoryPresenter = RepositoryPresenter(mockView, mockGetRepositoriesUseCase)
    }

    @Test
    fun testGetUserRepos_errorCase_showError() {
        // Given
        val error = "Test error"
        val observable: Observable<List<RepositoriesQuery.Node>> = Observable.create { emitter ->
            emitter.onError(Exception(error))
        }

        // When
        `when`(mockView.getOwnerLogin()).thenReturn("test_user")
        `when`(mockGetRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

        repositoryPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showError()
    }

    @Test
    fun testGetUserRepos_normalCase_showRepos() {
        // Given
        val observable: Observable<List<RepositoriesQuery.Node>> = Observable.fromCallable {
            listOf(
                RepositoriesQuery.Node("testType", "testName1", "testUrl1"),
                RepositoriesQuery.Node("testType", "testName2", "testUrl2"),
                RepositoriesQuery.Node("testType", "testName2", "testUrl2")
            )
        }

        // When
        `when`(mockView.getOwnerLogin()).thenReturn("test_user")
        `when`(mockGetRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

        repositoryPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showRepositories(anyList())
    }

    @Test
    fun testGetUserRepos_emptyCase_showEmpty() {
        // Given
        val observable: Observable<List<RepositoriesQuery.Node>> = Observable.fromCallable {
            emptyList<RepositoriesQuery.Node>()
        }

        // When
        `when`(mockView.getOwnerLogin()).thenReturn("test_user")
        `when`(mockGetRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

        repositoryPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showEmpty()
        verify(mockView).showRepositories(emptyList())
    }
}
