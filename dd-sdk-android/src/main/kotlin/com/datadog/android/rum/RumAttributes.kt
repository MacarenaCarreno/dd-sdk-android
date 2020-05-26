/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.rum

import com.datadog.android.Datadog
import com.datadog.android.DatadogConfig
import com.datadog.android.DatadogInterceptor

/**
 * This class holds constant rum attribute keys.
 */
@Suppress("unused")
object RumAttributes {

    // region Tags

    /**
     * The human readable version of the application. (String)
     * This value is extracted from your application's manifest and
     * filled automatically by the [RumMonitor].
     */
    const val APPLICATION_VERSION: String = "version"

    /**
     * The custom environment name. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val ENV: String = "env"

    /**
     * The name of the application or service generating the rum events. (String)
     * This values is configurable through the DatadogConfig during the SDK initialization.
     * By default it will take the application package name.
     * @see [DatadogConfig.Builder.setServiceName]
     */
    const val SERVICE_NAME: String = "service"

    /**
     * The technology from which the log originated. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val SOURCE: String = "source"

    /**
     * Version of the current Datadog SDK.
     */
    const val SDK_VERSION: String = "sdk_version"

    // endregion

    // region Global Attributes

    /**
     * The version of the RUM data format.
     * This value is filled automatically by the [RumMonitor].
     */
    @Suppress("ObjectPropertyNaming", "ObjectPropertyName")
    internal const val _DD_FORMAT_VERSION = "_dd.format_version"

    /**
     * The UUID of the active user action. (String)
     * This is used to link errors and resources with a user action.
     * This value is filled automatically by the [RumMonitor].
     */
    const val ACTION_ID: String = "action.id"

    /**
     * The UUID of the application. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [DatadogConfig.Builder]
     */
    const val APPLICATION_ID: String = "application.id"

    /**
     * The date when the log is fired as an ISO-8601 String. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val DATE: String = "date"

    /**
     * The UUID of the session. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val SESSION_ID: String = "session.id"

    /**
     * The type of the session (can be "user" or"synthetics"). (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val SESSION_TYPE: String = "session.type"

    /**
     * The type of the event. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val TYPE: String = "type"

    /**
     * The user email. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [Datadog.setUserInfo]
     */
    const val USER_EMAIL: String = "usr.email"

    /**
     * The user identifier. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [Datadog.setUserInfo]
     */
    const val USER_ID: String = "usr.id"

    /**
     * The user friendly name. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [Datadog.setUserInfo]
     */
    const val USER_NAME: String = "usr.name"

    /** The UUID of the view. (String) */
    const val VIEW_ID: String = "view.id"

    /**
     * The Url of the view. (String)
     */
    const val VIEW_URL: String = "view.url"

    // endregion

    // region View

    /**
     * Version of the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    @Suppress("ObjectPropertyNaming", "ObjectPropertyName")
    internal const val _DD_DOCUMENT_VERSION: String = "_dd.document_version"

    /** Number of user actions collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_ACTION_COUNT: String = "view.action.count"

    /**
     * The View duration in nanoseconds. (Number)
     * This is the time during which the View stayed visible and interactive.
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_DURATION: String = "view.duration"

    /** Number of errors collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_ERROR_COUNT: String = "view.error.count"

    /**
     * The Url of the page that linked to the current page. (String)
     */
    const val VIEW_REFERRER: String = "view.referrer"

    /** Number of resources collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_RESOURCE_COUNT: String = "view.resource.count"

    // endregion

    // region Resource

    /**
     * The resource (down)loading duration in nanoseconds. (Number)
     * This value is filled automatically by the [RumMonitor] for
     * Views, Resources and Actions.
     */
    const val RESOURCE_DURATION: String = "resource.duration"

    /**
     * The type of Resource. (String)
     * It can be one of: document, xhr, beacon, fetch, css, js, image, font, media, other.
     * This value is filled automatically by the [RumInterceptor].
     * @see [RumMonitor.stopResource]
     * @see [RumResourceType]
     */
    const val RESOURCE_TYPE: String = "resource.type"

    /**
     * Indicates the desired action to be performed for a given resource. (String)
     * This value is filled automatically by the [RumMonitor] and the [RumInterceptor].
     * @see [RumMonitor.startResource]
     */
    const val RESOURCE_METHOD: String = "resource.method"

    /**
     * Total number of bytes transmitted from the server to the client. (Number)
     * This value is filled automatically by the [RumInterceptor].
     */
    const val RESOURCE_SIZE: String = "resource.size"

    /**
     * The HTTP response status code. (Number)
     * This value is filled automatically by the [RumInterceptor].
     */
    const val RESOURCE_STATUS_CODE: String = "resource.status_code"

    /**
     * The start time of a resource DNS resolution.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DNS_START: String = "resource.dns.start"

    /**
     * The duration of a resource DNS resolution.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DNS_DURATION: String = "resource.dns.duration"

    /**
     * The start time of a resource connection.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_CONNECT_START: String = "resource.connect.start"

    /**
     * The duration of a resource connection.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_CONNECT_DURATION: String = "resource.connect.duration"

    /**
     * The start time of a resource  SSL handshake.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_SSL_START: String = "resource.ssl.start"

    /**
     * The duration of a resource SSL handshake.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_SSL_DURATION: String = "resource.ssl.duration"

    /**
     * The start time of a resource response headers download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_FB_START: String = "resource.first_byte.start"

    /**
     * The duration of a resource response headers download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_FB_DURATION: String = "resource.first_byte.duration"

    /**
     * The start time of a resource response body download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DL_START: String = "resource.download.start"

    /**
     * The duration of a resource response body download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DL_DURATION: String = "resource.download.duration"

    /**
     * The URL of a loaded Resource. (String)
     * This value is filled automatically by the [RumMonitor] and the [RumInterceptor].
     * @see [RumMonitor.startResource]
     */
    const val RESOURCE_URL: String = "resource.url"

