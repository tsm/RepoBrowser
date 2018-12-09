package com.tomszom.repobrowser.repository

import com.tomszom.repobrowser.UserRepositoriesQuery
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
    lateinit var mockGetUserRepositoriesUseCase: GetUserRepositoriesUseCase

    @Mock
    lateinit var mockView: RepositoryContract.View

    private lateinit var repositoryPresenter: RepositoryPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repositoryPresenter = RepositoryPresenter(mockView, mockGetUserRepositoriesUseCase)
    }

    @Test
    fun testGetUserRepos_errorCase_showError() {
        // Given
        val error = "Test error"
        val observable: Observable<List<UserRepositoriesQuery.Node>> = Observable.create { emitter ->
            emitter.onError(Exception(error))
        }

        // When
        `when`(mockView.getGitUser()).thenReturn("test_user")
        `when`(mockGetUserRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

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
        val observable: Observable<List<UserRepositoriesQuery.Node>> = Observable.fromCallable {
            listOf(
                UserRepositoriesQuery.Node("testType", "testName1", "testUrl1"),
                UserRepositoriesQuery.Node("testType", "testName2", "testUrl2"),
                UserRepositoriesQuery.Node("testType", "testName2", "testUrl2")
            )
        }

        // When
        `when`(mockView.getGitUser()).thenReturn("test_user")
        `when`(mockGetUserRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

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
        val observable: Observable<List<UserRepositoriesQuery.Node>> = Observable.fromCallable {
            emptyList<UserRepositoriesQuery.Node>()
        }

        // When
        `when`(mockView.getGitUser()).thenReturn("test_user")
        `when`(mockGetUserRepositoriesUseCase.getObservable(anyString())).thenReturn(observable)

        repositoryPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showEmpty()
        verify(mockView).showRepositories(emptyList())
    }
}
