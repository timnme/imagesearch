package com.instacentner.instacentner.ui.imagesearch

import com.instacentner.instacentner.domain.domainmodels.Image
import com.instacentner.instacentner.domain.usecases.FavouriteImageUseCase
import com.instacentner.instacentner.domain.usecases.ImageSearchUseCase
import com.instacentner.instacentner.domain.usecases.UnfavouriteImageUseCase
import com.instacentner.instacentner.ui.imagesearch.intents.ImageSearchIntent
import com.instacentner.instacentner.ui.imagesearch.viewstates.ImageSearchViewState
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ImageSearchPresenterTest {
    @Mock
    lateinit var imageSearchUseCase: ImageSearchUseCase
    @Mock
    lateinit var favouriteImageUseCase: FavouriteImageUseCase
    @Mock
    lateinit var unfavouriteImageUseCase: UnfavouriteImageUseCase

    private lateinit var presenter: ImageSearchPresenter
    private lateinit var robot: ImageSearchViewRobot

    private val query: String = "foo"
    private val errorMessage: String = "Search error"
    private val searchIntent = ImageSearchIntent(query)

    private val images = arrayListOf(
        Image(
            id = 1,
            previewUrl = "",
            previewImageHeight = 1,
            previewImageWidth = 2,
            largeImageUrl = "",
            isFavourite = true
        ),
        Image(
            id = 2,
            previewUrl = "",
            previewImageHeight = 1,
            previewImageWidth = 2,
            largeImageUrl = "",
            isFavourite = false
        )
    )
    private val searchNotStarted = ImageSearchViewState(
        loading = false,
        message = "Type a word into the box above",
        imageList = null
    )
    private val searchInProgress =
        ImageSearchViewState(loading = true, message = """Search for "$query"""", imageList = null)
    private val searchSuccessful = ImageSearchViewState(
        loading = false,
        message = """Results for "$query"""",
        imageList = images
    )
    private val searchEmpty = ImageSearchViewState(
        loading = false,
        message = """Nothing found for query "$query"""",
        imageList = null
    )
    private val searchError = ImageSearchViewState(
        loading = false,
        message = """Error while searching for "$query":${"\n$errorMessage"}""",
        imageList = null
    )

    companion object {
        @BeforeClass
        @JvmStatic
        fun init() = RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ImageSearchPresenter(
            imageSearchUseCase,
            favouriteImageUseCase,
            unfavouriteImageUseCase
        )
        robot = ImageSearchViewRobot(presenter)
    }

    @Test
    fun imageSearchIntent_verifySuccessRendered() {
        `when`(imageSearchUseCase.execute(query)).thenReturn(Single.just(images))

        robot.fireSearchImageIntent(searchIntent)

        robot.assertViewStateRendered(searchNotStarted, searchInProgress, searchSuccessful)
    }

    @Test
    fun imageSearchIntent_verifyEmptyRendered() {
        `when`(imageSearchUseCase.execute(query)).thenReturn(Single.just(emptyList()))

        robot.fireSearchImageIntent(searchIntent)

        robot.assertViewStateRendered(searchNotStarted, searchInProgress, searchEmpty)
    }

    @Test
    fun imageSearchIntent_verifyErrorRendered() {
        `when`(imageSearchUseCase.execute(query)).thenReturn(Single.error(Throwable(errorMessage)))

        robot.fireSearchImageIntent(searchIntent)

        robot.assertViewStateRendered(searchNotStarted, searchInProgress, searchError)
    }
}