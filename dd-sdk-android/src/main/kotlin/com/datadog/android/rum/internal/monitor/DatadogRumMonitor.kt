/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.rum.internal.monitor

import android.os.Handler
import com.datadog.android.core.internal.data.Writer
import com.datadog.android.rum.RumMonitor
import com.datadog.android.rum.RumResourceKind
import com.datadog.android.rum.internal.domain.event.RumEvent
import com.datadog.android.rum.internal.domain.event.RumEventData
import com.datadog.android.rum.internal.domain.scope.RumApplicationScope
import com.datadog.android.rum.internal.domain.scope.RumRawEvent
import com.datadog.android.rum.internal.domain.scope.RumScope
import java.util.UUID
import java.util.concurrent.TimeUnit

internal class DatadogRumMonitor(
    applicationId: UUID,
    private val writer: Writer<RumEvent>,
    private val handler: Handler
) : RumMonitor, AdvancedRumMonitor {

    private val rootScope: RumScope = RumApplicationScope(applicationId)

    internal val keepAliveRunnable = Runnable {
        handleEvent(RumRawEvent.KeepAlive())
    }

    init {
        handler.postDelayed(keepAliveRunnable, KEEP_ALIVE_MS)
    }

    // region RumMonitor

    override fun startView(key: Any, name: String, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StartView(key, name, attributes)
        )
    }

    override fun stopView(key: Any, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StopView(key, attributes)
        )
    }

    override fun addUserAction(action: String, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StartAction(action, false, attributes)
        )
    }

    override fun startUserAction(action: String, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StartAction(action, true, attributes)
        )
    }

    override fun stopUserAction(action: String, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StopAction(action, attributes)
        )
    }

    override fun startResource(
        key: String,
        method: String,
        url: String,
        attributes: Map<String, Any?>
    ) {
        handleEvent(
            RumRawEvent.StartResource(key, url, method, attributes)
        )
    }

    override fun stopResource(key: String, kind: RumResourceKind, attributes: Map<String, Any?>) {
        handleEvent(
            RumRawEvent.StopResource(key, kind, attributes)
        )
    }

    override fun stopResourceWithError(
        key: String,
        message: String,
        origin: String,
        throwable: Throwable
    ) {
        handleEvent(
            RumRawEvent.StopResourceWithError(key, message, origin, throwable)
        )
    }

    override fun addError(
        message: String,
        origin: String,
        throwable: Throwable?,
        attributes: Map<String, Any?>
    ) {
        handleEvent(
            RumRawEvent.AddError(message, origin, throwable, attributes)
        )
    }

    // endregion

    // region Internal

    override fun resetSession() {
        handleEvent(
            RumRawEvent.ResetSession()
        )
    }

    override fun viewTreeChanged() {
        handleEvent(
            RumRawEvent.ViewTreeChanged()
        )
    }

    override fun waitForResourceTiming(key: String) {
        handleEvent(
            RumRawEvent.WaitForResourceTiming(key)
        )
    }

    override fun addResourceTiming(key: String, timing: RumEventData.Resource.Timing) {
        handleEvent(
            RumRawEvent.AddResourceTiming(key, timing)
        )
    }

    internal fun handleEvent(event: RumRawEvent) {
        handler.removeCallbacks(keepAliveRunnable)
        synchronized(rootScope) { rootScope.handleEvent(event, writer) }
        handler.postDelayed(keepAliveRunnable, KEEP_ALIVE_MS)
    }

    internal fun stopKeepAliveCallback() {
        handler.removeCallbacks(keepAliveRunnable)
    }

    // endregion

    companion object {
        internal val KEEP_ALIVE_MS = TimeUnit.MINUTES.toMillis(5)
    }
}
