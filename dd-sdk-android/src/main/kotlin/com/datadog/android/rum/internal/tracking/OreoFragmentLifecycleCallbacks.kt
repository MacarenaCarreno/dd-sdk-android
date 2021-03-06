package com.datadog.android.rum.internal.tracking

import android.app.Activity
import android.app.DialogFragment
import android.app.Fragment
import android.app.FragmentManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.datadog.android.core.internal.utils.resolveViewName
import com.datadog.android.core.internal.utils.runIfValid
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.internal.RumFeature
import com.datadog.android.rum.tracking.ComponentPredicate

@Suppress("DEPRECATION")
@RequiresApi(Build.VERSION_CODES.O)
internal class OreoFragmentLifecycleCallbacks(
    private val argumentsProvider: (Fragment) -> Map<String, Any?>,
    private val componentPredicate: ComponentPredicate<Fragment>
) : FragmentLifecycleCallbacks<Activity>, FragmentManager.FragmentLifecycleCallbacks() {

    // region FragmentLifecycleCallbacks

    override fun register(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.fragmentManager.registerFragmentLifecycleCallbacks(this, true)
        }
    }

    override fun unregister(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.fragmentManager.unregisterFragmentLifecycleCallbacks(this)
        }
    }

    // endregion

    // region FragmentManager.FragmentLifecycleCallbacks

    override fun onFragmentActivityCreated(
        fm: FragmentManager,
        f: Fragment,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState)

        val context = f.context

        if (f is DialogFragment && context != null) {
            val window = f.dialog?.window
            val gesturesTracker = RumFeature.gesturesTracker
            gesturesTracker.startTracking(window, context)
        }
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        componentPredicate.runIfValid(f) {
            GlobalRum.get()
                .startView(
                    it,
                    it.resolveViewName(),
                    argumentsProvider(it)
                )
        }
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        componentPredicate.runIfValid(f) { GlobalRum.get().stopView(it) }
    }

    // endregion
}
