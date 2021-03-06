/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.rum

import com.datadog.android.Datadog
import com.datadog.android.DatadogConfig

/**
 * This class holds constant rum attribute keys.
 */
@Suppress("unused")
object RumAttributes {

    /**
     * The UUID of the application. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [DatadogConfig.Builder]
     */
    const val APPLICATION_ID: String = "application_id"

    /**
     * The package name of the application. (String)
     * This value is extracted from your application's manifest and
     * filled automatically by the [RumMonitor].
     */
    const val APPLICATION_PACKAGE: String = "application.package"

    /**
     * The human readable version of the application. (String)
     * This value is extracted from your application's manifest and
     * filled automatically by the [RumMonitor].
     */
    const val APPLICATION_VERSION: String = "version"

    /**
     * The name of the application or service generating the rum events. (String)
     * This values is configurable through the DatadogConfig during the SDK initialization.
     * By default it will take the application package name.
     * @see [DatadogConfig.Builder.setServiceName]
     */
    const val SERVICE_NAME: String = "service"

    /**
     * The date when the log is fired as an ISO-8601 String. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val DATE: String = "date"

    /**
     * A duration of any kind in nanoseconds. (Number)
     * This value is filled automatically by the [RumMonitor] for
     * Views, Resources and Actions.
     */
    const val DURATION: String = "duration"

    /**
     * The error type or kind (or code is some cases). (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_KIND: String = "error.kind"

    /**
     * A concise, human-readable, one-line message explaining the event. (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_MESSAGE: String = "message"

    /**
     * Value among: agent, console, network, source, logger. (String)
     * This value is filled automatically by the [RumMonitor].
     * @see [RumMonitor.addError]
     */
    const val ERROR_ORIGIN: String = "error.origin"

    /**
     * The stack trace or the complementary information about the error. (String)
     * This value is filled automatically by the [RumMonitor] when you pass in a [Throwable].
     * @see [RumMonitor.addError]
     * @see [RumMonitor.stopResourceWithError]
     */
    const val ERROR_STACK: String = "error.stack"

    /**
     * The category of the event. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val EVT_CATEGORY: String = "evt.category"

    /**
     * The UUID of the user action. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val EVT_ID: String = "evt.id"

    /**
     * The name of the user action. (String)
     * @see [RumMonitor.addUserAction]
     */
    const val EVT_NAME: String = "evt.name"

    /**
     * The UUID of the active user action. (String)
     * This is used to link errors and resources with a user action.
     * This value is filled automatically by the [RumMonitor].
     */
    const val EVT_USER_ACTION_ID: String = "evt.user_action_id"

    /**
     * Indicates the desired action to be performed for a given resource. (String)
     * This value is filled automatically by the [RumMonitor] for Resources,
     * as well as by the [RumInterceptor].
     * @see [RumMonitor.startResource]
     */
    const val HTTP_METHOD: String = "http.method"

    /** Duration of the initial connection phase. (Number) */
    const val HTTP_PERFORMANCE_CONNECT_DURATION: String = "http.performance.connect.duration"

    /** Duration between the start of the page and the initial connection phase. (Number) */
    const val HTTP_PERFORMANCE_CONNECT_START: String = "http.performance.connect.start"

    /** Duration of the dns lookup phase. (Number) */
    const val HTTP_PERFORMANCE_DNS_DURATION: String = "http.performance.dns.duration"

    /** Duration between the start of the page and the dns lookup phase. (Number) */
    const val HTTP_PERFORMANCE_DNS_START: String = "http.performance.dns.start"

    /** Duration of the content download phase. (Number) */
    const val HTTP_PERFORMANCE_DOWNLOAD_DURATION: String = "http.performance.download.duration"

    /** Duration between the start of the page and the content download phase. (Number) */
    const val HTTP_PERFORMANCE_DOWNLOAD_START: String = "http.performance.download.start"

    /** Duration of the time to first byte phase. (Number) */
    const val HTTP_PERFORMANCE_FIRST_BYTE_DURATION: String = "http.performance.first_byte.duration"

    /** Duration between the start of the page and the time to first byte phase. (Number) */
    const val HTTP_PERFORMANCE_FIRST_BYTE_START: String = "http.performance.first_byte.start"

    /** Duration of the redirect phase. (Number) */
    const val HTTP_PERFORMANCE_REDIRECT_DURATION: String = "http.performance.redirect.duration"

    /** Start of the redirect phase. (Number) */
    const val HTTP_PERFORMANCE_REDIRECT_START: String = "http.performance.redirect.start"

    /** Duration of the secure connection phase. (Number) */
    const val HTTP_PERFORMANCE_SSL_DURATION: String = "http.performance.ssl.duration"

    /** Duration between the start of the page and the secure connection phase. (Number) */
    const val HTTP_PERFORMANCE_SSL_START: String = "http.performance.ssl.start"

    /**
     * HTTP header field that identifies the address of the web page
     * that linked to the resource being requested. (String)
     */
    const val HTTP_REFERRER: String = "http.referrer"

    /**
     * The HTTP response status code. (Number)
     * This value is filled automatically by the [RumInterceptor].
     */
    const val HTTP_STATUS_CODE: String = "http.status_code"

