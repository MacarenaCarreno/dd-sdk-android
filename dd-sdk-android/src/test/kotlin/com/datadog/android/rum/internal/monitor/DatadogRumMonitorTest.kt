/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.rum.internal.monitor

import android.os.Handler
import com.datadog.android.core.internal.data.Writer
import com.datadog.android.rum.RumResourceKind
import com.datadog.android.rum.internal.domain.event.RumEvent
import com.datadog.android.rum.internal.domain.event.RumEventData
import com.datadog.android.rum.internal.domain.scope.RumRawEvent
import com.datadog.android.rum.internal.domain.scope.RumScope
import com.datadog.android.utils.forge.Configurator
import com.datadog.android.utils.forge.exhaustiveAttributes
import com.datadog.tools.unit.extensions.ApiLevelExtension
import com.datadog.tools.unit.setFieldValue
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.same
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import fr.xgouchet.elmyr.Forge
import fr.xgouchet.elmyr.annotation.Forgery
import fr.xgouchet.elmyr.annotation.RegexForgery
import fr.xgouchet.elmyr.annotation.StringForgery
import fr.xgouchet.elmyr.annotation.StringForgeryType
import fr.xgouchet.elmyr.junit5.ForgeConfiguration
import fr.xgouchet.elmyr.junit5.ForgeExtension
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness

@Extensions(
    ExtendWith(MockitoExtension::class),
    ExtendWith(ForgeExtension::class),
    ExtendWith(ApiLevelExtension::class)
)
@MockitoSettings(strictness = Strictness.LENIENT)
@ForgeConfiguration(Configurator::class)
internal class DatadogRumMonitorTest {

    lateinit var testedMonitor: DatadogRumMonitor

    @Mock
    lateinit var mockScope: RumScope

    @Mock
    lateinit var mockWriter: Writer<RumEvent>

    @Mock
    lateinit var mockHandler: Handler

    @Forgery
    lateinit var fakeApplicationId: UUID

    lateinit var fakeAttributes: Map<String, Any?>

    @BeforeEach
    fun `set up`(forge: Forge) {
        fakeAttributes = forge.exhaustiveAttributes()
        testedMonitor = DatadogRumMonitor(fakeApplicationId, mockWriter, mockHandler)
        testedMonitor.setFieldValue("rootScope", mockScope)
    }

    @AfterEach
    fun `tear down`() {
    }

