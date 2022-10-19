package com.example.android.concatadapter

import com.google.android.material.transition.platform.MaterialContainerTransform.ProgressThresholds


/**
 * Created by Thinhvh on 19/10/2022.
 * Phone: 0398477967
 * Email: thinhvh.fpt@gmail.com
 */
data class AdsAdapterConfig(
    val itemThresholds: Int = DEFAULT_THRESHOLDS,
    val showAdsInCenter: Boolean= true,
    val showBanner: Boolean = true,
    val showNative: Boolean = true
) {
    companion object {
        const val DEFAULT_THRESHOLDS = 7
    }
}