    /**
     * The URL of the HTTP request. (String)
     * This value is filled automatically by the [RumMonitor] for Resources,
     * as well as by the [RumInterceptor].
     * @see [RumMonitor.startResource]
     */
    const val HTTP_URL: String = "http.url"

    /**
     * The User-Agent as it is sent (raw format). (String)
     * This value is automatically filled by the Datadog framework, using the System's "http.agent" property.
     */
    const val HTTP_USERAGENT: String = "http.useragent"

    /**
     * The version of HTTP used for the request. (String)
     */
    const val HTTP_VERSION: String = "http.version"

    /**
     * Total number of bytes transmitted from the client to the server. (Number)
     */
    const val NETWORK_BYTES_READ: String = "network.bytes_read"

    /**
     * Total number of bytes transmitted from the server to the client. (Number)
     * This value is filled automatically by the [RumInterceptor].
     */
    const val NETWORK_BYTES_WRITTEN: String = "network.bytes_written"

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
     * The IP address of the client that initiated the TCP connection. (String)
     * This value is automatically filled by the Datadog framework.
     */
    const val NETWORK_CLIENT_IP: String = "network.client.ip"

    /**
     * The port of the client that initiated the connection. (Number)
     */
    const val NETWORK_CLIENT_PORT: String = "network.client.port"

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

    /** Value among: document, xhr, beacon, fetch, css, js, image, font, media, other. (String) */
    const val RESOURCE_KIND: String = "resource.kind"

    /**
     * The start time of a resource DNS resolution.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DNS_START: String = "http.performance.dns.start"

    /**
     * The duration of a resource DNS resolution.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DNS_DURATION: String = "http.performance.dns.duration"

    /**
     * The start time of a resource connection.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_CONNECT_START: String = "http.performance.connect.start"

    /**
     * The duration of a resource connection.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_CONNECT_DURATION: String = "http.performance.connect.duration"

    /**
     * The start time of a resource  SSL handshake.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_SSL_START: String = "http.performance.ssl.start"

    /**
     * The duration of a resource SSL handshake.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_SSL_DURATION: String = "http.performance.ssl.duration"

    /**
     * The start time of a resource response headers download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_FB_START: String = "http.performance.first_byte.start"

    /**
     * The duration of a resource response headers download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_FB_DURATION: String = "http.performance.first_byte.duration"

    /**
     * The start time of a resource response body download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DL_START: String = "http.performance.download.start"

    /**
     * The duration of a resource response body download.
     * This value is filled automatically by the [RumMonitor].
     */
    const val RESOURCE_TIMING_DL_DURATION: String = "http.performance.download.duration"

    /**
     * Version of the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val RUM_DOCUMENT_VERSION: String = "rum.document_version"

    /**
     * The UUID of the session. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val SESSION_ID: String = "session_id"

    /**
     * The technology from which the log originated. (String)
     * This value is filled automatically by the [RumMonitor].
     */
    const val SOURCE: String = "source"

    /**
     * Version of the current Datadog SDK.
     */
    const val SDK_VERSION: String = "sdk_version"

    /**
     * Trace Id related to the resource loading. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val TRACE_ID: String = "trace_id"

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

    /** Number of errors collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_MEASURES_ERROR_COUNT: String = "view.measures.error_count"

    /** Number of resources collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_MEASURES_RESOURCE_COUNT: String = "view.measures.resource_count"

    /** Number of user actions collected on the view. (Number)
     * This value is filled automatically by the [RumMonitor].
     */
    const val VIEW_MEASURES_USER_ACTION_COUNT: String = "view.measures.user_action_count"

    /**
     * The Url of the page that linked to the current page. (String)
     */
    const val VIEW_REFERRER: String = "view.referrer"

    /**
     * The Url of the view. (String)
     */
    const val VIEW_URL: String = "view.url"

    /**
     * The touch target class name. (String)
     */
    const val TAG_TARGET_CLASS_NAME: String = "target.classname"

    /**
     * The touch target resource id. (String)
     * In case the resource id is missing we will provide the
     * target id in a Hexa String format (e.g. 0x1A2B1)
     */
    const val TAG_TARGET_RESOURCE_ID: String = "target.resourceId"

    /**
     * The position of the touch target in the scrollable container adapter. (Integer)
     * Provided only in cases where the parent of the target is a scrollable component.
     * For now we only support the RecyclerView component.
     */
    const val TAG_TARGET_POSITION_IN_SCROLLABLE_CONTAINER: String =
        "target.scrollableContainer.position"

    /**
     * The class name of the target container in case this is a scrollable component. (String)
     * For now we only support the RecyclerView component.
     */
    const val TAG_TARGET_SCROLLABLE_CONTAINER_CLASS_NAME: String =
        "target.scrollableContainer.classname"

    /**
     * The resource id of the target container in case this is a scrollable component. (String)
     * In case the resource id is missing we will provide the
     * container id in a Hexa String format (e.g. 0x1A2B1)
     * For now we only support the RecyclerView component.
     */
    const val TAG_TARGET_SCROLLABLE_CONTAINER_RESOURCE_ID: String =
        "target.scrollableContainer.resourceId"

    /**
     * The value of the target title attribute. We are usually adding this as an extra information
     * for the Tapped Menu items.
     */
    const val TAG_TARGET_TITLE: String = "target.title"

    /**
     * The gesture event direction.
     */
    const val TAG_GESTURE_DIRECTION: String = "gesture.direction"
}
