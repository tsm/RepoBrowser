package com.tomszom.repobrowser.owner

import com.tomszom.repobrowser.OwnerQuery
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class OwnerPresenterTest {

    @Mock
    lateinit var mockGetOwnersUseCase: GetOwnersUseCase

    @Mock
    lateinit var mockView: OwnerContract.View

    private lateinit var ownerPresenter: OwnerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        ownerPresenter = OwnerPresenter(mockView, mockGetOwnersUseCase)
    }

    @Test
    fun testGetOwners_errorCase_showError() {
        // Given
        val error = "Test error"
        val observable: Observable<List<OwnerQuery.RepositoryOwner>> = Observable.create { emitter ->
            emitter.onError(Exception(error))
        }

        // When
        `when`(mockGetOwnersUseCase.getObservable(anyList())).thenReturn(observable)

        ownerPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showError()
    }

    @Test
    fun testGetOwners_normalCase_showRepos() {
        // Given
        val observable: Observable<List<OwnerQuery.RepositoryOwner>> = Observable.fromCallable {
            listOf(
                OwnerQuery.RepositoryOwner("testType", "", "testLogin1"),
                OwnerQuery.RepositoryOwner("testType", "url", "testLogin2"),
                OwnerQuery.RepositoryOwner("testType", "", "testLogin3")

            )
        }

        // When
        `when`(mockGetOwnersUseCase.getObservable(anyList())).thenReturn(observable)

        ownerPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showOwners(anyList())
    }

    @Test
    fun testGetOwners_emptyCase_showEmpty() {
        // Given
        val observable: Observable<List<OwnerQuery.RepositoryOwner>> = Observable.fromCallable {
            emptyList<OwnerQuery.RepositoryOwner>()
        }

        // When
        `when`(mockGetOwnersUseCase.getObservable(anyList())).thenReturn(observable)

        ownerPresenter.onResume()

        // Then
        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showEmpty()
        verify(mockView).showOwners(emptyList())
    }

    @Test
    fun testOnOwnerClick_loginPassed_showRepositories() {
        // When
        ownerPresenter.onOwnerClick("testLogin")

        // Then
        verify(mockView).startRepositoryActivity("testLogin")
    }

    @Test
    fun testAddOwner_blankCase_resetInput() {
        // Given
        ownerPresenter.owners.clear()

        // When
        ownerPresenter.addOwner("")

        // Then
        assert(ownerPresenter.owners.isEmpty())
        verify(mockView).resetNewOwnerInput()
    }

    @Test
    fun testAddOwner_repeatCase_resetInput() {
        // Given
        ownerPresenter.owners.clear()
        ownerPresenter.owners.add("testUser")

        // When
        ownerPresenter.addOwner("testUser")

        // Then
        assert(ownerPresenter.owners.size == 1)
        verify(mockView).resetNewOwnerInput()
    }

    @Test
    fun testAddOwner_normalCase_addToOwnersAndResetInput() {
        // Given
        ownerPresenter.owners.clear()
        val observable: Observable<List<OwnerQuery.RepositoryOwner>> = Observable.fromCallable {
            listOf(
                OwnerQuery.RepositoryOwner("testType", "", "testUser")
            )
        }

        // When
        `when`(mockGetOwnersUseCase.getObservable(anyList())).thenReturn(observable)

        ownerPresenter.addOwner("testUser")

        // Then
        assert(ownerPresenter.owners.size == 1)
        assert(ownerPresenter.owners.first() == "testUser")
        verify(mockView).resetNewOwnerInput()

        verify(mockView).hideEmpty()
        verify(mockView).showProgress()

        verify(mockView).hideProgress()
        verify(mockView).showOwners(anyList())
    }
}
