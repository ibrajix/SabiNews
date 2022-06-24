package com.ibrajix.sabinews.screens.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ibrajix.sabinews.BuildConfig
import com.ibrajix.sabinews.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import java.net.URL

@Composable
fun ShowCustomChromeTab(articleUrl: String?){

    val context = LocalContext.current

    val packageName = "com.android.chrome"

    val activity = (context as? Activity)

    val builder = CustomTabsIntent.Builder()

    builder.setShowTitle(true)

    builder.setInstantAppsEnabled(true)

    builder.setDefaultColorSchemeParams(
        CustomTabColorSchemeParams.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.status_bar_color))
            .build()
    )

    val customBuilder = builder.build()

    if (packageName != null){
        customBuilder.intent.setPackage(packageName)
        customBuilder.launchUrl(context, Uri.parse(articleUrl))
    }
    else {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
        activity?.startActivity(i)
    }


}