    @Test
    fun `delegates startView to rootScope`(
        @StringForgery(StringForgeryType.ASCII) key: String,
        @StringForgery(StringForgeryType.ALPHABETICAL) name: String
    ) {
        testedMonitor.startView(key, name, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StartView
            assertThat(event.key).isEqualTo(key)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
            assertThat(event.name).isEqualTo(name)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates stopView to rootScope`(
        @StringForgery(StringForgeryType.ASCII) key: String
    ) {
        testedMonitor.stopView(key, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StopView
            assertThat(event.key).isEqualTo(key)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates addUserAction to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) name: String
    ) {
        testedMonitor.addUserAction(name, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StartAction
            assertThat(event.name).isEqualTo(name)
            assertThat(event.waitForStop).isFalse()
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates startUserAction to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) name: String
    ) {
        testedMonitor.startUserAction(name, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StartAction
            assertThat(event.name).isEqualTo(name)
            assertThat(event.waitForStop).isTrue()
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates stopUserAction to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) name: String
    ) {
        testedMonitor.stopUserAction(name, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StopAction
            assertThat(event.name).isEqualTo(name)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates startResource to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) key: String,
        @StringForgery(StringForgeryType.ALPHABETICAL) method: String,
        @RegexForgery("http(s?)://[a-z]+.com/[a-z]+") url: String
    ) {
        testedMonitor.startResource(key, method, url, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StartResource
            assertThat(event.key).isEqualTo(key)
            assertThat(event.method).isEqualTo(method)
            assertThat(event.url).isEqualTo(url)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates stopResource to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) key: String,
        @Forgery kind: RumResourceKind
    ) {
        testedMonitor.stopResource(key, kind, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StopResource
            assertThat(event.key).isEqualTo(key)
            assertThat(event.kind).isEqualTo(kind)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates stopResourceWithError to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) key: String,
        @StringForgery(StringForgeryType.ALPHABETICAL) message: String,
        @StringForgery(StringForgeryType.ALPHABETICAL) origin: String,
        @Forgery throwable: Throwable
    ) {
        testedMonitor.stopResourceWithError(key, message, origin, throwable)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.StopResourceWithError
            assertThat(event.key).isEqualTo(key)
            assertThat(event.message).isEqualTo(message)
            assertThat(event.origin).isEqualTo(origin)
            assertThat(event.throwable).isEqualTo(throwable)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates addError to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) message: String,
        @StringForgery(StringForgeryType.ALPHABETICAL) origin: String,
        @Forgery throwable: Throwable
    ) {
        testedMonitor.addError(message, origin, throwable, fakeAttributes)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            val event = firstValue as RumRawEvent.AddError
            assertThat(event.message).isEqualTo(message)
            assertThat(event.origin).isEqualTo(origin)
            assertThat(event.throwable).isEqualTo(throwable)
            assertThat(event.attributes).containsAllEntriesOf(fakeAttributes)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates viewTreeChanged to rootScope`() {
        testedMonitor.viewTreeChanged()

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            assertThat(firstValue).isInstanceOf(RumRawEvent.ViewTreeChanged::class.java)
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates waitForResourceTiming to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) key: String
    ) {
        testedMonitor.waitForResourceTiming(key)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            assertThat(firstValue).isEqualTo(RumRawEvent.WaitForResourceTiming(key))
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `delegates addResourceTiming to rootScope`(
        @StringForgery(StringForgeryType.ALPHABETICAL) key: String,
        @Forgery timing: RumEventData.Resource.Timing
    ) {
        testedMonitor.addResourceTiming(key, timing)

        argumentCaptor<RumRawEvent> {
            verify(mockScope).handleEvent(capture(), same(mockWriter))

            assertThat(firstValue).isEqualTo(RumRawEvent.AddResourceTiming(key, timing))
        }
        verifyNoMoreInteractions(mockScope, mockWriter)
    }

    @Test
    fun `sends keep alive event to rootScope regularly`() {
        argumentCaptor<Runnable> {
            inOrder(mockScope, mockWriter, mockHandler) {
                verify(mockHandler).postDelayed(capture(), eq(DatadogRumMonitor.KEEP_ALIVE_MS))
                verifyZeroInteractions(mockScope)
                val runnable = firstValue
                runnable.run()
                verify(mockHandler).removeCallbacks(same(runnable))
                verify(mockScope).handleEvent(
                    argThat { this is RumRawEvent.KeepAlive },
                    same(mockWriter)
                )
                verify(mockHandler).postDelayed(same(runnable), eq(DatadogRumMonitor.KEEP_ALIVE_MS))
                verifyNoMoreInteractions()
            }
        }
    }

    @Test
    fun `delays keep alive runnable on other event`() {
        val mockEvent: RumRawEvent = mock()
        val runnable = testedMonitor.keepAliveRunnable

        testedMonitor.handleEvent(mockEvent)

        argumentCaptor<Runnable> {
            inOrder(mockScope, mockWriter, mockHandler) {
                verify(mockHandler).removeCallbacks(same(runnable))
                verify(mockScope).handleEvent(same(mockEvent), same(mockWriter))
                verify(mockHandler).postDelayed(same(runnable), eq(DatadogRumMonitor.KEEP_ALIVE_MS))
                verifyNoMoreInteractions()
            }
        }
    }

    @Test
    fun `removes callback from handler on stopKeepAliveCallback`() {
        // initial post
        verify(mockHandler).postDelayed(any(), any())

        testedMonitor.stopKeepAliveCallback()

        verify(mockHandler).removeCallbacks(same(testedMonitor.keepAliveRunnable))
        verifyNoMoreInteractions(mockHandler, mockWriter, mockScope)
    }
}
