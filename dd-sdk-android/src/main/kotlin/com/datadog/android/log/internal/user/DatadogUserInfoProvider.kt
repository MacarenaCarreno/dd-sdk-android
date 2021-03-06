/*
 * Unless explicitly stated otherwise all files in this repository are licensed under the Apache License Version 2.0.
 * This product includes software developed at Datadog (https://www.datadoghq.com/).
 * Copyright 2016-Present Datadog, Inc.
 */

package com.datadog.android.log.internal.user

internal class DatadogUserInfoProvider : MutableUserInfoProvider {

    private var internalUserInfo = UserInfo()

    override fun setUserInfo(userInfo: UserInfo) {
        internalUserInfo = userInfo
    }

    override fun getUserInfo(): UserInfo {
        return internalUserInfo
    }
}
