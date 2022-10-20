package com.example.android.concatadapter



/**
 * Created by Thinhvh on 19/10/2022.
 * Phone: 0398477967
 * Email: thinhvh.fpt@gmail.com
 */
data class AdsAdapterConfig(
    val itemThresholds: Int = DEFAULT_THRESHOLDS,
    val showAdsInCenter: Boolean= true,
    val showBanner: Boolean = true,
    val showNative: Boolean = true,
    val loadMore: Boolean = true
) {
    companion object {
        const val DEFAULT_THRESHOLDS = 7
    }
}
