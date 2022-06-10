package dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.exoPlayer(){
    implementation("com.google.android.exoplayer:exoplayer-core:2.17.1")
    implementation("com.google.android.exoplayer:exoplayer-dash:2.17.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.17.1")
}