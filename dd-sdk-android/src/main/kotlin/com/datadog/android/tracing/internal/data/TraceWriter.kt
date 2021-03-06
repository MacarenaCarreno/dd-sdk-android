/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.tracing.internal.data

import datadog.opentracing.DDSpan
import datadog.trace.common.writer.Writer

internal class TraceWriter(
    val writer: com.datadog.android.core.internal.data.Writer<DDSpan>
) : Writer {

    override fun start() {
        // NO - OP
    }

    override fun write(trace: MutableList<DDSpan>?) {
        trace?.let {
            writer.write(it)
        }
    }

    override fun close() {
        // NO - OP
    }

    override fun incrementTraceCount() {
        // NO - OP
    }
}
