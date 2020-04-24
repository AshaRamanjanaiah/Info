package com.project.info

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.project.info.di.DaggerViewModelComponent
import com.project.info.model.CanadaInfo
import com.project.info.model.Details
import com.project.info.network.InfoApiService
import com.project.info.viewmodel.InfoViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class InfoViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var infoApiService: InfoApiService

    val infoViewModel by lazy { InfoViewModel(true) }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        DaggerViewModelComponent.builder()
            .apiModule(ApiModuleTest(infoApiService))
            .build()
            .inject(infoViewModel)
    }

    @Before
    fun setupRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }

    @Test
    fun getInfoSuccess() {
        val detail1 = Details("Beavers", "Beavers are second only animals", "http://test")
        val detail2 = Details("Flag", "", "http://test")
        val details = listOf(detail1, detail2)
        val canadaInfo = CanadaInfo("About Canada", details)

        val testSingle = Single.just(canadaInfo)
        Mockito.`when`(infoApiService.getCanadaInfo()).thenReturn(testSingle)

        infoViewModel.refresh()

        Assert.assertEquals(2, infoViewModel.countryDetails.value?.size)
        Assert.assertEquals(canadaInfo.details, infoViewModel.countryDetails.value)
        Assert.assertEquals(false, infoViewModel.loadError.value)
        Assert.assertEquals(false,infoViewModel.loading.value)
    }

    @Test
    fun getInfoFailure() {
        val testSingleError = Single.error<CanadaInfo>(Throwable())
        Mockito.`when`(infoApiService.getCanadaInfo()).thenReturn(testSingleError)

        infoViewModel.refresh()

        Assert.assertNotNull(infoViewModel.countryDetails.value)
        Assert.assertNotNull(infoViewModel.title.value)
        Assert.assertEquals(false, infoViewModel.loadError.value)
        Assert.assertEquals(false,infoViewModel.loading.value)
    }
}