    /**
     * Trace Id related to the resource loading. (Number)
     * This value is filled automatically by the [DatadogInterceptor].
     */
    const val TRACE_ID: String = "trace_id"

    // endregion

    // region Error

    /**
     * The error type or kind (or code in some cases). (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_TYPE: String = "error.type"

    /**
     * A concise, human-readable, one-line message explaining the event. (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_MESSAGE: String = "error.message"

    /**
     * The source of the error. (String)
     * Value among: agent, console, network, source, logger.
     * This value is filled automatically by the [RumMonitor].
     * @see [RumMonitor.addError]
     */
    const val ERROR_SOURCE: String = "error.source"

    /**
     * The stack trace or the complementary information about the error. (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_STACK: String = "error.stack"

    /**
     * Indicates the action performed by the Resource which triggered the error. (String)
     * This value is filled automatically by the [RumMonitor] and the [RumInterceptor].
     * @see [RumMonitor.startResource]
     */
    const val ERROR_RESOURCE_METHOD: String = "error.resource.method"

    /**
     * The HTTP response status code for the Resource which triggered the error. (Number)
     * This value is filled automatically by the [RumInterceptor].
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_RESOURCE_STATUS_CODE: String = "error.resource.status_code"

    /**
     * The URL of a loaded Resource which triggered the error. (String)
     * This value is filled automatically by the [RumMonitor] and the [RumInterceptor].
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_RESOURCE_URL: String = "error.resource.url"

    // endregion

    // region Action

    /**
     * The action duration in nanoseconds. (Number)
     * This value is filled automatically by the [RumMonitor].
     * @see [RumMonitor.addAction]
     * @see [RumMonitor.startAction]
     * @see [RumMonitor.stopAction]
     */
    const val ACTION_DURATION: String = "action.duration"

    /**
     * Number of errors collected on the action. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val ACTION_ERROR_COUNT: String = "action.error.count"

    /**
     * Number of resources collected on the action. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val ACTION_RESOURCE_COUNT: String = "action.resource.count"

    /**
     * The touch target class name. (String)
     * @see [RumMonitor.addAction]
     * @see [RumMonitor.startAction]
     * @see [RumMonitor.stopAction]
     */
    const val ACTION_TARGET_CLASS_NAME: String = "action.target.classname"

    /**
     * The name of the action's target. (String)
     * @see [RumMonitor.addAction]
     * @see [RumMonitor.startAction]
     * @see [RumMonitor.stopAction]
     */
    const val ACTION_TARGET_NAME: String = "action.target.name"

    /**
     * The index of the touch target in the parent view. (Integer)
     * For now we only detect RecyclerView as parent.
     */
    const val ACTION_TARGET_PARENT_INDEX: String = "action.target.parent.index"

    /**
     * The class name of the touch target's parent view. (String)
     * For now we only detect RecyclerView as parent.
     */
    const val ACTION_TARGET_PARENT_CLASSNAME: String = "action.target.parent.classname"

    /**
     * The resource id of the target container in case this is a scrollable component. (String)
     * In case the resource id is missing we will provide the
     * container id in a Hexa String format (e.g. 0x1A2B1)
     * For now we only support the RecyclerView component.
     */
    const val ACTION_TARGET_PARENT_RESOURCE_ID: String = "action.target.parent.resource_id"

    /**
     * The touch target resource id. (String)
     * It can either be the resource identifier, or the raw hexadecimal value.
     * @see [RumMonitor.addAction]
     * @see [RumMonitor.startAction]
     * @see [RumMonitor.stopAction]
     */
    const val ACTION_TARGET_RESOURCE_ID: String = "action.target.resource_id"

    /**
     * The action type. (String)
     * @see [RumMonitor.addAction]
     * @see [RumMonitor.startAction]
     * @see [RumMonitor.stopAction]
     */
    const val ACTION_TYPE: String = "action.type"

    // endregion

    // region Network Info

    /**
     * The unique id of the Carrier attached to the SIM card. (Number)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_CARRIER_ID: String = "network.client.sim_carrier.id"

    /**
     * The name of the Carrier attached to the SIM card. (String)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_CARRIER_NAME: String = "network.client.sim_carrier.name"

    /**
     * The connectivity status of the device. (String)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_CONNECTIVITY: String = "network.client.connectivity"

    /**
     * The downstream bandwidth for the current network in Kbps. (Number)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_DOWN_KBPS: String = "network.client.downlink_kbps"

    /**
     * The bearer specific signal strength. (Number)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_SIGNAL_STRENGTH: String = "network.client.signal_strength"

    /**
     * The upstream bandwidth for the current network in Kbps. (Number)
     * This value is filled automatically by the [RumMonitor] for resources and errors.
     */
    const val NETWORK_UP_KBPS: String = "network.client.uplink_kbps"

    // endregion

    // region …

    /**
     * Total number of bytes transmitted from the client to the server. (Number)
     * TODO RUMM-469 rename to v2
     */
    const val NETWORK_BYTES_READ: String = "network.bytes_read"

    /**
     * The gesture event direction.
     */
    const val TAG_GESTURE_DIRECTION: String = "gesture.direction"

    // endregion
}
