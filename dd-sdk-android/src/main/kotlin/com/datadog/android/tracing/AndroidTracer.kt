/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.tracing

import com.datadog.android.core.internal.CoreFeature
import com.datadog.android.log.LogAttributes
import com.datadog.android.log.Logger
import com.datadog.android.rum.GlobalRum
import com.datadog.android.tracing.internal.TracesFeature
import com.datadog.android.tracing.internal.data.TraceWriter
import com.datadog.android.tracing.internal.handlers.AndroidSpanLogsHandler
import datadog.opentracing.DDTracer
import datadog.opentracing.LogHandler
import datadog.opentracing.propagation.ExtractedContext
import datadog.trace.api.Config
import datadog.trace.api.sampling.PrioritySampling
import io.opentracing.Span
import io.opentracing.log.Fields
import java.math.BigInteger
import java.security.SecureRandom
import java.util.Properties
import java.util.Random

/**
 *  A class enabling Datadog tracing features.
 *
 * It allows you to create [ DDSpan ] and send them to Datadog servers.
 *
 * You can have multiple tracers configured in your application, each with their own settings.
 *
 */
class AndroidTracer internal constructor(
    config: Config,
    writer: TraceWriter,
    private val logsHandler: LogHandler,
    private val random: Random,
    private val bundleWithRum: Boolean
) : DDTracer(config, writer) {

    override fun buildSpan(operationName: String): DDSpanBuilder {
        // On Android, the same zygote is reused for every single application,
        // meaning that the ThreadLocalRandom reuses the same exact state,
        // resulting in conflicting TraceIds.
        // To mitigate this, we recompute our own trace id and override it here
        val parentContext = activeSpan()?.context() ?: ExtractedContext(
            BigInteger(TRACE_ID_BIT_SIZE, random),
            BigInteger.ZERO,
            PrioritySampling.UNSET,
            null,
            emptyMap(),
            emptyMap()
        )
        return DDSpanBuilder(operationName, scopeManager())
            .withLogHandler(logsHandler)
            .asChildOf(parentContext)
            .withRumContext()
    }

    /**
     * Builds a [AndroidTracer] instance.
     *
     */
    class Builder {

        private var bundleWithRumEnabled: Boolean = true
        private var serviceName: String = CoreFeature.serviceName
        private var partialFlushThreshold = DEFAULT_PARTIAL_MIN_FLUSH
        private var random: Random = SecureRandom()
        private val logsHandler: LogHandler

        init {
            val logger = Logger.Builder().setLoggerName(TRACE_LOGGER_NAME).build()
            logsHandler = AndroidSpanLogsHandler(logger)
        }

        // region Public API

        /**
         * Builds a [AndroidTracer] based on the current state of this Builder.
         */
        fun build(): AndroidTracer {
            return AndroidTracer(
                config(),
                TraceWriter(TracesFeature.persistenceStrategy.getWriter()),
                logsHandler,
                random,
                bundleWithRumEnabled
            )
        }

        /**
         * Sets the service name that will appear in your traces.
         * @param serviceName the service name (default = "android")
         */
        fun setServiceName(serviceName: String): Builder {
            this.serviceName = serviceName
            return this
        }

        /**
         * Sets the partial flush threshold. When this threshold is reached (you have a specific
         * amount of spans closed waiting) the flush mechanism will be triggered and all the pending
         * closed spans will be processed in order to be sent to the intake.
         * @param threshold the threshold value (default = 5)
         */
        fun setPartialFlushThreshold(threshold: Int): Builder {
            this.partialFlushThreshold = threshold
            return this
        }

        /**
         * Enables the trace bundling with the current active View. If this feature is enabled all
         * the spans from this moment on will be bundled with the current view information and you
         * will be able to see all the traces sent during a specific view in the Rum Explorer.
         * @param enabled true by default
         */
        fun setBundleWithRumEnabled(enabled: Boolean): Builder {
            bundleWithRumEnabled = enabled
            return this
        }

        // endregion

        // region Internal

        internal fun withRandom(random: Random): Builder {
            this.random = random
            return this
        }

        internal fun properties(): Properties {
            val properties = Properties()
            properties.setProperty(Config.SERVICE_NAME, serviceName)
            properties.setProperty(
                Config.PARTIAL_FLUSH_MIN_SPANS,
                partialFlushThreshold.toString()
            )
            return properties
        }

        private fun config(): Config {
            return Config.get(properties())
        }

        // endregion
    }

    // region Internal

    private fun DDSpanBuilder.withRumContext(): DDSpanBuilder {
        return if (bundleWithRum) {
            val rumContext = GlobalRum.getRumContext()
            withTag(LogAttributes.RUM_APPLICATION_ID, rumContext.applicationId)
                .withTag(LogAttributes.RUM_SESSION_ID, rumContext.sessionId)
                .withTag(LogAttributes.RUM_VIEW_ID, rumContext.viewId)
        } else {
            this
        }
    }

    // endregion

    companion object {
        // the minimum closed spans required for triggering a flush and deliver
        // everything to the writer
        internal const val DEFAULT_PARTIAL_MIN_FLUSH = 5

        internal const val TRACE_LOGGER_NAME = "trace"

        internal const val TRACE_ID_BIT_SIZE = 63

        /**
         * Helper method to attach a Throwable to a specific Span.
         * The Throwable information (class name, message and stacktrace) will be added to the
         * provided Span as standard Error Tags.
         * @param span the active Span
         * @param throwable the Throwable you wan to log
         */
        @JvmStatic
        fun logThrowable(span: Span, throwable: Throwable) {
            val fieldsMap = mapOf(Fields.ERROR_OBJECT to throwable)
            span.log(fieldsMap)
        }

        /**
         * Helper method to attach an error message to a specific Span.
         * The error message will be added to the provided Span as a standard Error Tag.
         * @param span the active Span
         * @param message the error message you want to attach
         */
        @JvmStatic
        fun logErrorMessage(span: Span, message: String) {
            val fieldsMap = mapOf(Fields.MESSAGE to message)
            span.log(fieldsMap)
        }
    }
}
